package xyz.luan.console.parser.actions;

import java.io.Serializable;

public class ActionRef implements Serializable {

    private static final long serialVersionUID = 3677009974829679163L;

    private String controller;
    private String action;
    
    public ActionRef(String actionRef) {
        String[] halves = actionRef.split(":");
        assert halves.length == 2;
        this.controller = halves[0];
        this.action = halves[1];
    }

    public ActionRef(String controller, String action) {
        this.controller = controller;
        this.action = action;
    }

    public String getController() {
        return controller;
    }

    public String getAction() {
        return action;
    }
}
