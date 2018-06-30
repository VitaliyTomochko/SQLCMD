package com.vtom.sqlcmd.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<Data> getTableData(String tableName) throws SqlCmdExeption, SQLException {
        isConnected();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM " + tableName);
        ResultSetMetaData rsmd = rs.getMetaData();
        List<Data> data = new ArrayList<>();
        int count = 1;
        while (rs.next()) {
            if (count <= rsmd.getColumnCount())
                data.add(new Data(rsmd.getColumnName(count), rs.getObject(count)));
            count++;
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
    public void create(String newTableName, Data input) throws SQLException, SqlCmdExeption {
        isConnected();
        Statement st = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS " + newTableName + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	capacity real\n"
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
    public void insert(String tableName, String login, Object pass) throws SQLException, SqlCmdExeption {
        isConnected();
        Statement st = connection.createStatement();
        st.executeUpdate("INSERT INTO " + tableName + "(name,password) VALUES('" + login + "','" + pass + "')");
        st.close();
    }

    public class SqlCmdExeption extends Exception {
        public SqlCmdExeption(String message) {
            super(message);
        }
    }
}
