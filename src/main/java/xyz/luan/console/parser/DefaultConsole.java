package xyz.luan.console.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DefaultConsole extends TabbedConsole {

    private BufferedReader reader;

    public DefaultConsole() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        tabLevel = 0;
    }

    @Override
    public void message(Object m) {
        System.out.println(defaultTabs() + m);
    }

    @Override
    public void result(Object m) {
        System.out.println(defaultTabs() + m);
    }

    @Override
    public void error(Object m) {
        System.err.println(defaultTabs() + m);
    }

    @Override
    public String read() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public char[] readPassword() {
        try {
            return reader.readLine().toCharArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exit() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
