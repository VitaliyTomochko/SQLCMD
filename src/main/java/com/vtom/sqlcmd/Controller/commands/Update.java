package com.vtom.sqlcmd.Controller.commands;

import java.util.function.Function;

public class Update extends Command {
    public static String COMMAND_SAMPLE_UPDATE = "update|tableName|column1|value1|column2|value2";

    public Update(Function<String, String> function) {
        super(function);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("update|");
    }
}
