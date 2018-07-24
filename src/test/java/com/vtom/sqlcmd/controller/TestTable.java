package com.vtom.sqlcmd.controller;

import com.vtom.sqlcmd.Controller.FunctionFactory;
import com.vtom.sqlcmd.Controller.commands.Command;
import com.vtom.sqlcmd.Controller.commands.TableList;
import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.View.View;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Function;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestTable {
    private Command command;
    private DatabaseManager manager;
    private View view;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = System.out::println;
        FunctionFactory factory = new FunctionFactory(manager);
        command = new TableList(factory.getTableListFunction());
    }

    @Test
    public void testPrintGetTableNames() {
        when(manager.getTableNames()).thenReturn(Arrays.asList("test1","test2"));
        command.process("tables");
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getTableListFunction();
        String result = f.apply("tables");
        assertEquals("[test1, test2]", result);
    }

    @Test
    public void canProcess() {
        boolean canProcess = command.canProcess("tables");
        assertTrue(canProcess);
    }

    @Test
    public void canProcessWithWrongParameter() {
        boolean canNotProcess = command.canProcess("tabdlses");
        assertFalse(canNotProcess);
    }

    @Test
    public void canProcessWithoutParameter() {
        boolean canNotProcess = command.canProcess("");
        assertFalse(canNotProcess);
    }

    @Test
    public void testPrintEmptyTableData() {
        when(manager.getTableNames()).thenReturn(Arrays.asList());
        command.process(("tables"));
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getTableListFunction();
        String result = f.apply("tables");
        assertEquals("[]", result);
    }
  
}
