package xyz.luan.console.parser;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import xyz.luan.console.parser.actions.Action;
import xyz.luan.console.parser.actions.Arg;
import xyz.luan.console.parser.actions.ArgumentParser;
import xyz.luan.console.parser.actions.InvalidAction;
import xyz.luan.console.parser.actions.InvalidCall;
import xyz.luan.console.parser.actions.InvalidParameter;
import xyz.luan.console.parser.actions.Optional;

public class ControllerRef<T extends Controller<?>> {

    private T controller;
    private Map<String, Method> actions;
    // private Method exceptionHandler; TODO << exception handler

    public ControllerRef(T controller) throws InvalidAction {
        this.controller = controller;
        this.actions = new HashMap<>();
        for (Method method : controller.getClass().getMethods()) {
            Action action = method.getAnnotation(Action.class);
            if (action != null) {
                validateActionMethod(method);
                if (this.actions.get(action.value()) != null) {
                    throw new InvalidAction("Two actions on the same controller same action name...");
                }
                this.actions.put(action.value(), method);
            }
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
            throw new RuntimeException(""); //TODO << exception handler
            //return ExceptionHandler.handleController(e, controller.getClass().getCanonicalName());
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
    
    private void validateActionMethod(Method method) throws InvalidAction {
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
