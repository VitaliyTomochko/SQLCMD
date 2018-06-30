package com.vtom.sqlcmd.Controller.commands;


import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.View.View;

import java.util.function.Consumer;
import java.util.function.Function;

public class Connect extends Command {
    public static String COMMAND_SAMPLE = "connect|sqlcmd|sqlcmd|121213";

    public Connect(Function<String, String> function) {
        super(function);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

}
