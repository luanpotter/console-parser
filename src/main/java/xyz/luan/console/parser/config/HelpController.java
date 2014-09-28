package xyz.luan.console.parser.config;

import java.util.List;

import xyz.luan.console.parser.ActionCall;
import xyz.luan.console.parser.ActionRef;
import xyz.luan.console.parser.Callable;
import xyz.luan.console.parser.Context;
import xyz.luan.console.parser.Controller;
import xyz.luan.console.parser.Output;
import xyz.luan.console.parser.Pattern;
import xyz.luan.console.parser.actions.Action;
import xyz.luan.console.parser.actions.Required;

public class HelpController extends Controller<Context> {

    @Action("list")
    public Output list() {
        return context.getParser().listCallables();
    }

    @Action("show")
    public Output show(@Required String command) {
        return context.getParser().listCallables(command);
    }

    public static void defaultActions(String name, List<Callable> callables) {
        callables.add(new ActionCall(name + ":list", ":help", "List all callables"));
        callables.add(new ActionCall(new ActionRef(name, "show"), new Pattern(":help command", true), "List all callables starting with command"));
    }
}