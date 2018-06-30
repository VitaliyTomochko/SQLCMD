package com.vtom.sqlcmd.Controller.commands;

import java.util.function.Function;

public abstract class Command implements ICommand {
    protected final Function<String, String> function;

    public Command(Function<String, String> function) {
        this.function = function;
    }

    public String process(String command) {
        try {
            return function.apply(command);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
