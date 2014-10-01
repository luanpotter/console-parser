package xyz.luan.console.parser;

import java.util.HashMap;
import java.util.Map;

public class ActionCall implements Callable {

    private static final long serialVersionUID = 5347353630808864913L;

    private String description;
    private Pattern pattern;
    private SingleCall predefCall;
    private Map<String, String> argsMapping;

    public ActionCall(String actionRef, String pattern, String description) {
        this(actionRef, pattern, new HashMap<>(), description);
    }

    public ActionCall(String actionRef, String pattern, Map<String, String> argsValues, String description) {
        this(new ActionRef(actionRef), new Pattern(pattern), argsValues, description);
    }
    
    public ActionCall(ActionRef actionRef, Pattern pattern, String description) {
        this(actionRef, pattern, new HashMap<>(), description);
    }

    public ActionCall(ActionRef actionRef, Pattern pattern, Map<String, String> argsValues, String description) {
        this(actionRef, pattern, argsValues, null, description);
    }

    public ActionCall(ActionRef actionRef, Pattern pattern, Map<String, String> argsValues, Map<String, String> argsMapping, String description) {
        this.pattern = pattern;
        this.predefCall = new SingleCall(actionRef, argsValues);
        this.argsMapping = argsMapping;
        this.description = description;
    }

    public Call parseAction(String[] args, Map<String, String> map) {
        if (map == null) {
            return null; // no match
        }

        if (argsMapping != null) {
            Map<String, String> realMap = new HashMap<>();
            for (String actualArgument : argsMapping.keySet()) {
                realMap.put(actualArgument, map.get(argsMapping.get(actualArgument)));
            }
            map = realMap;
        }

        return predefCall.copy(map);
    }

    @Override
    public Call parse(String[] args, Map<String, String> aliases) {
        return parseAction(args, pattern.parse(args, aliases));
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
