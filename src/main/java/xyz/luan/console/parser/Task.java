package xyz.luan.console.parser;

import java.util.List;
import java.util.Map;

public class Task implements Callable {

    private static final long serialVersionUID = -4785830935180783565L;

    private String description;
    private Pattern pattern;
    private List<ActionCall> actions;

    public Task(Pattern pattern, List<ActionCall> actions, String description) {
        this.pattern = pattern;
        this.actions = actions;
        this.description = description;
    }

    @Override
    public Call[] parse(String[] args, Map<String, String> aliases) {
        Map<String, String> map = pattern.parse(args, aliases);
        if (map == null) {
            return null;
        }

        Call[] calls = new Call[actions.size()];
        for (int i = 0; i < actions.size(); i++) {
            calls[i] = actions.get(i).parseAction(args, map);
        }

        return calls;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Pattern getPattern() {
        return this.pattern;
    }
}