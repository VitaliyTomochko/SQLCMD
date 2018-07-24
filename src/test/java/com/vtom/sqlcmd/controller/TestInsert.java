package com.vtom.sqlcmd.controller;

import com.vtom.sqlcmd.Controller.FunctionFactory;
import com.vtom.sqlcmd.Controller.commands.Command;
import com.vtom.sqlcmd.Controller.commands.Insert;
import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.View.View;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestInsert {
    private Command command;
    private DatabaseManager manager;
    private View view;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = System.out::println;
        FunctionFactory factory = new FunctionFactory(manager);
        command = new Insert(factory.getInsertFunction());
    }


    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("insert|");
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongInput() throws Exception {
        boolean canNotProcess = command.canProcess("inser|");
        assertFalse(canNotProcess);
    }

    @Test
    public void testProcess() throws Exception {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("username", "Blackjack");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("username", "WhiteJack");
        List<Map<String, Object>> tableData = Arrays.asList(map1, map2);
        when(manager.getTableNames()).thenReturn(Arrays.asList("sdfvsxcv"));
        when(manager.getTableData(any())).thenReturn(tableData);
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
    public void testWithWrongParameters() throws Exception {
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getInsertFunction();
        try {
            String result = f.apply("insert|tablename|tablename");
        } catch (Exception e) {
            assertEquals("має бути парна кількість аргументів && >= 4 в форматі insert|tableName|column1|value1", e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void testWithoutParameters() throws Exception {
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getInsertFunction();
        try {
            String result = f.apply("insert|");
        } catch (Exception e) {
            assertEquals("має бути парна кількість аргументів && >= 4 в форматі insert|tableName|column1|value1", e.getMessage());
            return;
        }
        fail();
    }
}