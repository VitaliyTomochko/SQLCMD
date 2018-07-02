package com.vtom.sqlcmd.Controller;

import com.vtom.sqlcmd.Model.Data;
import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.Model.JDBCDatabaseManager;
import com.vtom.sqlcmd.View.View;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.vtom.sqlcmd.Controller.commands.Connect.COMMAND_SAMPLE;

public class FunctionFactory {
    final DatabaseManager manager;
    final View view;


    public FunctionFactory(DatabaseManager databaseManager, View view) {
        this.manager = databaseManager;
        this.view = view;
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
//            if (data.length % 2 != 0) {
//                throw new IllegalArgumentException();
//            }
            String tableName = data[1];
            Data[] dataSets = new Data[data.length - 2];
            for (int index = 0; index < (data.length - 2); index++) {
                String columnName = data[index];
                // String value = data[index * 2 + 1];
                // dataSets.setName(columnName);
                //dataSets.setValue(value);
                dataSets[index] = new Data(columnName, "");

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

    public Function<String, String> getTableListFunction() {
        Function<String, String> function = command -> {
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

    public Function<String, String> getFindFunction() {
        Function<String, String> function = command -> {
            String[] data = command.split("\\|");
            if (data.length != 2) throw new IllegalArgumentException("кількість аргументів має бути 2");
            String tableName = data[1];
            Map<String, List<Object>> tableData;

            try {
                tableData = manager.getTableData(tableName);
            } catch (JDBCDatabaseManager.SqlCmdExeption | SQLException e) {
                throw new RuntimeException(e);
            }
            StringBuffer result = new StringBuffer();
            result.append("-------------------- \n");
            result.append("+  ");
          /*  for (Map.Entry colomName : tableData.entrySet()) {
                result.append(colomName.getKey().toString() + "  +  ");
            }*/
            result.append("\n-------------------- \n");
            result.append("+  ");
            for (Map.Entry colomName : tableData.entrySet()) {
                System.out.print(colomName.getKey().toString() + "  +  ");
            }
            System.out.println();
            for (List<Object> values : tableData.values()) {
                for (int i = 0; i < tableData.values().size(); i++)
                    System.out.println(values.get(i)+" ");
            }

            return result.toString();
        };
        return function;
    }

    public Function<String, String> getClearFunction() {
        Function<String, String> function = command -> {
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

    public Function<String, String> getDropFunction() {
        Function<String, String> function = command -> {
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

    public Function<String, String> getDeleteFunction() {
        Function<String, String> function = command -> {
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

    public Function<String, String> getInsertFunction() {
        Function<String, String> function = command -> {
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
            } catch (SQLException | JDBCDatabaseManager.SqlCmdExeption e) {
                throw new RuntimeException(e);
            }
            return "команда виконана успішно";

        };
        return function;
    }
}
