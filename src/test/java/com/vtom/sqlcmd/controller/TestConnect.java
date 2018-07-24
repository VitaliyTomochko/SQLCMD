package com.vtom.sqlcmd.controller;

import com.vtom.sqlcmd.Controller.FunctionFactory;
import com.vtom.sqlcmd.Controller.commands.Command;
import com.vtom.sqlcmd.Controller.commands.Connect;
import com.vtom.sqlcmd.Controller.commands.Find;
import com.vtom.sqlcmd.Controller.commands.ICommand;
import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.View.View;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class TestConnect {
    DatabaseManager manager;
    View view;
    Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = System.out::println;;
        FunctionFactory factory = new FunctionFactory(manager);
        command = new Connect(factory.getConnectFunction());
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("connect|");
        assertTrue(canProcess);
    }

    @Test
    public void testCanNotProcess() throws Exception {
        boolean canProcess = command.canProcess("conneeeect|");
        assertFalse(canProcess);
    }


}