package com.vtom.sqlcmd.controller;

import com.vtom.sqlcmd.Controller.FunctionFactory;
import com.vtom.sqlcmd.Controller.commands.Clear;
import com.vtom.sqlcmd.Controller.commands.Command;
import com.vtom.sqlcmd.Controller.commands.Find;
import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.Model.JDBCDatabaseManager;
import com.vtom.sqlcmd.View.View;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.function.Function;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestClear {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = System.out::println;
        FunctionFactory factory = new FunctionFactory(manager);
        command = new Clear(factory.getClearFunction());
    }

    @Test
    public void testClearTable() throws SQLException, JDBCDatabaseManager.SqlCmdException {
        command.process("clear|user");
        verify(manager).clear("user");

       }


    @Test
    public void testCantProcess() {
        boolean canProcess = command.canProcess("clear|user");
        assertTrue(canProcess);
    }

    @Test
    public void getClearFunctionPositive() throws Exception {
        FunctionFactory factory = new FunctionFactory(manager);

        String result = factory.getClearFunction().apply("clear|ASDADA");
        System.out.println();
        assertEquals("команда виконана успішно",result);
    }

    @Test
    public void testClearTableWrongCommand() {
        boolean  canNotProcess = command.canProcess("cleardf|user");
        assertFalse(canNotProcess);
    }

    @Test
    public void testCantProcessClearWithoutParametersString() {
        boolean canProcess = command.canProcess("clear|");
        assertTrue(canProcess);
    }

    @Test
    public void testValidationErrorWhenCountParametersIsLessThan2() {
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getClearFunction();
        try {
            String result = f.apply("clear|");
        } catch (Exception e) {
            assertEquals("кількість аргументів має бути 2 в форматі clear|tablename", e.getMessage());
            return;
        }
        fail();
    }


    @Test
    public void testValidationErrorWhenCountParametersIsMoreThan2() {
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getClearFunction();
        try {
            String result = f.apply("clear|table|table");
        } catch (Exception e) {
            assertEquals("кількість аргументів має бути 2 в форматі clear|tablename", e.getMessage());
            return;
        }
        fail();
    }

}
