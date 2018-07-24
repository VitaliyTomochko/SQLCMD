package com.vtom.sqlcmd.controller;

import com.vtom.sqlcmd.Controller.FunctionFactory;
import com.vtom.sqlcmd.Controller.commands.Command;
import com.vtom.sqlcmd.Controller.commands.Drop;
import com.vtom.sqlcmd.Controller.commands.Find;
import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.View.View;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestDropTable {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = System.out::println;;
        FunctionFactory factory = new FunctionFactory(manager);
        command = new Drop(factory.getDropFunction());
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("drop|");
        assertTrue(canProcess);
    }

    @Test
    public void testProcessWithWrongCommand() throws Exception {
        boolean canNotProcess = command.canProcess("dropad|");
        assertFalse(canNotProcess);
    }

    @Test
    public void testCanProcessClearWithoutParametersString() {
        boolean canProcess = command.canProcess("drop|");
        assertTrue(canProcess);
    }

    @Test
    public void testProcess() throws Exception {
        command.process("drop|nameTable");
        verify(manager).drop("nameTable");
    }

    @Test
    public void testProcessWrongFormat() throws Exception {
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getDropFunction();
        try {
            String result = f.apply("drop|name|name");
        } catch (Exception e) {
            assertEquals("кількість аргументів має бути 2 в форматі drop|tableName", e.getMessage());
            return;
        }
        fail();
    }

}