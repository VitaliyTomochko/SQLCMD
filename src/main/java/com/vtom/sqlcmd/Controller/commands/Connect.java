package com.vtom.sqlcmd.Controller.commands;


import java.util.function.Function;

public class Connect extends Command {
    public static String COMMAND_SAMPLE_CONNECT = "connect|sqlcmd|sqlcmd|123321";

    public Connect(Function<String, String> function) {
        super(function);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

}
