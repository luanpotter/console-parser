package xyz.luan.console.parser.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xyz.luan.console.parser.Action;
import xyz.luan.console.parser.Callable;
import xyz.luan.console.parser.Context;
import xyz.luan.console.parser.Controller;
import xyz.luan.console.parser.Keyword;
import xyz.luan.console.parser.Output;
import xyz.luan.console.parser.Parser;
import xyz.luan.console.parser.Pattern;

public class ConfigController extends Controller<Context> {

    public static final String[] MESSAGES = { "Alias added successfully.", "Alias already set to something else." };

    public Output addAlias(Map<String, String> args) {
        Controller.required(args, "alias", "keyword");

        boolean results = context.getParser().addAlias(args.get("alias"), args.get("keyword"));

        return new Output(MESSAGES[results ? 0 : 1]);
    }

    public Output listAliases(Map<String, String> args) {
        Controller.optional(args, "keyword");
        return context.getParser().listAliasesFor(args.get("keyword") == null ? null : ':' + args.get("keyword"));
    }

    public Output listKeywords(Map<String, String> args) {
        Controller.optional(args, "group", "keyword");
        String groupName = args.get("group");
        if (groupName == null) {
            return Parser.listKeywords();
        }
        Class<? extends Keyword> group = Parser.getGroupByName(groupName);
        if (group == null) {
            return new Output("Invalid group name. No keyword group found with that name.");
        }
        String keywordName = args.get("keyword");
        if (keywordName == null) {
            return Parser.listKeywordsForGroup(group);
        }
        Keyword keyword = Parser.getKeywordByNameAndGroup(group, keywordName);
        if (keyword == null) {
            return new Output("Invalid keyword name. No keyword found with that name.");
        }
        return new Output("Keyword is " + keyword);
    }

    public static List<Callable> getDefaultActions() {
        List<Callable> callables = new ArrayList<>(1);

        callables.add(new Action(ConfigKeyword.LIST_KEYWORDS, new Pattern(":config :keywords"), "List all keywords"));
        callables.add(new Action(ConfigKeyword.LIST_KEYWORDS, new Pattern(":config :keywords group"), "List all keywords in the group"));
        callables.add(new Action(ConfigKeyword.LIST_KEYWORDS, new Pattern(":config :keywords group keyword"),
                "Show information about a keyword"));
        callables
                .add(new Action(ConfigKeyword.ADD_ALIAS, new Pattern(":config :aliases :add alias :to keyword"), "Add an alias to keyword"));
        callables.add(new Action(ConfigKeyword.LIST_ALIASES, new Pattern(":config :aliases :list"), "List all aliases"));
        callables.add(new Action(ConfigKeyword.LIST_ALIASES, new Pattern(":config :aliases :list keyword"),
                "List all aliases for this keyword"));

        return callables;
    }
}