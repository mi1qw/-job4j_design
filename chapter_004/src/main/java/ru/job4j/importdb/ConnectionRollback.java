package ru.job4j.importdb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.internal.util.reflection.FieldSetter;
import ru.job4j.sqltracker.SqlTracker;

import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * Connection, which rollback all commits.
 * It is used for integration test.
 */
final class ConnectionRollback {
    private static final Logger LOG = LogManager.getLogger(ConnectionRollback.class);

    protected ConnectionRollback() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Create connection with autocommit=false mode and rollback call, when conneciton is closed.
     *
     * @param connection connection.
     * @return Connection object.
     * @throws NoSuchFieldException the no such field exception
     */
    public static Connection create(final Connection connection) throws NoSuchFieldException {
        SqlTracker sql = new SqlTracker();
        FieldSetter.setField(sql, sql.getClass().getDeclaredField("cn"), connection);
        return (Connection) Proxy.newProxyInstance(
                ConnectionRollback.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    Object rsl = null;
                    LOG.info("Connection ......{}", method.getName());
                    if ("close".equals(method.getName())) {
                        LOG.info("close");
                        sql.isAnyTable();
                        rsl = method.invoke(connection, args);
                    } else if ("commit".equals(method.getName())) {
                        LOG.info("commit");
                        sql.isAnyTable();
                        boolean auto = connection.getAutoCommit();
                        connection.setAutoCommit(false);
                        connection.rollback();
                        connection.setAutoCommit(auto);
                        rsl = method.invoke(connection, args);
                    } else if ("prepareStatement".equals(method.getName())) {
                        LOG.info("prepareStatement");
                        sql.isAnyTable();
                        rsl = method.invoke(connection, args);
                    } else {
                        rsl = method.invoke(connection, args);
                    }
                    return rsl;
                }
        );
    }
}