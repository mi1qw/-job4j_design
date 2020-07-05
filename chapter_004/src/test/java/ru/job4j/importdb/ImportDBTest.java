package ru.job4j.importdb;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.*;

//Sorts by method name
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@PowerMockRunnerDelegate(JUnit4.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*",
        "org.apache.http.conn.ssl.*", "com.amazonaws.*", "javax.net.ssl.*", "com.sun.*", "org.w3c.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({ImportDB.class})
public class ImportDBTest {
    private static final Logger LOG = LogManager.getLogger(ImportDBTest.class);
    private String fileDb = Objects.requireNonNull(ImportDB.class.getClassLoader().
            getResource("app_ImportDB.properties")).getFile();
    private String dump = Objects.requireNonNull(ImportDB.class.getClassLoader().
            getResource("dump.txt")).getFile();
    private ImportDB importDB;

    @Before
    public void setUp() {
        importDB = new ImportDB(fileDb, dump);
    }

    @Test
    public void a1Run() throws Exception {
        importDB = new ImportDB(fileDb, dump);
        ImportDB mock = spy(importDB);
        when(mock, "init").thenReturn(ConnectionRollback.create(this.init()));
        mock.go();
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void a0ExceptionInit() throws Exception {
        whenNew(Properties.class).withNoArguments().thenThrow(new Exception());
        importDB.go();
    }

    @Test(expected = IllegalStateException.class)
    public void a3ExceptionRollback() {
        new ConnectionRollback();
    }

    @SuppressFBWarnings("PATH_TRAVERSAL_IN")
    private Connection init() {
        Connection cn = null;
        try (FileInputStream in = new FileInputStream(fileDb)) {
            Properties cfg = new Properties();
            cfg.load(in);
            Class.forName(cfg.getProperty("jdbc.driver"));
            cn = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password"));
        } catch (IOException | ClassNotFoundException | SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return cn;
    }
}
