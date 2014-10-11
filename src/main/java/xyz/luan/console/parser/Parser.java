package xyz.luan.console.parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.luan.console.parser.call.Call;
import xyz.luan.console.parser.call.CallResult;
import xyz.luan.console.parser.callable.Callable;

public class Parser implements Serializable {

    private static final long serialVersionUID = 4644666847148733579L;

    private Aliases aliases;
    private List<Callable> callables;

    public Parser() {
        this(true, new HashMap<String, String>(), new ArrayList<Callable>());
    }

    public Parser(Map<String, String> aliases, List<Callable> callables) {
        this(false, aliases, callables);
    }
    
    public Parser(boolean enableDefaultAliases, Map<String, String> aliases, List<Callable> callables) {
        this.aliases = new Aliases(aliases, enableDefaultAliases);
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

    public Aliases getAliases() {
        return this.aliases;
    }

}