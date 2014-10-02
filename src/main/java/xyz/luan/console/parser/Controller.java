package xyz.luan.console.parser;

import xyz.luan.console.parser.actions.InvalidCall;
import xyz.luan.console.parser.actions.InvalidHandler;
import xyz.luan.console.parser.call.CallResult;

public abstract class Controller<T extends Context> {

    protected T context;
    protected Console console;

    public Controller<T> setup(T context, Console console) {
        this.context = context;
        this.console = console;
        return this;
    }
 
    @ExceptionHandler({InvalidCall.class, InvalidHandler.class})
    public CallResult handleInvalidMethod(Exception ex) {
    	console.error(ex.getMessage());
    	return CallResult.ERROR;
    }
}