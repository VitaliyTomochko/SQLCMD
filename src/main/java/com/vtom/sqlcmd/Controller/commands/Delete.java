package com.vtom.sqlcmd.Controller.commands;

import java.util.function.Function;

public class Delete extends Command {
    public Delete(Function<String, String> function) {
        super(function);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("delete|");
    }
}

