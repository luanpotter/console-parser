package xyz.luan.console.parser;

public interface Console {

    public void message(Object m);
    public void result(Object m);
    public void error(Object m);

    public void tabIn();
    public void tabOut();
    public void tab(int amount);
    public void resetTab();

    public String read();
    public char[] readPassword();
    
    public void exit();
}
