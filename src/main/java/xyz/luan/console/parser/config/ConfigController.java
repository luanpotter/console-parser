package xyz.luan.console.parser.config;

import java.util.List;

import xyz.luan.console.parser.Context;
import xyz.luan.console.parser.Controller;
import xyz.luan.console.parser.actions.Action;
import xyz.luan.console.parser.actions.Arg;
import xyz.luan.console.parser.call.CallResult;
import xyz.luan.console.parser.callable.ActionCall;
import xyz.luan.console.parser.callable.Callable;

public class ConfigController extends Controller<Context> {

    @Action("add")
    public CallResult add(@Arg("alias") String alias, @Arg("keyword") String keyword) {
        boolean success = context.getParser().getAliases().add(alias, keyword);
        console.result(success ? "Alias added successfully." : "Alias already set to something else.");
        return CallResult.SUCCESS;
    }

    @Action("list")
    public CallResult list(@Arg(value = "keyword", required = false) String keyword) {
        return context.getParser().getAliases().listFor(console, keyword == null ? null : ':' + keyword);
    }

    public static void defaultActions(String name, List<Callable> callables) {
        callables.add(new ActionCall(name + ":add", ":config :aliases :add alias :to keyword", "Add an alias to keyword"));
        callables.add(new ActionCall(name + ":list", ":config :aliases :list", "List all aliases"));
        callables.add(new ActionCall(name + ":list", ":config :aliases :list keyword", "List all aliases for this keyword"));
    }
}