package xyz.luan.console.parser.actions;

import java.lang.reflect.Method;

public class InvalidHandler extends Exception {

	private static final long serialVersionUID = -5146327797265733979L;

	public InvalidHandler(String message) {
		super(message);
	}

	public InvalidHandler(Method method, String error) {
		super(String.format("Handler %s:%s is invalid: %s", method.getDeclaringClass().getName(), method.getName(), error));
	}
}