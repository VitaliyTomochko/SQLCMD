package com.vtom.sqlcmd.Controller.commands;


public class Exit implements ICommand {

    public Exit() {
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("exit");
    }

    @Override
    public String process(String command) {

        throw new ExitException("програма завершила роботу");
    }
}
