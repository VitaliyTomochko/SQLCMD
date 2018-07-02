package com.vtom.sqlcmd.Model;

import java.sql.*;
import java.util.*;

public class JDBCDatabaseManager implements DatabaseManager {
    private Connection connection;

    @Override
    public void connect(String DB_CONNECTION,
                        String DB_USER,
                        String DB_PASSWORD) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/" +
                        DB_CONNECTION, DB_USER, DB_PASSWORD);
    }

    @Override
    public void isConnected() throws SqlCmdExeption {
        if (connection == null)
            throw new SqlCmdExeption("Not connected \n" + "Для під'єднання до бази даних введіть ім'я бази даних," +
                    " ім'я користувача та пароль у форматі: connect|database|username|password");
    }

    @Override
    public Map<String, List<Object>> getTableData(String tableName) throws SqlCmdExeption, SQLException {
        isConnected();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM " + tableName);
        ResultSetMetaData rsmd = rs.getMetaData();
        //  Map<Set<String>, List<Object>> data = new HashMap<>();
        Map<String, List<Object>> data = new HashMap<>();
        List<Object> values = new ArrayList<>();


        while (rs.next()) {
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    data.computeIfAbsent(rsmd.getColumnName(i), ignored -> new ArrayList<>())
                            .addAll(Arrays.asList(rs.getObject(i)));
               // values.add(rs.getObject(i));
            }
        }
        rs.close();
        st.close();
        return data;
    }

    @Override
    public List<Data> getTableNames() throws SQLException, SqlCmdExeption {
        isConnected();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_type = 'BASE TABLE'");
        List<Data> tableNames = new ArrayList<>();
        while (rs.next()) {
            tableNames.add(new Data(rs.getString("table_name"), ""));
        }
        rs.close();
        st.close();
        return tableNames;
    }

    @Override
    public void create(String newTableName, Data[] input) throws SQLException, SqlCmdExeption {
        isConnected();
        Statement st = connection.createStatement();
        for (Data elem : input) {
            System.out.println(elem.getName());
        }
        String sql = "CREATE TABLE IF NOT EXISTS " + newTableName + " (\n"
             /* + input.getName()*/
                + ");";
        st.execute(sql);
        st.close();
    }

    @Override
    public void update(String tableName, String colomnNameOLd, Object oldValue, String colomnNameNew, Object newValue) throws SQLException, SqlCmdExeption {
        isConnected();
        Statement st = connection.createStatement();
        st.executeUpdate("UPDATE " + tableName + " SET " + colomnNameNew
                + " = '" + newValue + "'  WHERE " + colomnNameOLd
                + " = '" + oldValue + "'");
        st.close();
    }

    @Override
    public void clear(String tableName) throws SQLException, SqlCmdExeption {
        isConnected();
        Statement st;
        st = connection.createStatement();
        st.execute("TRUNCATE " + tableName);
        st.close();
    }

    @Override
    public void drop(String tableName) throws SQLException, SqlCmdExeption {
        isConnected();
        Statement st = connection.createStatement();
        st.execute("DROP TABLE " + tableName);
        st.close();
    }

    @Override
    public void delete(String tableName, String name, Object value) throws SQLException, SqlCmdExeption {
        isConnected();
        Statement st = connection.createStatement();
        st.execute("delete from " + tableName + " WHERE " + name
                + " = '" + value + "'");
        st.close();
    }

    @Override
    public void insert(String tableName, String colomn, Object value) throws SQLException, SqlCmdExeption {
        isConnected();
        Statement st = connection.createStatement();
        st.executeUpdate("INSERT INTO " + tableName + "(" + colomn + "," + value + ") VALUES('" + colomn + "','" + value + "')");
        st.close();
    }

    public class SqlCmdExeption extends Exception {
        public SqlCmdExeption(String message) {
            super(message);
        }
    }
}
