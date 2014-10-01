package xyz.luan.console.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DefaultConsole implements Console {

    private BufferedReader reader;

    public DefaultConsole() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void message(Object m) {
        System.out.println(m);
    }

    @Override
    public void result(Object m) {
        System.out.println(m);
    }

    @Override
    public void error(Object m) {
        System.err.println(m);
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
