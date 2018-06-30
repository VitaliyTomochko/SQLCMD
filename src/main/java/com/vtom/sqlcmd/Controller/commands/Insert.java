package com.vtom.sqlcmd.Controller.commands;

import java.util.function.Function;

public class Insert extends Command {

    public Insert(Function<String, String> function) {
        super(function);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("insert|");
    }
}
