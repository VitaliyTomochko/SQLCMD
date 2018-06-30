package com.vtom.sqlcmd.Controller.commands;

import com.vtom.sqlcmd.View.View;

public class NotExisted implements ICommand {
    public NotExisted() {
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public String process(String command) {
        return "No such command! " + command;
    }
}
