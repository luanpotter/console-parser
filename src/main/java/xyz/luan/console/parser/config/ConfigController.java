package xyz.luan.console.parser.config;

import java.util.List;

import xyz.luan.console.parser.ActionCall;
import xyz.luan.console.parser.CallResult;
import xyz.luan.console.parser.Callable;
import xyz.luan.console.parser.Context;
import xyz.luan.console.parser.Controller;
import xyz.luan.console.parser.actions.Action;
import xyz.luan.console.parser.actions.Arg;

public class ConfigController extends Controller<Context> {

    @Action("add")
    public CallResult add(@Arg("alias2") String alias, @Arg("keyword") String keyword) {
        boolean success = context.getParser().addAlias(alias, keyword);
        console.result(success ? "Alias added successfully." : "Alias already set to something else.");
        return CallResult.SUCCESS;
    }

    @Action("list")
    public CallResult list(@Arg(value = "keyword", required = false) String keyword) {
        return context.getParser().listAliasesFor(console, keyword == null ? null : ':' + keyword);
    }

    public static void defaultActions(String name, List<Callable> callables) {
        callables.add(new ActionCall(name + ":add", ":config :aliases :add alias2 :to keyword", "Add an alias to keyword"));
        callables.add(new ActionCall(name + ":list", ":config :aliases :list", "List all aliases"));
        callables.add(new ActionCall(name + ":list", ":config :aliases :list keyword", "List all aliases for this keyword"));
    }
}