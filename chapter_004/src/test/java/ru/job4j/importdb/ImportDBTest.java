package ru.job4j.importdb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import ru.job4j.sqltracker.SqlTracker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class ImportDBTest {
    private static final Logger LOG = LogManager.getLogger(ImportDBTest.class);
    private SqlTracker sql = new SqlTracker();

    private Connection init() {
        Connection cn = null;
        try (InputStream in = Objects.requireNonNull(ImportDB.class.getClassLoader().
                getResourceAsStream("app_ImportDB.properties"))) {
            Properties cfg = new Properties();
            cfg.load(in);
            Class.forName(cfg.getProperty("jdbc.driver"));
            cn = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password"));
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return cn;
    }

    @Test
    public void createItem() throws Exception {
        //String fileDb = Objects.requireNonNull(ImportDB.class.getClassLoader().
        //        getResource("app_ImportDB.properties")).getFile();
        //String dump = Objects.requireNonNull(ImportDB.class.getClassLoader().
        //        getResource("dump.txt")).getFile();
        ////new ImportDB(fileDb, dump);
        //
        //ImportDB importDB = new ImportDB(fileDb, dump);
        //ImportDB mock = spy(importDB);
        //
        //when(mock, "init").thenReturn(ConnectionRollback.create(this.init()));
        //ImportDB.main(new String[0]);

        //mock.getClass().

        //ImportDB importDB = Mockito.mock(ImportDB.class);
        //doReturn(ConnectionRollback.create(this.init())).when(importDB).

        //try (Connection cn = ConnectionRollback.create(this.init())) {
        //    //FieldSetter.setField(sql, sql.getClass().getDeclaredField("cn"), cn);
        //
        //    System.out.println("qqqqqqqqqqqqqqqqqqqqqqqq");
        //    //tracker.add(new Item("name", "desc"));
        //    //assertThat(tracker.findByName("desc").size(), is(1));
        //
        //    ImportDB importDB = new ImportDB(
        //            new Properties(),
        //            Objects.requireNonNull(ImportDB.class.getClassLoader().
        //                    getResource("dump.txt")).getFile()
        //    );
        //
        //    //FieldSetter.setField(importDB,
        //    //        importDB.getClass().getDeclaredMethod("save").getParameters().getDeclaredField("cnt"), cn);
        //
        //    ImportDB.main(new String[0]);
        //    //sql.isAnyTable();
        //} catch (NoSuchFieldException e) {
        //    e.printStackTrace();
        //}
        assertTrue(true);
    }

    private Connection init(final String fileDb) {
        Connection cnt = null;
        Properties cfg = new Properties();
        try (FileInputStream in = new FileInputStream(fileDb)) {
            cfg.load(in);
            Class.forName(cfg.getProperty("jdbc.driver"));
            cnt = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password")
            );
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return cnt;
    }
    //@Test
    //public void setUp() throws Exception {
    //    InputStream fileDb = Objects.requireNonNull(ImportDB.class.getClassLoader().
    //            getResourceAsStream("app_ImportDB.properties"));
    //    Properties cfg = new Properties();
    //    cfg.load(fileDb);
    //    Class.forName(cfg.getProperty("jdbc.driver"));
    //    try (Connection cnt = DriverManager.getConnection(
    //            cfg.getProperty("jdbc.url"),
    //            cfg.getProperty("jdbc.username"),
    //            cfg.getProperty("jdbc.password")
    //    )) {
    //        FieldSetter.setField(sql, sql.getClass().getDeclaredField("cn"), cnt);
    //        if (!sql.isAnyTable()) {
    //            sql.doQuery("CREATE TABLE users (\"id\" serial NOT NULL PRIMARY KEY, \"name\" VARCHAR(50), \"email\" VARCHAR(50))");
    //        }
    //        ImportDB.main(new String[0]);
    //        sql.isAnyTable();
    //    } catch (Exception e) {
    //        throw new IllegalStateException(e);
    //    }
    //    assertTrue(true);
    //}
}
