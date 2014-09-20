package xyz.luan.console.parser;

import java.io.Serializable;
import java.util.Map;

public interface Callable extends Serializable {

    public Call[] parse(String[] args, Map<String, String> aliases);

    public Pattern getPattern();

    public String getDescription();
}