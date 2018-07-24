package com.vtom.sqlcmd.integration;

import com.vtom.sqlcmd.Controller.Main;
import com.vtom.sqlcmd.Controller.commands.ExitException;
import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.Model.JDBCDatabaseManager;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class IntegrationTest {

    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;
    private DatabaseManager databaseManager;

    @Before
    public void setup() {
        databaseManager = new JDBCDatabaseManager();
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testHelp() {

        in.add("help");
        in.add("exit");

        try {
            Main.main(new String[0]);
        } catch (ExitException e) {
            assertEquals("Welcome to SQLCmd!\n" +
                    "Для під'єднання до бази даних введіть ім'я бази даних, ім'я користувача та пароль у форматі: connect|database|username|password\n" +
                    "Commands:\n" +
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
                    "\texit\n" +
                    "\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "програма завершила роботу\n", getData());
            return;
        }
        fail();
    }

    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Test
    public void testExit() {

        in.add("exit");

        try {
            Main.main(new String[0]);
        } catch (ExitException e) {
            assertEquals("Welcome to SQLCmd!\n" +
                    "Для під'єднання до бази даних введіть ім'я бази даних, ім'я користувача та пароль у форматі: connect|database|username|password\n" +
                    "програма завершила роботу\n", getData());
            return;
        }
        fail();
    }

    @Test
    public void testListWithoutConnect() {

        in.add("tables");
        in.add("exit");

        try {
            Main.main(new String[0]);
        } catch (ExitException e) {
            assertEquals("Welcome to SQLCmd!\n" +
                    "Для під'єднання до бази даних введіть ім'я бази даних, ім'я користувача та пароль у форматі: connect|database|username|password\n" +
                    "com.vtom.sqlcmd.Model.JDBCDatabaseManager$SqlCmdException: Not connected \n" +
                    "Для під'єднання до бази даних введіть ім'я бази даних, ім'я користувача та пароль у форматі: connect|database|username|password\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "програма завершила роботу\n", getData());
            return;
        }
        fail();
    }

    @Test
    public void testFindWithoutConnect() {

        in.add("find|user");
        in.add("exit");

        try {
            Main.main(new String[0]);
        } catch (ExitException e) {
            assertEquals("Welcome to SQLCmd!\n" +
                    "Для під'єднання до бази даних введіть ім'я бази даних, ім'я користувача та пароль у форматі: connect|database|username|password\n" +
                    "com.vtom.sqlcmd.Model.JDBCDatabaseManager$SqlCmdException: Not connected \n" +
                    "Для під'єднання до бази даних введіть ім'я бази даних, ім'я користувача та пароль у форматі: connect|database|username|password\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "програма завершила роботу\n", getData());
            return;
        }
        fail();
    }

    @Test
    public void testUnsupported() {

        in.add("unsupported");
        in.add("exit");

        try {
            Main.main(new String[0]);
        } catch (ExitException e) {
            assertEquals("Welcome to SQLCmd!\n" +
                    "Для під'єднання до бази даних введіть ім'я бази даних, ім'я користувача та пароль у форматі: connect|database|username|password\n" +
                    "No such command! unsupported\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "програма завершила роботу\n", getData());
            return;
        }
        fail();
    }

    @Test
    public void testUnsupportedAfterConnect() {
        in.add("connect|sqlcmd|sqlcmd|1478951");
        in.add("unsupported");
        in.add("exit");

        try {
            Main.main(new String[0]);
        } catch (ExitException e) {
            assertEquals("Welcome to SQLCmd!\n" +
                    "Для під'єднання до бази даних введіть ім'я бази даних, ім'я користувача та пароль у форматі: connect|database|username|password\n" +
                    "команда виконана успішно\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "No such command! unsupported\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "програма завершила роботу\n", getData());
            return;
        }
        fail();
    }

    @Test
    public void testListAfterConnect() {

        in.add("connect|sqlcmd|sqlcmd|1478951");
        in.add("tables");
        in.add("exit");

        try {
            Main.main(new String[0]);
        } catch (ExitException e) {
            assertEquals("Welcome to SQLCmd!\n" +
                    "Для під'єднання до бази даних введіть ім'я бази даних, ім'я користувача та пароль у форматі: connect|database|username|password\n" +
                    "команда виконана успішно\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "[test1, test2, test3, tablename, work]\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "програма завершила роботу\n", getData());
            return;
        }
        fail();
    }

    @Test
    public void testFindAfterConnect() {

        in.add("connect|sqlcmd|postgres|postgres");
        in.add("find|user");
        in.add("exit");

        try {
            Main.main(new String[0]);
        } catch (ExitException e) {
            assertEquals("Welcome to SQLCmd!\n" +
                    "Для під'єднання до бази даних введіть ім'я бази даних, ім'я користувача та пароль у форматі: connect|database|username|password\n" +
                    "org.postgresql.util.PSQLException: FATAL: password authentication failed for user \"postgres\"\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "com.vtom.sqlcmd.Model.JDBCDatabaseManager$SqlCmdException: Not connected \n" +
                    "Для під'єднання до бази даних введіть ім'я бази даних, ім'я користувача та пароль у форматі: connect|database|username|password\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "програма завершила роботу\n", getData());
            return;
        }
        fail();
    }

    @Test
    public void testConnectAfterConnect() {

        in.add("connect|sqlcmd|sqlcmd|1478951");
        in.add("tables");
        in.add("connect|sqlcmd|sqlcmd|1478951");
        in.add("tables");
        in.add("exit");

        try {
            Main.main(new String[0]);
        } catch (ExitException e) {
            assertEquals("Welcome to SQLCmd!\n" +
                    "Для під'єднання до бази даних введіть ім'я бази даних, ім'я користувача та пароль у форматі: connect|database|username|password\n" +
                    "команда виконана успішно\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "[test1, test2, test3, tablename, work]\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "команда виконана успішно\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "[test1, test2, test3, tablename, work]\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "програма завершила роботу\n", getData());
            return;
        }
        fail();
    }

    @Test
    public void testConnectWithError() {

        in.add("connect|sqlcmd");
        in.add("exit");

        try {
            Main.main(new String[0]);
        } catch (ExitException e) {
            assertEquals("Welcome to SQLCmd!\n" +
                    "Для під'єднання до бази даних введіть ім'я бази даних, ім'я користувача та пароль у форматі: connect|database|username|password\n" +
                    "кількість аргументів має бути 4 в форматі connect|sqlcmd|sqlcmd|123321\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "програма завершила роботу\n", getData());
            return;
        }
        fail();
    }

    @Test
    public void testInsertAfterConnection() {

        in.add("connect|sqlcmd|sqlcmd|1478951");
        in.add("create|work|id SERIAL NOT NULL PRIMARY KEY|username varchar(225) NOT NULL UNIQUE| password varchar(225)");
        in.add("clear|work");
        in.add("insert|work|id|1|username|BlackJack|password|21");
        in.add("insert|work|id|2|username|WhiteJack|password|123");
        in.add("find|work");
        in.add("exit");

        try {
            Main.main(new String[0]);
        } catch (ExitException e) {
            assertEquals("Welcome to SQLCmd!\n" +
                    "Для під'єднання до бази даних введіть ім'я бази даних, ім'я користувача та пароль у форматі: connect|database|username|password\n" +
                    "команда виконана успішно\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "команда виконана успішно\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "команда виконана успішно\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "команда виконана успішно\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "команда виконана успішно\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "+-----------+----------+----------+\n" +
                    "|id         |username  |password  |\n" +
                    "+-----------+----------+----------+\n" +
                    "|1          |BlackJack |21        |\n" +
                    "+-----------+----------+----------+\n" +
                    "|2          |WhiteJack |123       |\n" +
                    "+-----------+----------+----------+\n" +
                    "Введи команду (або help для допомоги) :\n" +
                    "програма завершила роботу\n", getData());
            return;
        }
        fail();
    }
}
