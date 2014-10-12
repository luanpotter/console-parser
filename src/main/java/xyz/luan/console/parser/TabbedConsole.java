package xyz.luan.console.parser;

public abstract class TabbedConsole implements Console {

    protected int tabLevel;
    
    protected String defaultTabs() {
        return multiply("\t", tabLevel);
    }
    
    protected String multiply(String token, int amount) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            builder.append(token);
        }
        return builder.toString();
    }
    
    @Override
    public void tabIn() {
        tabLevel++;
    }

    @Override
    public void tabOut() {
        if (tabLevel == 0) {
            throw new RuntimeException("Tryed to tab out too much! Already at level 0. Did you forget to tab in somewhere?");
        }
        tabLevel--;
    }

    @Override
    public void tab(int amount) {
        tabLevel += amount;
    }

    @Override
    public void resetTab() {
        tabLevel = 0;
    }

}
