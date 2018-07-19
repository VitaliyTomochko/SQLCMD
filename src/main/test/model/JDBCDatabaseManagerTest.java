package model;

import com.vtom.sqlcmd.Model.DatabaseManager;
import com.vtom.sqlcmd.Model.JDBCDatabaseManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JDBCDatabaseManagerTest {
    private DatabaseManager manager;


    @Before
    public void setUp() throws Exception {
        manager = new JDBCDatabaseManager();
        manager.connect("sqlcmd", "sqlcmd", "1478951");
    }


    @Test
    public void create() throws Exception {

        manager.create("test1", "id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225)");
        manager.create("test2", "id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225)");
        manager.create("test3", "id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225)");

        List<String> tables = manager.getTableNames();
        assertEquals("[test1, test2, test3]", tables.toString());
    }

    @Test
    public void getTableNames() throws Exception {
        List<String> tables = manager.getTableNames();
        assertEquals("[test1, test2, test3]", tables.toString());
    }

    @Test
    public void getTableData() throws Exception {
        // given
        manager.clear("test1");
        // when
        String str = "id,username,password";
        Object obj = "11','Stiven','123";

        manager.insert("test1", str, obj);
        // then
        List<Map<String, Object>> users = manager.getTableData("test1");
        assertEquals(1, users.size());
        Map<String, Object> user = users.get(0);
        assertEquals("[id, username, password]", Arrays.toString(user.keySet().toArray()));
        assertEquals("[11, Stiven, 123]", Arrays.toString(user.values().toArray()));
    }

    @Test
    public void update() throws Exception {
        // given
        manager.clear("test3");

        manager.create("test3", "id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225)");
        String str = "id,username,password";
        Object obj = "13','Stiven','123";

        manager.insert("test3", str, obj);

        // when
        String[] data=new String[]{"username","Stiven","password","111111111"};

        manager.update("test3", data[0],data[1],data[2],data[3]);

        // then
        List<Map<String, Object>> users = manager.getTableData("test3");
        Map<String, Object> user = users.get(0);
        assertEquals("[id, username, password]", Arrays.toString(user.keySet().toArray()));
        assertEquals("[13, Stiven, 111111111]", Arrays.toString(user.values().toArray()));

    }

    @Test
    public void clear() throws Exception {
        // given
        String str = "id,username,password";
        Object obj = "12','Stiven','123";

        manager.insert("test2", str, obj);
        // when
        manager.clear("test2");
        // then
        List<Map<String, Object>> users = manager.getTableData("test2");

        assertEquals(0, users.size());
    }

    @Test
    public void drop() throws Exception {
        manager.drop("test3");
        List<String> tables = manager.getTableNames();
        assertEquals("[test1, test2]", tables.toString());
        manager.create("test3", "id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225)");
    }

    @Test
    public void delete() throws Exception {
        manager.clear("test3");
        String str = "id,username,password";
        Object obj = "1','Stiven','123";

        manager.insert("test3", str, obj);

        String str1 = "id,username,password";
        Object obj1 = "13','BlackJack','13";

        manager.insert("test3", str1, obj1);



        manager.delete("test3","id","13");
        List<Map<String, Object>> users = manager.getTableData("test3");
        Map<String, Object> user = users.get(0);
        assertEquals("[id, username, password]", Arrays.toString(user.keySet().toArray()));
        assertEquals("[1, Stiven, 123]", Arrays.toString(user.values().toArray()));

    }

}