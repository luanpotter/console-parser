package xya.luan.console.parser.config;

import xya.luan.console.parser.Keyword;

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