package xyz.luan.console.parser.actions;

public class InvalidParameter extends Exception {

    private static final long serialVersionUID = -571171754932190180L;

    public InvalidParameter(Class<?> expectedType, String stringValue) {
        this(expectedType, stringValue, null);
    }

    public InvalidParameter(Class<?> expectedType, String stringValue, Exception cause) {
        super(String.format(" expected a '%s' but value found '%s' can't be converted.", expectedType.getSimpleName(), stringValue), cause);
    }
}
