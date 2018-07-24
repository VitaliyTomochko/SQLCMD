package com.vtom.sqlcmd.Controller.commands;

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
