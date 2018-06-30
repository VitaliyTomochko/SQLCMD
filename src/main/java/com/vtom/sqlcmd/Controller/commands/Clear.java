package com.vtom.sqlcmd.Controller.commands;

import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.View.View;

import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class Clear  extends Command {
    public Clear(Function<String, String> function) {
        super(function);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

}
