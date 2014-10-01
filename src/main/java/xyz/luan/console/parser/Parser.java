package xyz.luan.console.parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser implements Serializable {

    private static final long serialVersionUID = 4644666847148733579L;

    private Map<String, String> aliases;
    private List<Callable> callables;

    public Parser() {
        this(new HashMap<String, String>(), new ArrayList<Callable>());
    }

    public Parser(Map<String, String> aliases, List<Callable> callables) {
        this.aliases = aliases;
        this.callables = callables;
    }

    public Call parse(String[] args) {
        if (args.length < 1) {
            return null;
        }

        for (Callable c : callables) {
            Call call = c.parse(args, aliases);
            if (call != null) {
                return call;
            }
        }

        return null; // no match
    }

    public void addCallable(Callable c) {
        this.callables.add(c);
    }

    public boolean removeCallable(int index) {
        if (index >= 0 && index < this.callables.size()) {
            this.callables.remove(index);
            return true;
        } else {
            return false;
        }
    }

    public CallResult listCallables(Console console) {
        return this.listCallables(console, "");
    }

    public CallResult listCallables(Console console, String start) {
        int actualStart = start.lastIndexOf(":");
        if (actualStart < 0) {
            actualStart = 0;
        }

        console.result("Listing all callables" + (start.isEmpty() ? "" : " starting with '" + start + "'") + ":");
        for (Callable c : callables) {
            String pattern = c.getPattern().toString();
            if (pattern.startsWith(start)) {
                console.result(pattern.substring(actualStart) + " - " + c.getDescription());
            }
        }

        return CallResult.SUCCESS;
    }

    public boolean addAlias(String alias, String keyword) {
        if (this.aliases.get(alias) != null) {
            return false;
        }
        this.aliases.put(alias, keyword);
        return true;
    }

    public boolean deleteAlias(String alias) {
        if (this.aliases.get(alias) == null) {
            return false;
        }
        this.aliases.remove(alias);
        return true;
    }

    public String getAlias(String alias) {
        return this.aliases.get(alias);
    }

    public CallResult listAliases(Console console) {
        return listAliasesFor(console, null);
    }

    public CallResult listAliasesFor(Console console, String keyword) {
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