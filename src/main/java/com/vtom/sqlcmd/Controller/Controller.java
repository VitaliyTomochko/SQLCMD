package com.vtom.sqlcmd.Controller;

import com.vtom.sqlcmd.Controller.commands.*;
import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.View.View;


import java.util.Arrays;
import java.util.Scanner;


public class Controller {
    View view;
    DatabaseManager manager;
    ICommand[] commands;

    public Controller(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        FunctionFactory factory = new FunctionFactory(manager);

        commands = new ICommand[]{
                new Connect(factory.getConnectFunction())
                , new Create(factory.getCreateFunction())
                , new Insert(factory.getInsertFunction())
                , new Delete(factory.getDeleteFunction())
                , new Update(factory.getUpdateFunction())
                , new Drop(factory.getDropFunction())
                , new TableList(factory.getTableListFunction())
                , new Find(factory.getFindFunction())
                , new Clear(factory.getClearFunction())
                , new NotExisted()
                , new Exit()
                , new Help()

        };
    }


    public void run() {
        execute();
    }

    private void execute() {
        view.print("Welcome to SQLCmd!");
        view.print("Для під'єднання до бази даних введіть ім'я бази даних," +
                " ім'я користувача та пароль у форматі: connect|database|username|password");

        Scanner s = new Scanner(System.in);
        while (true) {
            String inputCommand = s.nextLine();
            String result = Arrays.asList(commands)
                    .stream()
                    .filter(cmd -> cmd.canProcess(inputCommand))
                    .findFirst()
                    .orElse(new NotExisted())
                    .process(inputCommand);

            view.print(result);
            view.print("Введи команду (або help для допомоги) :");
        }
    }
}
