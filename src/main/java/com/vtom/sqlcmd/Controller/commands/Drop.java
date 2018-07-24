package com.vtom.sqlcmd.Controller.commands;

import java.util.function.Function;

public class Drop extends Command {
    public static String COMMAND_SAMPLE_DROP = "drop|tableName";

    public Drop(Function<String, String> function) {
        super(function);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("drop|");
    }
}
