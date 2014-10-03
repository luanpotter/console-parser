package xyz.luan.console.parser.actions;

public class InvalidCall extends Exception {

    private static final long serialVersionUID = -1847854306985567286L;

    public InvalidCall(String message) {
        super(message);
    }
    
    public InvalidCall(String action, Exception cause) {
        super("Invalid call of action '" + action + "'; error: " + cause.getMessage(), cause);
    }

    public InvalidCall(String action, String message) {
        super("Invalid call of method '" + action + "'; error: " + message);
    }

}
