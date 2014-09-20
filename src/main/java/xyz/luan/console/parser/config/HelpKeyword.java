package xyz.luan.console.parser.config;

import xyz.luan.console.parser.Keyword;

public enum HelpKeyword implements Keyword {
    LIST("help:list"), SHOW("help:show");

    private String controller;

    private HelpKeyword(String controller) {
        this.controller = controller;
    }

    public String getController() {
        return this.controller;
    }
}