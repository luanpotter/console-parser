package xyz.luan.console.parser;


public abstract class Controller<T extends Context> {

    protected T context;

    public Controller<T> setContext(T context) {
        this.context = context;
        return this;
    }
}