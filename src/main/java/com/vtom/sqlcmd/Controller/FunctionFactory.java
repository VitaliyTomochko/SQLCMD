package com.vtom.sqlcmd.Controller;

import com.vtom.sqlcmd.Model.Data;
import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.Model.JDBCDatabaseManager;
import com.vtom.sqlcmd.View.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.vtom.sqlcmd.Controller.commands.Connect.COMMAND_SAMPLE;

public class FunctionFactory {
    final DatabaseManager manager;
    final View view;


    public FunctionFactory(DatabaseManager databaseManager, View view) {
        this.manager = databaseManager;
        this.view = view;
    }

    public Function<String,String> getConnectFunction() {
        Function<String,String> function = command -> {
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

    public Function<String,String> getCreateFunction() {
        Function<String,String> function = command -> {
            String[] data = command.split("\\|");
            if (data.length % 2 != 0) {
                throw new IllegalArgumentException();
            }
            String tableName = data[1];
            Data dataSets = new Data();
            for (int index = 1; index < (data.length / 2); index++) {
                String columnName = data[index * 2];
                String value = data[index * 2 + 1];
                dataSets.setName(columnName);
                dataSets.setValue(value);
            }

            try {
                manager.create(tableName, dataSets);
            } catch (SQLException | JDBCDatabaseManager.SqlCmdExeption e) {
                throw new RuntimeException(e);
            }
            return "команда виконана успішно";
        };
        return function;
    }

    public Function<String,String> getTableListFunction() {
        Function<String,String> function = command -> {
            List<Data> tables;
            try {
                tables = manager.getTableNames();
            } catch (SQLException | JDBCDatabaseManager.SqlCmdExeption e) {
                throw new RuntimeException(e);
            }
            return Arrays.toString(tables.toArray());
        };
        return function;
    }

    public Function<String,String> getFindFunction() {
        Function<String,String> function = command -> {
            String[] data = command.split("\\|");
            if(data.length!=2) throw new IllegalArgumentException("кількість аргументів має бути 2");
            String tableName = data[1];
            List<Data> tableData;
            try {
                tableData = manager.getTableData(tableName);
            } catch (JDBCDatabaseManager.SqlCmdExeption | SQLException e) {
                throw new RuntimeException(e);
            }
            StringBuffer result =new StringBuffer();
            result.append("-------------------- \n");
            result.append("+");
            for (Data columnName : tableData) {
                result.append(columnName.getName() + "+");
            }

            result.append("\n-------------------- \n");
            result.append("+");
            for (Data dataSet : tableData) {
                Object values = dataSet.getValue();
                result.append(values + "+");
            }
            return result.toString();
        };
        return function;
    }

    public Function<String,String> getClearFunction() {
        Function<String,String> function = command -> {
            String[] data = command.split("\\|");
            String tableName = data[1];
            try {
                manager.clear(tableName);
            } catch (SQLException | JDBCDatabaseManager.SqlCmdExeption e) {
                throw new RuntimeException(e);
            }
            return "команда виконана успішно";
        };
        return function;
    }

    public Function<String,String> getDropFunction() {
        Function<String,String> function = command -> {
            String[] data = command.split("\\|");
            String tableName = data[1];
            try {
                manager.drop(tableName);
            } catch (SQLException | JDBCDatabaseManager.SqlCmdExeption e) {
                throw new RuntimeException(e);
            }
            return "команда виконана успішно";
        };
        return function;
    }

    public Function<String,String> getDeleteFunction() {
        Function<String,String> function = command -> {
            String[] data = command.split("\\|");
            String tableName = data[1];
            try {
                manager.delete(tableName, data[2], data[3]);
            } catch (SQLException | JDBCDatabaseManager.SqlCmdExeption e) {
                throw new RuntimeException(e);
            }
            return "команда виконана успішно";
        };
        return function;
    }

    public Function<String,String> getInsertFunction() {
        Function<String,String> function = command -> {
            String[] data = command.split("\\|");
            String tableName = data[1];
            String columnName = "";
            String value = "";
            for (int index = 1; index < (data.length / 2); index++) {
                columnName = data[index * 2];
                value = data[index * 2 + 1];
            }

            try {
                manager.insert(tableName, columnName, value);
            } catch (SQLException | JDBCDatabaseManager.SqlCmdExeption e) {
                throw new RuntimeException(e);
            }
            return "команда виконана успішно";
        };
        return function;
    }

    public Function<String,String> getUpdateFunction() {
        Function<String,String> function = command -> {
            String[] data = command.split("\\|");
            String tableName = data[1];
            String oldColumnName = data[2];
            String oldValue = data[3];
            String columnName = data[4];
            String value = data[5];

            try {
                manager.update(tableName, oldColumnName, oldValue, columnName, value);
            } catch (SQLException | JDBCDatabaseManager.SqlCmdExeption e) {
               throw new RuntimeException(e);
            }
            return "команда виконана успішно";

        };
        return function;
    }
}
