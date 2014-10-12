package xyz.luan.console.parser.config;

import java.util.List;

import xyz.luan.console.parser.Aliases;
import xyz.luan.console.parser.Context;
import xyz.luan.console.parser.Controller;
import xyz.luan.console.parser.actions.Action;
import xyz.luan.console.parser.actions.Arg;
import xyz.luan.console.parser.call.CallResult;
import xyz.luan.console.parser.callable.ActionCall;
import xyz.luan.console.parser.callable.Callable;

public class ConfigAliasesController extends Controller<Context> {

    @Action("add")
    public CallResult add(@Arg("alias") String alias, @Arg("keyword") String keyword) {
        boolean success = aliases().add(alias, keyword);
        console.result(success ? "Alias added successfully." : "Alias already set to something else.");
        return CallResult.SUCCESS;
    }

    @Action("list")
    public CallResult list(@Arg(value = "keyword", required = false) String keyword) {
        return aliases().listFor(console, keyword == null ? null : ':' + keyword);
    }
    
    @Action("remove")
    public CallResult remove(@Arg("alias") String alias) {
        boolean result = aliases().remove(alias);
        if (!result) {
            console.error("Alias not found, thus could not be removed.");
            return CallResult.ERROR;
        }
        return CallResult.SUCCESS;
    }
    
    @Action("removeAll")
    public CallResult removeAll() {
        aliases().removeAll();
        return CallResult.SUCCESS;
    }

    @Action("setDefault")
    public CallResult setDefault(@Arg("value") Boolean value) {
        aliases().setDefaultAliases(value);
        return CallResult.SUCCESS;
    }
    
    @Action("getDefault")
    public CallResult getDefault() {
        console.result("Enable default aliases: " + aliases().isDefaultAliasesEnabled());
        return CallResult.SUCCESS;
    }
    
    private Aliases aliases() {
        return context.getParser().getAliases();
    }

    public static void defaultActions(String name, List<Callable> callables) {
        callables.add(new ActionCall(name + ":add", ":config :aliases :add alias :to keyword", "Add an alias to keyword"));
        callables.add(new ActionCall(name + ":list", ":config :aliases :list", "List all aliases"));
        callables.add(new ActionCall(name + ":list", ":config :aliases :list keyword", "List all aliases for this keyword"));
        callables.add(new ActionCall(name + ":remove", ":config :aliases :remove alias", "Remove a specific alias"));
        callables.add(new ActionCall(name + ":removeAll", ":config :aliases :remove-all", "Remove all aliases"));
        callables.add(new ActionCall(name + ":set", ":config :aliases :set alias :to keyword", "Remove all alias"));
        callables.add(new ActionCall(name + ":getDefault", ":config :aliases :default", "Returns the default aliases option, i.e., if a :token should match with token even if not alias is added. Custom alias will still work."));
        callables.add(new ActionCall(name + ":setDefault", ":config :aliases :default value", "Sets wether to use default aliases, i.e., if a :token should match with token even if not alias is added. Custom alias will still work."));
    }
}