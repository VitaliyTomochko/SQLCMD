package com.vtom.sqlcmd.Controller;

import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.Model.JDBCDatabaseManager;
import com.vtom.sqlcmd.View.View;

public class Main {

    public static void main(String[] args) {

        DatabaseManager manager = new JDBCDatabaseManager();
        View view = System.out::println;
        Controller controller = new Controller(manager, view);
        controller.run();

    }
}
