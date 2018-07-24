package com.vtom.sqlcmd.controller;

import com.vtom.sqlcmd.Controller.FunctionFactory;
import com.vtom.sqlcmd.Controller.commands.Command;
import com.vtom.sqlcmd.Controller.commands.Create;
import com.vtom.sqlcmd.Controller.commands.Find;
import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.View.View;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestCreate {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view =System.out::println;
        FunctionFactory factory = new FunctionFactory(manager);
        command = new Create(factory.getCreateFunction());
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("create|user");
        assertTrue(canProcess);
    }

    @Test
    public void testProcessWithWrongCommand() throws Exception {
        boolean canProcess = command.canProcess("creatsdfe|user");
        assertFalse(canProcess);
    }

    @Test
    public void testCantProcessClearWithoutParametersString() {
        boolean canProcess = command.canProcess("create|");
        assertTrue(canProcess);
    }

    @Test
    public void testProcess() throws Exception {
        command.process("create|user|id SERIAL NOT NULL PRIMARY KEY|username varchar(225) NOT NULL UNIQUE|password varchar(225))");
        verify(manager).create("user","id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE,password varchar(225))");
    }

    @Test
    public void testProcessWithoutParameters() throws Exception {
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getCreateFunction();
        try {
            String result = f.apply("create");
        } catch (Exception e) {
            assertEquals("кількість аргументів має бути > 3  в форматі SQL запиту  create|tablename|id SERIAL NOT NULL PRIMARY KEY|username varchar(225) NOT NULL UNIQUE| password varchar(225)", e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void testProcessWrongFormat() throws Exception {
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getCreateFunction();
        try {
            String result = f.apply("create|tables|we");
        } catch (Exception e) {
            assertEquals("кількість аргументів має бути > 3  в форматі SQL запиту  create|tablename|id SERIAL NOT NULL PRIMARY KEY|username varchar(225) NOT NULL UNIQUE| password varchar(225)", e.getMessage());
            return;
        }
        fail();
    }


}