package com.vtom.sqlcmd.Controller.commands;

import java.util.function.Function;

public class Find  extends Command {
    public static String COMMAND_SAMPLE_FIND = "find|tableName";

    public Find(Function<String, String> function) {
        super(function);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }
}

