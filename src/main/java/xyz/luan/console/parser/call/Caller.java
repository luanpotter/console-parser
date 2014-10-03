package xyz.luan.console.parser.call;

import java.util.HashMap;
import java.util.Map;

import xyz.luan.console.parser.Controller;
import xyz.luan.console.parser.ControllerRef;
import xyz.luan.console.parser.actions.InvalidAction;
import xyz.luan.console.parser.actions.InvalidHandler;

public class Caller {

    private Map<String, ControllerRef<?>> controllers;

    public Caller() {
        this.controllers = new HashMap<>();
    }

    public <T extends Controller<?>> void registerClass(String name, T controller) throws InvalidAction, InvalidHandler {
        controllers.put(name, new ControllerRef<T>(name, controller));
    }

    public CallResult call(Call call) {
        if (call == null) {
            return CallResult.INVALID_COMMAND;
        }
        return call.invoke(controllers);
    }

}