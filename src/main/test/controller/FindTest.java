package controller;

import com.vtom.sqlcmd.Controller.FunctionFactory;
import com.vtom.sqlcmd.Controller.commands.Command;
import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.Model.JDBCDatabaseManager;
import com.vtom.sqlcmd.View.View;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class FindTest {

    private Command command;
    private DatabaseManager manager;
    private View view;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = System.out::println;

    }

    @Test
    public void testCanProcessWithParameters() throws SQLException, JDBCDatabaseManager.SqlCmdException {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("username", "Blackjack");
        map1.put("id", 1);
        map1.put("password", 12312);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("username", "WhiteJack");
        map2.put("id", 2);
        map2.put("password", 11111);
        List<Map<String, Object>> tableData = Arrays.asList(map1, map2);
        when(manager.getTableNames()).thenReturn(Arrays.asList("sdfvsxcv"));
        when(manager.getTableData(any())).thenReturn(tableData);
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getFindFunction();
        String result = f.apply("find|sdfvsxcv");

        System.out.println(result);
        assertEquals("+-----------+----------+----------+\n" +
                "|password   |id        |username  |\n" +
                "+-----------+----------+----------+\n" +
                "|12312      |1         |Blackjack |\n" +
                "+-----------+----------+----------+\n" +
                "|11111      |2         |WhiteJack |\n" +
                "+-----------+----------+----------+", result);
    }

    @Test
    public void testCanProcessWithoutParameters() throws SQLException, JDBCDatabaseManager.SqlCmdException {
        FunctionFactory factory = new FunctionFactory(manager);
        Function<String, String> f = factory.getFindFunction();
        try {
            String result = f.apply("find|");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "кількість аргументів має бути 2");
            return;
        }
        fail();
    }
}

