package xyz.luan.console.parser.actions;

import java.lang.reflect.Method;

public class InvalidAction extends Exception {

    private static final long serialVersionUID = -5146327797265733979L;

    public InvalidAction(String message) {
        super(message);
    }

    public InvalidAction(Method method, String error) {
        super(String.format("Action %s:%s is invalid: %s", method.getDeclaringClass().getName(), method.getName(), error));
    }
}
