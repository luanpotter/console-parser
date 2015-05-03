package xyz.luan.console.parser;

import xyz.luan.console.parser.call.CallResult;

public abstract class Application {

    public abstract Console createConsole();
    public abstract Context createContext();

    public abstract String startMessage();
    public abstract String endMessage();

    protected void wrongCommandHandler(Console console) {
        console.error("Invalid command. Type help for a list of valid operations.");
    }

    public abstract CallResult emptyLineHandler();

    private void handleWrongCommand(CallResult r, Console console) {
        if (r == CallResult.INVALID_COMMAND) {
            wrongCommandHandler(console);
        }
    }

    public int run(String[] args) {
        Console console = createConsole();
        Context c = createContext();

        int responseCode = executeOrLoop(args, console, c);
        console.exit();
        return responseCode;
    }

    protected int executeOrLoop(String[] args, Console console, Context c) {
        if (args.length != 0) {
            CallResult r = c.execute(args);
            handleWrongCommand(r, console);
            return r.getResponseCode();
        } else {
            console.message(startMessage());
            loop(console, c);
            console.message(endMessage());
        }
        return CallResult.SUCCESS.getResponseCode();
    }

    protected void loop(Console console, Context c) {
        for (;;) {
            String cmd = console.read();
            CallResult r = cmd.isEmpty() ? emptyLineHandler() : c.execute(cmd);
            if (r == CallResult.QUIT) {
                break;
            } else {
                handleWrongCommand(r, console);
            }
        }
    }

}
