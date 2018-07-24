package com.vtom.sqlcmd.Controller.commands;

public class Help implements ICommand {
    public Help() {
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("help");
    }

    @Override
    public String process(String command) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Commands:\n");
        buffer.append("Команда видаляє задану таблицю \n\tdrop   |tableName\n");
        buffer.append("Команда для получения содержимого указанной таблицы \n\tfind   |tableName\n");
        buffer.append("Команда очищає вміст зазначеної (всієї) таблиці \n\tclear  |tableName\n");
        buffer.append("Команда для підключення до відповідної БД \n\tconnect|databaseName|userName|password\n");
        buffer.append("Команда створює нову таблицю з заданими полями \n\tcreate |tableName |column1 |column2| ... | columnN\n");
        buffer.append("Команда оновить запис, встановивши значення column2 = value2, для якої дотримується умова column1 = value1 \n\tupdate |tableName |column1 |value1 | column2 | value2\n");
        buffer.append("Команда для вставки одного рядка в задану таблицю \n\tinsert |tableName |column1 |value1 | column2 | value2 | ... | columnN | valueN\n");
        buffer.append("Команда видаляє одну або кілька записів для яких виконується умова column = value \n\tdelete |tableName |column  |value\n");
        buffer.append("Команда виводить список всіх таблиць: \n\ttables\n");
        buffer.append("Команда виводить в консоль список всіх доступних команд \n\thelp\n");
        buffer.append("Команда для відключення від БД і вихід з програми \n\texit\n");
        return buffer.toString();
    }
}
