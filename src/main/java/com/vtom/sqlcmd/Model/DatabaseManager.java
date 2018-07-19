package com.vtom.sqlcmd.Model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DatabaseManager {
    void connect(
                 String DB_CONNECTION,
                 String DB_USER,
                 String DB_PASSWORD) throws SQLException, ClassNotFoundException;

    void isConnected() throws JDBCDatabaseManager.SqlCmdException;

    List<Map<String, Object>> getTableData(String tableName) throws JDBCDatabaseManager.SqlCmdException, SQLException;

    List<String> getTableNames();

    void create(String newTableName, String input) throws SQLException, JDBCDatabaseManager.SqlCmdException;

    void update(String tableName, String colomnNameOLd, Object oldValue, String colomnNameNew, Object newValue) throws SQLException, JDBCDatabaseManager.SqlCmdException;

    void clear(String tableName) throws SQLException, JDBCDatabaseManager.SqlCmdException;

    void drop(String tableName) throws SQLException, JDBCDatabaseManager.SqlCmdException;

    void delete(String tableName, String name, Object value) throws SQLException, JDBCDatabaseManager.SqlCmdException;

    void insert(String tableName, String login, Object pass) throws SQLException, JDBCDatabaseManager.SqlCmdException;
}
