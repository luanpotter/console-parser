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

    public Output call(Call[] calls) {
        Output results = new Output();
        if (calls == null) {
            results.add("Command not recognized. Type help for help.");
        } else {
            for (Call call : calls) {
                results.append(call(call));
            }
        }

        return results;
    }

    public Output call(Call call) {
        return call.invoke(controllers);
    }

}