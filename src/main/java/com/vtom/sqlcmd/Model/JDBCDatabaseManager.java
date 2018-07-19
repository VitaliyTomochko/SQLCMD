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
    public void isConnected() throws SqlCmdException {
        if (connection == null)
            throw new SqlCmdException("Not connected \n" + "Для під'єднання до бази даних введіть ім'я бази даних," +
                    " ім'я користувача та пароль у форматі: connect|database|username|password");
    }

    @Override
    public List<Map<String, Object>> getTableData(String tableName) throws SqlCmdException, SQLException {
        isConnected();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM " + tableName);
        ResultSetMetaData rsmd = rs.getMetaData();
        List<Map<String, Object>> result = new LinkedList<>();
        while (rs.next()) {
            Map<String, Object> data = new LinkedHashMap<>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                data.put(rsmd.getColumnName(i), rs.getObject(i));
            }
            result.add(data);
        }
        rs.close();
        st.close();
        return result;
    }

    @Override
    public List<String> getTableNames() {
        try {
            isConnected();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_type = 'BASE TABLE'");
            List<String> tableNames = new ArrayList<>();
            while (rs.next()) {
                tableNames.add(rs.getString("table_name"));
            }
            rs.close();
            st.close();
            return tableNames;
        } catch (SqlCmdException | SQLException sqlCmdException) {
            throw new RuntimeException(sqlCmdException);
        }
    }

    @Override
    public void create(String newTableName, String input) throws SQLException, SqlCmdException {
        isConnected();
        Statement st = connection.createStatement();
        // System.out.println(input);
        String sql = "CREATE TABLE IF NOT EXISTS " + newTableName + " (\n"
                + input
                + ");";
        st.execute(sql);
        st.close();
    }

    @Override
    public void update(String tableName, String colomnNameOLd, Object oldValue, String colomnNameNew, Object newValue) throws SQLException, SqlCmdException {
        isConnected();
        Statement st = connection.createStatement();
        st.executeUpdate("UPDATE " + tableName + " SET " + colomnNameNew
                + " = '" + newValue + "'  WHERE " + colomnNameOLd
                + " = '" + oldValue + "'");
        st.close();
    }

    @Override
    public void clear(String tableName) throws SQLException, SqlCmdException {
        isConnected();
        Statement st;
        st = connection.createStatement();
        st.execute("TRUNCATE " + tableName);
        st.close();
    }

    @Override
    public void drop(String tableName) throws SQLException, SqlCmdException {
        isConnected();
        Statement st = connection.createStatement();
        st.execute("DROP TABLE " + tableName);
        st.close();
    }

    @Override
    public void delete(String tableName, String name, Object value) throws SQLException, SqlCmdException {
        isConnected();
        Statement st = connection.createStatement();
        st.execute("delete from " + tableName + " WHERE " + name
                + " = '" + value + "'");
        st.close();
    }

    @Override
    public void insert(String tableName, String colomn, Object value) throws SQLException, SqlCmdException {
        isConnected();
        Statement st = connection.createStatement();
        st.executeUpdate("INSERT INTO " + tableName + " (" + colomn + ") VALUES('" + value + "')");
        st.close();
    }

    public class SqlCmdException extends Exception {
        public SqlCmdException(String message) {
            super(message);
        }
    }
}
