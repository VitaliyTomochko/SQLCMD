package com.vtom.sqlcmd.controller;

import com.vtom.sqlcmd.Controller.FunctionFactory;
import com.vtom.sqlcmd.Controller.commands.Command;
import com.vtom.sqlcmd.Controller.commands.Find;
import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.Model.JDBCDatabaseManager;
import com.vtom.sqlcmd.View.View;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TestFind {

    private Command command;
    private DatabaseManager manager;
    private View view;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = System.out::println;
        FunctionFactory factory = new FunctionFactory(manager);
        command = new Find(factory.getFindFunction());
    }

    @Test
    public void testCantProcess() {
        boolean canProcess = command.canProcess("find|tablename");
        assertTrue(canProcess);
    }

    @Test
    public void testClearTableWrongCommand() {
        boolean canNotProcess = command.canProcess("findds|tablename");
        assertFalse(canNotProcess);
    }

    @Test
    public void testCantProcessClearWithoutParametersString() {
        boolean canProcess = command.canProcess("find|");
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWithFewColomns() throws SQLException, JDBCDatabaseManager.SqlCmdException {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("username", "Blackjack");
        map1.put("id", 1);
        map1.put("password", 12312);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("username", "WhiteJack");
        map2.put("id", 2);
        map2.put("password", 11111);
        List<Map<String, Object>> tableData = Arrays.asList(map1, map2);
        when(manager.getTableNames()).thenReturn(Arrays.asList("sdfvsxcv"));
        when(manager.getTableData(any())).thenReturn(tableData);
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getFindFunction();
        String result = f.apply("find|sdfvsxcv");
        assertEquals("+-----------+----------+----------+\n" +
                "|password   |id        |username  |\n" +
                "+-----------+----------+----------+\n" +
                "|12312      |1         |Blackjack |\n" +
                "+-----------+----------+----------+\n" +
                "|11111      |2         |WhiteJack |\n" +
                "+-----------+----------+----------+", result);
    }


    @Test
    public void testCanProcessWithOneColomn() throws SQLException, JDBCDatabaseManager.SqlCmdException {
        command.process("insert|user|name|Vasia|password|****|id|22");
        Map<String, Object> map1 = new HashMap<>();
        map1.put("username", "Blackjack");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("username", "WhiteJack");
        List<Map<String, Object>> tableData = Arrays.asList(map1, map2);
        when(manager.getTableNames()).thenReturn(Arrays.asList("sdfvsxcv"));
        when(manager.getTableData(any())).thenReturn(tableData);
     //   when(manager.insert(any(),any(),any()));
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getFindFunction();
        String result = f.apply("find|sdfvsxcv");
        assertEquals("+---------------------------------+\n" +
                "|username                         |\n" +
                "+---------------------------------+\n" +
                "|Blackjack                        |\n" +
                "+---------------------------------+\n" +
                "|WhiteJack                        |\n" +
                "+---------------------------------+", result);
    }

    @Test
    public void testCanProcessEmptyTable() throws SQLException, JDBCDatabaseManager.SqlCmdException {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("username", "");
        map1.put("id", null);
        map1.put("password", null);
        List<Map<String, Object>> tableData = Arrays.asList(map1);
        when(manager.getTableNames()).thenReturn(Arrays.asList("sdfvsxcv"));
        when(manager.getTableData(any())).thenReturn(tableData);
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getFindFunction();
        String result = f.apply("find|sdfvsxcv");
        assertEquals("+-----------+----------+----------+\n" +
                        "|password   |id        |username  |\n" +
                        "+-----------+----------+----------+\n" +
                        "|                                 |\n" +
                        "+---------------------------------+"
                , result);
    }


    @Test
    public void testCanProcessWithoutParameters() throws SQLException, JDBCDatabaseManager.SqlCmdException {
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getFindFunction();
        try {
            String result = f.apply("find|");
        } catch (Exception e) {
            assertEquals("кількість аргументів має бути 2 в форматі find|tableName", e.getMessage());
            return;
        }
        fail();
    }


    @Test
    public void testValidationErrorWhenCountParametersIsMoreThan2() {
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getFindFunction();
        try {
            String result = f.apply("find|tablename|tablename");
        } catch (Exception e) {
            assertEquals("кількість аргументів має бути 2 в форматі find|tableName", e.getMessage());
            return;
        }
        fail();
    }
}

