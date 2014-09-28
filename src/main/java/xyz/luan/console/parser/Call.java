package xyz.luan.console.parser;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import xyz.luan.console.parser.actions.InvalidCall;

public class Call implements Serializable {

    private static final long serialVersionUID = -1477714211952802085L;

    private ActionRef actionRef;
    private Map<String, String> args;

    public Call(ActionRef actionRef, Map<String, String> args) {
        this.actionRef = actionRef;
        this.args = args;
    }

    public ActionRef getActionRef() {
        return actionRef;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public Call newCall(Map<String, String> newArgs) {
        Map<String, String> args = new HashMap<>(this.args);
        args.putAll(newArgs);

        return new Call(this.actionRef, args);
    }

    public Output invoke(Map<String, ControllerRef<?>> controllers) {
        ControllerRef<?> controller = controllers.get(actionRef.getController());
        if (controller == null) {
            InvalidCall ex = new InvalidCall(String.format("Controller '%s' not found", actionRef.getController()));
            return ExceptionHandler.handleController(ex, actionRef.getController());
        }
        return controller.call(actionRef.getAction(), args);
    }
}
