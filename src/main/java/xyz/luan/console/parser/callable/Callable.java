package xyz.luan.console.parser.callable;

import java.io.Serializable;

import xyz.luan.console.parser.Aliases;
import xyz.luan.console.parser.call.Call;

public interface Callable extends Serializable {

    public Call parse(String[] args, Aliases aliases);

    public Pattern getPattern();

    public String getDescription();
}