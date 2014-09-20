package xyz.luan.console.parser.config;

import xyz.luan.console.parser.Keyword;

public enum ConfigKeyword implements Keyword {
    LIST_KEYWORDS("config:listKeywords"),

    ADD_ALIAS("config:addAlias"), LIST_ALIASES("config:listAliases"), REMOVE_ALIAS("config:removeAlias");

    private String controller;

    private ConfigKeyword(String controller) {
        this.controller = controller;
    }

    public String getController() {
        return this.controller;
    }
}