package com.vtom.sqlcmd.Controller.commands;

import java.util.function.Function;

public class Clear  extends Command {
    public Clear(Function<String, String> function) {
        super(function);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

}
