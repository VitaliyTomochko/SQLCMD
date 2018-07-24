package com.vtom.sqlcmd.Controller.commands;

public class ExitException extends RuntimeException {
    public ExitException(String message) {
        super(message);
    }
}
