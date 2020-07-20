package ru.job4j.sqltracker2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        return (Connection) Proxy.newProxyInstance(
                ConnectionRollback.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    Object rsl = null;
                    LOG.info("Connection ......{}", method.getName());
                    if ("close".equals(method.getName())) {
                        LOG.info("close");
                        rsl = method.invoke(connection, args);
                    } else if ("commit".equals(method.getName())) {
                        LOG.info("commit");
                        boolean auto = connection.getAutoCommit();
                        connection.setAutoCommit(false);
                        connection.rollback();
                        connection.setAutoCommit(auto);
                        rsl = method.invoke(connection, args);
                    } else if ("prepareStatement".equals(method.getName())) {
                        LOG.info("prepareStatement");
                        rsl = method.invoke(connection, args);
                    } else {
                        rsl = method.invoke(connection, args);
                    }
                    return rsl;
                }
        );
    }
}