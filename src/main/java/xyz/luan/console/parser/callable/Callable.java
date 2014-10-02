package xyz.luan.console.parser.callable;

import java.io.Serializable;
import java.util.Map;

import xyz.luan.console.parser.call.Call;

public interface Callable extends Serializable {

    public Call parse(String[] args, Map<String, String> aliases);

    public Pattern getPattern();

    public String getDescription();
}