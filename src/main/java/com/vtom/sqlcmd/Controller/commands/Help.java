package com.vtom.sqlcmd.Controller.commands;

import com.vtom.sqlcmd.View.View;

public class Help implements ICommand {
    public Help() {
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("help");
    }

    @Override
    public String process(String command) {
        StringBuffer buffer=new StringBuffer();
        buffer.append("Commands:");
        buffer.append("\tconnect|databaseName|userName|password");
        buffer.append("\tlist");
        buffer.append("\tclear|tableName");
        buffer.append("\tcreate|tableName|column1|value1|column2|value2|...|columnN|valueN");
        buffer.append("\tfind|tableName");
        buffer.append("\thelp");
        buffer.append("\texit");
        return buffer.toString();
    }
}
