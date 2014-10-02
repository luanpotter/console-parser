package xyz.luan.console.parser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import xyz.luan.console.parser.actions.Action;
import xyz.luan.console.parser.actions.Arg;
import xyz.luan.console.parser.actions.ArgumentParser;
import xyz.luan.console.parser.actions.InvalidAction;
import xyz.luan.console.parser.actions.InvalidCall;
import xyz.luan.console.parser.actions.InvalidHandler;
import xyz.luan.console.parser.actions.InvalidParameter;
import xyz.luan.console.parser.actions.Optional;
import xyz.luan.console.parser.call.CallResult;
import xyz.luan.console.parser.util.ClassMap;

public class ControllerRef<T extends Controller<?>> {

    private T controller;
    private Map<String, Method> actions;
    private Map<Class<? extends Throwable>, Method> handlers;

    public ControllerRef(T controller) throws InvalidAction, InvalidHandler {
        this.controller = controller;
        this.actions = new HashMap<>();
        this.handlers = new HashMap<>();
        for (Method method : controller.getClass().getMethods()) {
            parseAction(method);
            parseHandler(method);
        }
    }

	private void parseHandler(Method method) throws InvalidHandler {
		ExceptionHandler handler = method.getAnnotation(ExceptionHandler.class);
		if (handler != null) {
			assertValidHandlerMethod(method, handler);
			for (Class<? extends Throwable> c : handler.value()) {
				if (handlers.get(c) != null) {
					throw new InvalidHandler("Trying to register two handlers to the same exception type: " + c.getSimpleName());
				}
				handlers.put(c, method);
			}
		}
	}

	private void assertValidHandlerMethod(Method method, ExceptionHandler handler) throws InvalidHandler {
    	if (Modifier.isStatic(method.getModifiers())) {
    		throw new InvalidHandler(method, "should not be static");
    	}
		if (method.getParameters().length != 1) {
			throw new InvalidHandler(method, "must take exactly one parameter: the exception to be handle");
		}
		Class<?> parameterType = method.getParameters()[0].getType();
		for (Class<? extends Throwable> c : handler.value()) {
			if (!parameterType.isAssignableFrom(c)) {
				throw new InvalidHandler(method, String.format("the parameter must be a supertype common to all @ExceptionHandler exceptions; type '%s' can't be cast from '%s'.", c.getSimpleName(), parameterType.getSimpleName()));
			}	
		}
        if (!method.getReturnType().equals(CallResult.class)) {
            throw new InvalidHandler(method, "must always return Output object");
        }
	}

	private void parseAction(Method method) throws InvalidAction {
		Action action = method.getAnnotation(Action.class);
		if (action != null) {
		    assertValidateActionMethod(method);
		    if (this.actions.get(action.value()) != null) {
		        throw new InvalidAction("Two actions on the same controller same action name: " + action.value());
		    }
		    this.actions.put(action.value(), method);
		}
	}

    public CallResult call(String actionName, Map<String, String> rawParams) {
        try {
            Method method = actions.get(actionName);
            if (method == null) {
                throw new InvalidCall(String.format("Action name '%s' not found in controller '%s'", actionName, controller.getClass().getCanonicalName()));
            }
            Object[] actualParamValues = getActualParameterValues(method, rawParams);
            method.setAccessible(true);
            return (CallResult) method.invoke(controller, actualParamValues);
        } catch (Throwable e) {
        	if (e instanceof InvocationTargetException) {
        		e = e.getCause();
        	}
        	Method handler = ClassMap.getFromClassMap(handlers, e.getClass());
        	if (handler == null) {
        		throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
        	}
        	try {
				return (CallResult) handler.invoke(controller, e);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				throw new RuntimeException("Exception handler threw an exception! Run for your lives!", e1);
			}
        }
    }

    private Object[] getActualParameterValues(Method method, Map<String, String> rawParamValues) throws InvalidCall {
        Parameter[] paramData = method.getParameters();
        Object[] actualParamValues = new Object[paramData.length];
        assert paramData.length == actualParamValues.length;
        for (int i = 0; i < actualParamValues.length; i++) {
            boolean required;
            String name;

            Arg arg = paramData[i].getAnnotation(Arg.class);
            if (arg != null) {
                required = arg.required();
                name = arg.value();                
            } else {
                if (!paramData[i].isNamePresent()) {
                    throw new InvalidCall(method, "Unless you use @Arg on every param, you must turn on -parameters on Java so we can access the names of your parameters.");
                }
                required = paramData[i].getAnnotation(Optional.class) == null;
                name = paramData[i].getName();
            }

            String value = rawParamValues.get(name);
            if (value == null) {
                if (required) {
                    throw new InvalidCall(method, "The parameter " + name + " is required.");
                }
                actualParamValues[i] = null;
            } else {
                try {
                    actualParamValues[i] = ArgumentParser.parse(value);
                } catch (InvalidParameter e) {
                    throw new InvalidCall(method, e);
                }
            }
        }
        return actualParamValues;
    }
    
    private void assertValidateActionMethod(Method method) throws InvalidAction {
    	if (Modifier.isStatic(method.getModifiers())) {
    		throw new InvalidAction(method, "should not be static");
    	}

        for (Parameter param : method.getParameters()) {
            if (!ArgumentParser.hasParser(param.getType())) {
                String message = String.format("invalid parameter type '%s'; not registered on ArgumentParser", param.getType());
                throw new InvalidAction(method, message);
            }
        }
        
        if (!method.getReturnType().equals(CallResult.class)) {
            throw new InvalidAction(method, "must always return Output object");
        }
    }
}
