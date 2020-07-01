package ru.job4j.importdb;

import org.junit.Test;
import org.mockito.internal.util.reflection.FieldSetter;
import ru.job4j.sqltracker.SqlTracker;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class ImportDBTest {
    private SqlTracker sql = new SqlTracker();

    @Test
    public void setUp() throws Exception {
        InputStream fileDb = Objects.requireNonNull(ImportDB.class.getClassLoader().
                getResourceAsStream("app_ImportDB.properties"));
        Properties cfg = new Properties();
        cfg.load(fileDb);
        Class.forName(cfg.getProperty("jdbc.driver"));
        try (Connection cnt = DriverManager.getConnection(
                cfg.getProperty("jdbc.url"),
                cfg.getProperty("jdbc.username"),
                cfg.getProperty("jdbc.password")
        )) {
            FieldSetter.setField(sql, sql.getClass().getDeclaredField("cn"), cnt);
            if (!sql.isAnyTable()) {
                sql.doQuery("CREATE TABLE users (\"id\" serial NOT NULL PRIMARY KEY, \"name\" VARCHAR(50), \"email\" VARCHAR(50))");
            }
            ImportDB.main(new String[0]);
            sql.isAnyTable();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        assertTrue(true);
    }
}