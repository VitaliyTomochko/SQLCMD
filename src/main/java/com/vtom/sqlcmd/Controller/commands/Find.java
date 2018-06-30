package com.vtom.sqlcmd.Controller.commands;

import com.vtom.sqlcmd.Model.Data;
import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.View.View;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Find  extends Command {
    public Find(Function<String, String> function) {
        super(function);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

}

