package com.vtom.sqlcmd.Controller;

import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.Model.JDBCDatabaseManager;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.vtom.sqlcmd.Controller.commands.Clear.COMMAND_SAMPLE_CLEAR;
import static com.vtom.sqlcmd.Controller.commands.Connect.COMMAND_SAMPLE_CONNECT;
import static com.vtom.sqlcmd.Controller.commands.Create.COMMAND_SAMPLE_CREATE;
import static com.vtom.sqlcmd.Controller.commands.Delete.COMMAND_SAMPLE_DELETE;
import static com.vtom.sqlcmd.Controller.commands.Drop.COMMAND_SAMPLE_DROP;
import static com.vtom.sqlcmd.Controller.commands.Find.COMMAND_SAMPLE_FIND;
import static com.vtom.sqlcmd.Controller.commands.Insert.COMMAND_SAMPLE_INSERT;
import static com.vtom.sqlcmd.Controller.commands.TableList.COMMAND_SAMPLE_TABLES;
import static com.vtom.sqlcmd.Controller.commands.Update.COMMAND_SAMPLE_UPDATE;

public class FunctionFactory {
    final DatabaseManager manager;


    public FunctionFactory(DatabaseManager databaseManager) {
        this.manager = databaseManager;
    }

    public Function<String, String> getConnectFunction() {
        Function<String, String> function = command -> {
            String[] data = command.split("\\|");
            int count = COMMAND_SAMPLE_CONNECT.split("\\|").length;
            if (data.length != count) {
                throw new IllegalArgumentException("кількість аргументів має бути " + count + " в форматі " + COMMAND_SAMPLE_CONNECT);
            }
            String databaseName = data[1];
            String userName = data[2];
            String password = data[3];
            try {
                manager.connect(databaseName, userName, password);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return "команда виконана успішно";
        };
        return function;
    }

    public Function<String, String> getCreateFunction() {
        Function<String, String> function = command -> {
            String[] data = command.split("\\|");
            int count = COMMAND_SAMPLE_CREATE.split("\\|").length;
            if (data.length < count) {
                throw new IllegalArgumentException("кількість аргументів має бути > 3  в форматі SQL запиту  " + COMMAND_SAMPLE_CREATE);
            }
            String tableName = data[1];
            String columnName = data[2];
            for (int index = 3; index < (data.length); index++) {
                columnName += "," + data[index];
            }
            try {
                manager.create(tableName, columnName);
            } catch (SQLException | JDBCDatabaseManager.SqlCmdException e) {
                throw new RuntimeException(e);
            }
            return "команда виконана успішно";
        };
        return function;
    }

    public Function<String, String> getTableListFunction() {
        Function<String, String> function = command -> {
            String[] data = command.split("\\|");
            int count = COMMAND_SAMPLE_TABLES.split("\\|").length;
            if (data.length != count) {
                throw new IllegalArgumentException("кількість аргументів має бути " + count + " в форматі " + COMMAND_SAMPLE_TABLES);
            }
            List<String> tables;
            tables = manager.getTableNames();
            return Arrays.toString(tables.toArray());
        };
        return function;
    }

    public Function<String, String> getFindFunction() {
        Function<String, String> function = command -> {
            String[] data = command.split("\\|");
            int count = COMMAND_SAMPLE_FIND.split("\\|").length;
            if (data.length != count) {
                throw new IllegalArgumentException("кількість аргументів має бути " + count + " в форматі " + COMMAND_SAMPLE_FIND);
            }
            String tableName = data[1];
            if (manager.getTableNames().stream().filter(t -> t.equals(tableName))
                    .count() != 1) {
                throw new IllegalArgumentException("Таблиця не існує" + tableName);
            }
            List<Map<String, Object>> tableData;
            try {
                tableData = manager.getTableData(tableName);
            } catch (JDBCDatabaseManager.SqlCmdException | SQLException e) {
                throw new RuntimeException(e);
            }
            return getTablePrint(tableData);
        };
        return function;
    }

    public Function<String, String> getClearFunction() {
        Function<String, String> function = command -> {
            String[] data = command.split("\\|");
            int count = COMMAND_SAMPLE_CLEAR.split("\\|").length;
            if (data.length != count) {
                throw new IllegalArgumentException("кількість аргументів має бути " + count + " в форматі " + COMMAND_SAMPLE_CLEAR);
            }
            String tableName = data[1];
            try {
                manager.clear(tableName);
            } catch (SQLException | JDBCDatabaseManager.SqlCmdException e) {
                throw new RuntimeException(e);
            }
            return "команда виконана успішно";
        };
        return function;
    }

    public Function<String, String> getDropFunction() {
        Function<String, String> function = command -> {
            String[] data = command.split("\\|");
            int count = COMMAND_SAMPLE_DROP.split("\\|").length;
            if (data.length != count) {
                throw new IllegalArgumentException("кількість аргументів має бути " + count + " в форматі " + COMMAND_SAMPLE_DROP);
            }
            String tableName = data[1];
            try {
                manager.drop(tableName);
            } catch (SQLException | JDBCDatabaseManager.SqlCmdException e) {
                throw new RuntimeException(e);
            }
            return "команда виконана успішно";
        };
        return function;
    }

    public Function<String, String> getDeleteFunction() {
        Function<String, String> function = command -> {
            String[] data = command.split("\\|");
            int count = COMMAND_SAMPLE_DELETE.split("\\|").length;
            if (data.length != count) {
                throw new IllegalArgumentException("кількість аргументів має бути " + count);
            }
            String tableName = data[1];
            try {
                manager.delete(tableName, data[2], data[3]);
            } catch (SQLException | JDBCDatabaseManager.SqlCmdException e) {
                throw new RuntimeException(e);
            }
            return "команда виконана успішно";
        };
        return function;
    }

    public Function<String, String> getInsertFunction() {
        Function<String, String> function = command -> {
            String[] data = command.split("\\|");
            int count = COMMAND_SAMPLE_INSERT.split("\\|").length;
            if (data.length < count || data.length%2==1) {
                throw new IllegalArgumentException("має бути парна кількість аргументів && >= " + count +" в форматі " + COMMAND_SAMPLE_INSERT);
            }
            String tableName = data[1];
            String columnName = data[2];
            String value = data[3];
            for (int index = 2; index < (data.length / 2); index++) {
                columnName += "," + data[index * 2];
                value += "','" + data[index * 2 + 1];
            }
            try {
                manager.insert(tableName, columnName, value);
            } catch (SQLException | JDBCDatabaseManager.SqlCmdException e) {
                throw new RuntimeException(e);
            }
            return "команда виконана успішно";
        };
        return function;
    }

    public Function<String, String> getUpdateFunction() {
        Function<String, String> function = command -> {
            String[] data = command.split("\\|");
            int count = COMMAND_SAMPLE_UPDATE.split("\\|").length;
            if (data.length != count) {
                throw new IllegalArgumentException("кількість аргументів має бути " + count + " в форматі " + COMMAND_SAMPLE_UPDATE);
            }
            String tableName = data[1];
            String oldColumnName = data[2];
            String oldValue = data[3];
            String columnName = data[4];
            String value = data[5];

            try {
                manager.update(tableName, oldColumnName, oldValue, columnName, value);
            } catch (SQLException | JDBCDatabaseManager.SqlCmdException e) {
                throw new RuntimeException(e);
            }
            return "команда виконана успішно";

        };
        return function;
    }

    public String getTablePrint(List<Map<String, Object>> dataSet) {

        AsciiTable at = new AsciiTable();

        at.addRule();
        at.addRow(dataSet.get(0).keySet().toArray());
        for (Map<String, Object> entry : dataSet) {

            at.addRule();
            at.addRow(entry.values().toArray());

        }
        at.addRule();
        at.getContext().setWidth(35).setGrid(A7_Grids.minusBarPlusEquals());
        return at.render();


    }
}
