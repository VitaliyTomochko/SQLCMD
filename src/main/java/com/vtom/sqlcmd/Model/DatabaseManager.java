package com.vtom.sqlcmd.Model;

import java.sql.SQLException;
import java.util.List;

public interface DatabaseManager {
    void connect(
                 String DB_CONNECTION,
                 String DB_USER,
                 String DB_PASSWORD) throws SQLException, ClassNotFoundException;

    void isConnected() throws JDBCDatabaseManager.SqlCmdExeption;

    List<Data> getTableData(String tableName) throws JDBCDatabaseManager.SqlCmdExeption, SQLException;

    List<Data> getTableNames() throws SQLException, JDBCDatabaseManager.SqlCmdExeption;

    void create(String newTableName, Data input) throws SQLException, JDBCDatabaseManager.SqlCmdExeption;

    void update(String tableName, String colomnNameOLd, Object oldValue, String colomnNameNew, Object newValue) throws SQLException, JDBCDatabaseManager.SqlCmdExeption;

    void clear(String tableName) throws SQLException, JDBCDatabaseManager.SqlCmdExeption;

    void drop(String tableName) throws SQLException, JDBCDatabaseManager.SqlCmdExeption;

    void delete(String tableName, String name, Object value) throws SQLException, JDBCDatabaseManager.SqlCmdExeption;

    void insert(String tableName, String login, Object pass) throws SQLException, JDBCDatabaseManager.SqlCmdExeption;
}
