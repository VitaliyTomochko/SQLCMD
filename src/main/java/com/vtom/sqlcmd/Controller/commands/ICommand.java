package com.vtom.sqlcmd.Controller.commands;

public interface ICommand {
    boolean canProcess(String command);

    String process(String command);
}
