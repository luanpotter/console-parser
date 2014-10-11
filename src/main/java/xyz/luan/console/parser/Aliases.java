package xyz.luan.console.parser;

import java.util.HashMap;
import java.util.Map;

import xyz.luan.console.parser.call.CallResult;

public class Aliases {
    private Map<String, String> aliases;
    private boolean enableDefaultAliases;

    public static Aliases createAliasesWithDefaults() {
        return new Aliases(new HashMap<>(), true);
    }

    public static Aliases createAliasesWithoutDefaults(Map<String, String> map) {
        return new Aliases(map, false);
    }
    
    public Aliases(Map<String, String> aliases, boolean enableDefaultAliases) {
        this.aliases = aliases;
        this.enableDefaultAliases = enableDefaultAliases;
    }
    
    public boolean add(String alias, String keyword) {
        if (this.aliases.get(alias) != null) {
            return false;
        }
        this.aliases.put(alias, keyword);
        return true;
    }

    public boolean delete(String alias) {
        if (this.aliases.get(alias) == null) {
            return false;
        }
        this.aliases.remove(alias);
        return true;
    }

    public String get(String alias) {
        String res = this.aliases.get(alias);
        if (this.enableDefaultAliases && res == null) {
            return ':' + alias; 
        }
        return res;
    }
    
    public CallResult list(Console console) {
        return listFor(console, null);
    }

    public CallResult listFor(Console console, String keyword) {
        for (Map.Entry<String, String> entry : aliases.entrySet()) {
            if (keyword == null) {
                console.result(entry.getKey() + ": " + entry.getValue().substring(1));
            } else {
                if (keyword.equals(entry.getValue())) {
                    console.result(entry.getKey());
                }
            }
        }

        return CallResult.SUCCESS;
    }
}