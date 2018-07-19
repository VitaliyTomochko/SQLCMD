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
        buffer.append("Commands:\n");
        buffer.append("\tdrop   |tableName\n");
        buffer.append("\tfind   |tableName\n");
        buffer.append("\tclear  |tableName\n");
        buffer.append("\tconnect|databaseName|userName|password\n");
        buffer.append("\tcreate |tableName |column1 |column2| ... | columnN\n");
        buffer.append("\tupdate |tableName |column1 |value1 | column2 | value2\n");
        buffer.append("\tinsert |tableName |column1 |value1 | column2 | value2 | ... | columnN | valueN\n");
        buffer.append("\tdelete |tableName |column  |value\n");
        buffer.append("\ttables\n");
        buffer.append("\thelp\n");
        buffer.append("\texit\n");
        return buffer.toString();
    }
}
