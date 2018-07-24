package com.vtom.sqlcmd.controller;

import com.vtom.sqlcmd.Controller.commands.Command;
import com.vtom.sqlcmd.Controller.commands.Exit;
import com.vtom.sqlcmd.Controller.commands.ExitException;
import com.vtom.sqlcmd.Controller.commands.ICommand;
import com.vtom.sqlcmd.View.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

public class TestExit {
    private View view = mock(View.class);
    private ICommand command;

    @Before
    public void setup() {
        command = new Exit();
        view = System.out::println;
    }

    @Test
    public void testExit() throws Exception {
        try {
            command.process("exit");
        } catch (Exception e) {
            assertEquals("програма завершила роботу", e.getMessage());
            return;
        }
        fail();
    }
}
