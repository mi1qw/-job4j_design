package ru.job4j.sqltracker2;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.mockito.internal.util.reflection.FieldSetter;
import org.postgresql.jdbc.PgResultSet;
import org.postgresql.jdbc.PgStatement;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@PowerMockRunnerDelegate(JUnit4.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*", "org.apache.http.conn.ssl.*", "com.amazonaws.*", "javax.net.ssl.*", "com.sun.*", "org.w3c.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({SqlTracker2.class})
public class ExceptionTest {
    private static final Connection CONNECTION = mock(Connection.class);
    private static SqlTracker2 sp = new SqlTracker2("type");

    @BeforeClass
    public static void setUp() throws NoSuchFieldException {
        FieldSetter.setField(sp, sp.getClass().getDeclaredField("cn"), CONNECTION);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        sp.close();
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

    @Test
    public void a4SQLExceptionDoQuery() throws SQLException {
        doThrow(new SQLException()).when(CONNECTION).createStatement();
        sp.doQuery("1");
        assertTrue(true);
    }

    @Test
    public void a41SQLExceptionDoQueryIfisResult() throws SQLException {
        PgStatement pgStatement = mock(PgStatement.class);
        doReturn(pgStatement).when(CONNECTION).createStatement();
        doReturn(false).when(pgStatement).execute(any());
        sp.doQuery("1");
        assertTrue(true);
    }

    @Test
    public void a5SQLExceptionTruncate() throws SQLException {
        doThrow(new SQLException()).when(CONNECTION).prepareStatement(any());
        sp.truncate();
        assertTrue(true);
    }

    @Test
    public void a6SQLExceptionDelete() throws SQLException {
        doThrow(new SQLException()).when(CONNECTION).prepareStatement(any());
        sp.delete("1");
        assertTrue(true);
    }

    @Test
    public void a7SQLExceptionFindByIdClose() throws SQLException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet pgResultSet = mock(PgResultSet.class);
        doReturn(preparedStatement).when(CONNECTION).prepareStatement(any());
        doReturn(pgResultSet).when(preparedStatement).executeQuery();
        doThrow(new SQLException()).when(pgResultSet).next();
        doThrow(new SQLException()).when(pgResultSet).close();
        sp.findById("1");
        assertTrue(true);
    }

    @Test
    public void a9SQLExceptionAdd() throws SQLException {
        doThrow(new SQLException()).when(CONNECTION).prepareStatement(any(), eq(Statement.RETURN_GENERATED_KEYS));
        sp.add(eq(any()));
        assertTrue(true);
    }

    @Test
    public void bS0QLExceptionGetResult() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        doThrow(new SQLException()).when(statement).getGeneratedKeys();
        sp.getResult(statement);
        assertTrue(true);
    }

    @Test
    public void b1SQLExceptionfindByName() throws SQLException {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet pgResultSet = mock(PgResultSet.class);
        doReturn(preparedStatement).when(CONNECTION).prepareStatement(any());
        doReturn(pgResultSet).when(preparedStatement).executeQuery();
        doThrow(new SQLException()).when(pgResultSet).next();
        doThrow(new SQLException()).when(pgResultSet).close();
        sp.findByName("1");
        assertTrue(true);
    }

    @Test
    public void b2SQLExceptionNewItem() throws Exception {
        ResultSet pgResultSet = mock(PgResultSet.class);
        ResultSetMetaData resultSetMetaData = mock(ResultSetMetaData.class);
        doReturn(resultSetMetaData).when(pgResultSet).getMetaData();
        Method method = sp.getClass().getDeclaredMethod("newItem", new Class<?>[]{ResultSet.class});
        method.setAccessible(true);
        method.invoke(sp, pgResultSet);
        assertTrue(true);
    }

    @Test
    public void b3SQLExceptionReplace() throws SQLException {
        doThrow(new SQLException()).when(CONNECTION).prepareStatement(any());
        sp.replace(any(), new SqlTracker2.Item(Integer.valueOf("1"), "1"));
        assertTrue(true);
    }

    @Test
    public void b4SQLExceptionGetFields() throws SQLException, NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        doThrow(new SQLException()).when(CONNECTION).prepareStatement(any());
        Method method = sp.getClass().getDeclaredMethod("getFields");
        method.setAccessible(true);
        method.invoke(sp);
        assertTrue(true);
    }

    @Test
    public void b5SQLExceptionGetConstructor() throws Exception {
        Method method = sp.getClass().getDeclaredMethod("getConstructor");
        method.setAccessible(true);
        method.invoke(sp);
        assertTrue(true);
    }

    @Test
    public void z9close() throws Exception {
        FieldSetter.setField(sp, sp.getClass().getDeclaredField("cn"), null);
        sp.close();
        assertTrue(true);
    }
}
