package xyz.luan.console.parser.config;

import java.util.List;

import xyz.luan.console.parser.Context;
import xyz.luan.console.parser.Controller;
import xyz.luan.console.parser.actions.Action;
import xyz.luan.console.parser.actions.ActionRef;
import xyz.luan.console.parser.call.CallResult;
import xyz.luan.console.parser.callable.ActionCall;
import xyz.luan.console.parser.callable.Callable;

public class ConfigCallablesController extends Controller<Context> {

    @Action("listActions")
    public CallResult listActions() {
        context.getCaller().forEachController((controllerRef) -> {
            console.result(controllerRef.getName());
            console.tabIn();
            controllerRef.forEachAction((action) -> {
                Action actionAnn = action.getAnnotation(Action.class);
                console.result(actionAnn.value());
            });
            console.tabOut();
        });
        return CallResult.SUCCESS;
    }
    
    @Action("add")
    public CallResult addSimpleAction() {
        //ActionCall(ActionRef actionRef, Pattern pattern, Map<String, String> argsValues, Map<String, String> argsMapping, String description) {
        selectActionRef();
        //Callable c = new ActionCall();
        //context.getParser().addCallable(c);
        return CallResult.ERROR;
    }
    
    private ActionRef selectActionRef() {
        return null;
    }

    public static void defaultActions(String name, List<Callable> callables) {
        callables.add(new ActionCall(name + ":listActions", ":config :actions :list", "List all actions, per controller"));
    }
}