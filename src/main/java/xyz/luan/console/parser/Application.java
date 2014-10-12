package xyz.luan.console.parser;

import xyz.luan.console.parser.call.CallResult;

public abstract class Application {

    public abstract Console createConsole();
    public abstract Context createContext();

    public abstract String startMessage();
    public abstract String endMessage();

    public abstract CallResult emptyLineHandler();
    
    public void run(String[] args) {
        Console console = createConsole();
        Context c = createContext();

        executeOrLoop(args, console, c);
        console.exit();
    }

    protected void executeOrLoop(String[] args, Console console, Context c) {
        if (args.length != 0) {
            c.execute(args);
        } else {
            console.message(startMessage());
            loop(console, c);
            console.message(endMessage());
        }
    }

    protected void loop(Console console, Context c) {
        for (;;) {
            String cmd = console.read();
            CallResult r = cmd.isEmpty() ? emptyLineHandler() : c.execute(cmd);
            if (r == CallResult.QUIT) {
                break;
            } else if (r == CallResult.INVALID_COMMAND) {
                console.error("Invalid command. Type help for a list of valid operations.");
            }
        }
    }
}
