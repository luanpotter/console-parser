package xyz.luan.console.parser.config;

import java.util.List;

import xyz.luan.console.parser.ActionCall;
import xyz.luan.console.parser.Callable;
import xyz.luan.console.parser.Context;
import xyz.luan.console.parser.Controller;
import xyz.luan.console.parser.Output;
import xyz.luan.console.parser.actions.Action;
import xyz.luan.console.parser.actions.Required;

public class ConfigController extends Controller<Context> {

    @Action("add")
    public Output add(@Required String alias, @Required String keyword) {
        boolean success = context.getParser().addAlias(alias, keyword);
        return new Output(success ? "Alias added successfully." : "Alias already set to something else.");
    }

    @Action("list")
    public Output list(String keyword) {
        return context.getParser().listAliasesFor(keyword == null ? null : ':' + keyword);
    }

    public static void defaultActions(String name, List<Callable> callables) {
        callables.add(new ActionCall(name + ":add", ":config :aliases :add alias :to keyword", "Add an alias to keyword"));
        callables.add(new ActionCall(name + ":list", ":config :aliases :list", "List all aliases"));
        callables.add(new ActionCall(name + ":list", ":config :aliases :list keyword", "List all aliases for this keyword"));
    }
}