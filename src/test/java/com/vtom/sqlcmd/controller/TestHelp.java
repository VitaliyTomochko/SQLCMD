package com.vtom.sqlcmd.controller;

import com.vtom.sqlcmd.Controller.FunctionFactory;
import com.vtom.sqlcmd.Controller.commands.Command;
import com.vtom.sqlcmd.Controller.commands.Find;
import com.vtom.sqlcmd.Controller.commands.Help;
import com.vtom.sqlcmd.Controller.commands.ICommand;
import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.View.View;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestHelp {

    private View view;
    private ICommand command;
    private DatabaseManager manager;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = System.out::println;
        FunctionFactory factory = new FunctionFactory(manager);
        command = new Help();

    }

    @Test
    public void name() throws Exception {
        String result = command.process("asd");
        assertEquals("Commands:\n" +
                "Команда видаляє задану таблицю \n" +
                "\tdrop   |tableName\n" +
                "Команда для получения содержимого указанной таблицы \n" +
                "\tfind   |tableName\n" +
                "Команда очищає вміст зазначеної (всієї) таблиці \n" +
                "\tclear  |tableName\n" +
                "Команда для підключення до відповідної БД \n" +
                "\tconnect|databaseName|userName|password\n" +
                "Команда створює нову таблицю з заданими полями \n" +
                "\tcreate |tableName |column1 |column2| ... | columnN\n" +
                "Команда оновить запис, встановивши значення column2 = value2, для якої дотримується умова column1 = value1 \n" +
                "\tupdate |tableName |column1 |value1 | column2 | value2\n" +
                "Команда для вставки одного рядка в задану таблицю \n" +
                "\tinsert |tableName |column1 |value1 | column2 | value2 | ... | columnN | valueN\n" +
                "Команда видаляє одну або кілька записів для яких виконується умова column = value \n" +
                "\tdelete |tableName |column  |value\n" +
                "Команда виводить список всіх таблиць: \n" +
                "\ttables\n" +
                "Команда виводить в консоль список всіх доступних команд \n" +
                "\thelp\n" +
                "Команда для відключення від БД і вихід з програми \n" +
                "\texit\n",result);
    }
}