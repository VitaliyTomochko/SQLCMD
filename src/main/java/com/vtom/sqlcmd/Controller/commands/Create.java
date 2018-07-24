package com.vtom.sqlcmd.Controller.commands;

import java.util.function.Function;

public class Create extends Command {
    public static String COMMAND_SAMPLE_CREATE = "create|tablename|id SERIAL NOT NULL PRIMARY KEY|username varchar(225) NOT NULL UNIQUE| password varchar(225)";

    public Create(Function<String, String> function) {
        super(function);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }
}
