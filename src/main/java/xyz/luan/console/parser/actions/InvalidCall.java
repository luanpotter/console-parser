package xyz.luan.console.parser.actions;

import java.lang.reflect.Method;

public class InvalidCall extends Exception {

    private static final long serialVersionUID = -1847854306985567286L;

    public InvalidCall(String message) {
        super(message);
    }
    
    public InvalidCall(Method method, Exception cause) {
        super("Invalid call of method '" + method + "'; error: " + cause.getMessage(), cause);
    }

    public InvalidCall(Method method, String message) {
        super("Invalid call of method '" + method + "'; error: " + message);
    }

}
