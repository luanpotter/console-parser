package xyz.luan.console.parser;

import java.util.HashMap;
import java.util.Map;

import xyz.luan.console.parser.actions.InvalidCall;

public class SingleCall implements Call {

    private static final long serialVersionUID = -1477714211952802085L;

    private ActionRef actionRef;
    private Map<String, String> args;

    public SingleCall(ActionRef actionRef, Map<String, String> args) {
        this.actionRef = actionRef;
        this.args = args;
    }

    public Call copy(Map<String, String> newArgs) {
        Map<String, String> args = new HashMap<>(this.args);
        args.putAll(newArgs);

        return new SingleCall(this.actionRef, args);
    }

    public CallResult invoke(Map<String, ControllerRef<?>> controllers) {
        ControllerRef<?> controller = controllers.get(actionRef.getController());
        if (controller == null) {
            InvalidCall ex = new InvalidCall(String.format("Controller '%s' not found", actionRef.getController()));
            throw new RuntimeException(ex); //TODO exception handling
            //return ExceptionHandler.handleController(ex, actionRef.getController());
        }
        return controller.call(actionRef.getAction(), args);
    }
}
