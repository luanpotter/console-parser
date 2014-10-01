package xyz.luan.console.parser;


public abstract class Controller<T extends Context> {

    protected T context;
    protected Console console;

    public Controller<T> setup(T context, Console console) {
        this.context = context;
        this.console = console;
        return this;
    }
}