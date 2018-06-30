package com.vtom.sqlcmd.Controller.commands;

import java.util.function.Function;

public class Create extends Command {

    public Create(Function<String, String> function) {
        super(function);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }
}
