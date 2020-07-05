package ru.job4j.importdb;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ImportDB {
    private String dump;
    private Connection cnt = null;
    private String fileDb;

    public ImportDB(final String fileDb, final String dump) {
        this.fileDb = fileDb;
        this.dump = dump;
    }

    /**
     * запуск.
     *
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    public void go() throws IOException, SQLException {
        cnt = init();
        save(load());
        cnt.close();
    }

    /**
     * Load list.
     *
     * @return the list
     * @throws IOException the io exception
     */
    @SuppressFBWarnings({"DM_DEFAULT_ENCODING", "PATH_TRAVERSAL_IN"})
    public List<User> load() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader rd = new BufferedReader(new FileReader(dump))) {
            rd.lines().forEach(n -> {
                String[] user = n.split(";");
                users.add(new User(user[0].trim(), user[1].trim()));
            });
        }
        return users;
    }

    @SuppressFBWarnings("PATH_TRAVERSAL_IN")
    private Connection init() {
        Connection connection;
        try (FileInputStream in = new FileInputStream(fileDb)) {
            Properties cfg = new Properties();
            cfg.load(in);
            Class.forName(cfg.getProperty("jdbc.driver"));
            connection = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return connection;
    }

    /**
     * Save.
     *
     * @param users the users
     * @throws SQLException the sql exception
     */
    public void save(final List<User> users) throws SQLException {
        cnt.setAutoCommit(false);
        try (PreparedStatement ps = cnt.prepareStatement(
                "insert INTO users(name, email) VALUES(?, ?)")) {
            for (User user : users) {
                ps.setString(1, user.name);
                ps.setString(2, user.email);
                ps.addBatch();
            }
            ps.executeBatch();
        }
        cnt.commit();
        cnt.setAutoCommit(true);
    }

    private static class User {
        private final String name;
        private final String email;

        /**
         * Instantiates a new User.
         *
         * @param name  the name
         * @param email the email
         */
        User(final String name, final String email) {
            this.name = name;
            this.email = email;
        }
    }
}
