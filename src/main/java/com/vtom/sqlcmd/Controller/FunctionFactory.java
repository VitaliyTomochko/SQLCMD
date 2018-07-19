package com.vtom.sqlcmd.Controller;

import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.Model.JDBCDatabaseManager;
import com.vtom.sqlcmd.View.View;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

import static com.vtom.sqlcmd.Controller.commands.Connect.COMMAND_SAMPLE;

public class FunctionFactory {
    final DatabaseManager manager;


    public FunctionFactory(DatabaseManager databaseManager) {
        this.manager = databaseManager;
    }

    public Function<String, String> getConnectFunction() {
        Function<String, String> function = command -> {
            String[] data = command.split("\\|");
            int count = COMMAND_SAMPLE.split("\\|").length;
            if (data.length != count) {
                throw new IllegalArgumentException("кількість аргументів має бути " + count);
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
            List<String> tables;
            tables = manager.getTableNames();
            return Arrays.toString(tables.toArray());
        };
        return function;
    }

    public Function<String, String> getFindFunction() {
        Function<String, String> function = command -> {
            String[] data = command.split("\\|");
            if (data.length != 2) throw new IllegalArgumentException("кількість аргументів має бути 2");
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
