package com.vtom.sqlcmd.Controller.commands;

import java.util.function.Function;

public class Update extends Command {
    public Update(Function<String, String> function) {
        super(function);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("update|");
    }
}
