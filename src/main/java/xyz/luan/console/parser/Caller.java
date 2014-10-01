package xyz.luan.console.parser;

import java.util.HashMap;
import java.util.Map;

import xyz.luan.console.parser.actions.InvalidAction;

public class Caller {

    private Map<String, ControllerRef<?>> controllers;

    public Caller() {
        this.controllers = new HashMap<>();
    }

    public <T extends Controller<?>> void registerClass(String name, T controller) throws InvalidAction {
        controllers.put(name, new ControllerRef<T>(controller));
    }

    public CallResult call(Call call) {
        return call.invoke(controllers);
    }

}