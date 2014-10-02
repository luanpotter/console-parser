package xyz.luan.console.parser.config;

import java.util.List;

import xyz.luan.console.parser.Context;
import xyz.luan.console.parser.Controller;
import xyz.luan.console.parser.actions.Action;
import xyz.luan.console.parser.actions.ActionRef;
import xyz.luan.console.parser.actions.Arg;
import xyz.luan.console.parser.call.CallResult;
import xyz.luan.console.parser.callable.ActionCall;
import xyz.luan.console.parser.callable.Callable;
import xyz.luan.console.parser.callable.Pattern;

public class HelpController extends Controller<Context> {

    @Action("list")
    public CallResult list() {
        return context.getParser().listCallables(console);
    }

    @Action("show")
    public CallResult show(@Arg("command") String command) {
        return context.getParser().listCallables(console, command);
    }

    public static void defaultActions(String name, List<Callable> callables) {
        callables.add(new ActionCall(name + ":list", ":help", "List all callables"));
        callables.add(new ActionCall(new ActionRef(name, "show"), new Pattern(":help command", true), "List all callables starting with command"));
    }
}