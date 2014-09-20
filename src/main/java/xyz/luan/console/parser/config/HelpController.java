package xyz.luan.console.parser.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xyz.luan.console.parser.Action;
import xyz.luan.console.parser.Callable;
import xyz.luan.console.parser.Context;
import xyz.luan.console.parser.Controller;
import xyz.luan.console.parser.Output;
import xyz.luan.console.parser.Pattern;

public class HelpController extends Controller<Context> {

    public Output list(Map<String, String> args) {
        Controller.empty(args);
        return context.getParser().listCallables();
    }

    public Output show(Map<String, String> args) {
        Controller.required(args, "command");
        return context.getParser().listCallables(args.get("command"));
    }

    public static List<Callable> getDefaultActions() {
        List<Callable> callables = new ArrayList<>(1);

        callables.add(new Action(HelpKeyword.LIST, new Pattern(":help"), "List all callables"));
        callables.add(new Action(HelpKeyword.SHOW, new Pattern(":help command", true), "List all callables starting with command"));

        return callables;
    }
}