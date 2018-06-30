package com.vtom.sqlcmd.Controller.commands;


import java.util.function.Function;

public class TableList extends Command {
    public TableList(Function<String, String> function) {
        super(function);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("tables");
    }
}
