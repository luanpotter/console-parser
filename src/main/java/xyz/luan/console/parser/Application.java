package xyz.luan.console.parser;

public abstract class Application {

    public abstract Console createConsole();
    public abstract Context createContext();

    public abstract String startMessage();
    public abstract String endMessage();

    public abstract boolean emptyLineHandler();
    
    public void run(String[] args) {
        Console console = createConsole();
        Context c = createContext();
        
        if (args != null) {
            c.execute(args);
        } else {
            console.message(startMessage());
            
            for (;;) {
                String cmd = console.read();
                if (cmd.isEmpty()) {
                    if (emptyLineHandler()) {
                        break;
                    }
                }
                CallResult r = c.execute(cmd);
                if (r == CallResult.QUIT) {
                    break;
                }
            }

            console.message(endMessage());
        }
        
        console.flush();
    }
}
