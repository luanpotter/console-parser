package xyz.luan.console.parser;

import java.io.Serializable;

import xyz.luan.console.parser.call.CallResult;
import xyz.luan.console.parser.call.Caller;

public abstract class Context implements Serializable {

    private static final long serialVersionUID = -893098040420691058L;

    protected Parser parser;
    protected Caller caller;

    public void setup(Parser parser, Caller caller) {
        this.parser = parser;
        this.caller = caller;
    }

    public Parser getParser() {
        return this.parser;
    }

    public Caller getCaller() {
        return this.caller;
    }

    public CallResult execute(String string) {
        return execute(string.split(" "));
    }
    
    public CallResult execute(String[] params) {
        return caller.call(parser.parse(params));
    }
}