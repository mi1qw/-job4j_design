package ru.job4j.sqltracker;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.internal.util.reflection.FieldSetter;
import org.postgresql.jdbc.PgResultSet;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@PowerMockIgnore({"javax.net.ssl.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({SqlTracker.class})
public class ExceptionTest {
    private static SqlTracker sp = new SqlTracker();
    private static final Connection CONNECTION = mock(Connection.class);

    @BeforeClass
    public static void setUp() throws NoSuchFieldException {
        FieldSetter.setField(sp, sp.getClass().getDeclaredField("cn"), CONNECTION);
    }

    @Test(expected = IllegalStateException.class)
    public void a1ExceptionInit() throws Exception {
        whenNew(Properties.class).withNoArguments().thenThrow(new Exception());
        sp.init();
    }

    @Test
    public void a2EmptyDatabaseNotContainTables() throws Exception {
        DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
        ResultSet pgResultSet = mock(PgResultSet.class);
        ResultSet resultSet = mock(ResultSet.class);
        doReturn(databaseMetaData).when(CONNECTION).getMetaData();
        doReturn(resultSet).when(databaseMetaData).getTables(
                null, null, null, new String[]{"TABLE"});
        doReturn(false).when(pgResultSet).next();
        assertFalse(sp.isAnyTable());
    }

    @Test
    public void a3SQLExceptionIsAnyTable() throws SQLException {
        doThrow(new SQLException()).when(CONNECTION).getMetaData();
        sp.isAnyTable();
        assertTrue(true);
    }

    @SuppressFBWarnings("DLS_DEAD_LOCAL_STORE")
    @Test
    public void a4ExceptionToString() throws Exception {
        whenNew(StringBuilder.class).withNoArguments().thenThrow(new ReflectiveOperationException());
        Item item = new Item(new Table("any", "any", sp::typeStatement), "any");
        String str = item.toString();
        assertTrue(true);
    }
}
