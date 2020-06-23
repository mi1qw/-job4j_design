package ru.job4j.sqltracker;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {
    private static final Logger LOG = LoggerFactory.getLogger(SqlTracker.class);
    private Connection cn;
    private String idStr = "";
    private String idArg = "";

    /**
     * Prod statement prepared statement.
     *
     * @param st   the st
     * @param item the item
     * @throws SQLException the sql exception
     */
    protected void prodStatement(final PreparedStatement st, final Item item)
            throws SQLException {
        st.setString(1, item.getName());
        st.setInt(2, item.getTypeid());
        st.setTimestamp(3, Timestamp.valueOf(item.getExpireddate()));
        st.setBigDecimal(4, new BigDecimal(String.valueOf(item.getPrice())));
    }

    /**
     * Type statement prepared statement.
     *
     * @param st   the st
     * @param item the item
     * @throws SQLException the sql exception
     */
    protected void typeStatement(final PreparedStatement st, final Item item)
            throws SQLException {
        st.setString(1, item.getName());
    }

    /**
     * init.
     */
    @SuppressWarnings("ConstantConditions")
    @SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE")
    @Override
    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            System.out.println("qqqqqqqqqqqqqq111111111111111");
            throw new IllegalStateException(e);
        }
    }

    /**
     * add.
     * Statement.RETURN_GENERATED_KEYS);
     *
     * @param item item
     * @return String id
     * @throws SQLException для теста
     */
    @SuppressFBWarnings({"SQL_INJECTION_JDBC", "RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE"})
    @Override
    public String add(final Item item) throws SQLException {
        int id;
        Table table = item.getTable();
        String tableQuery = String.format("INSERT INTO %s (%s) VALUES(%s)",
                table.getName(),
                idStr.concat(table.getColumn()),
                idArg.concat(table.getArgs())
        );
        try (PreparedStatement st = cn.prepareStatement(tableQuery,
                Statement.RETURN_GENERATED_KEYS)) {
            table.getSt().statement(st, item);
            st.executeUpdate();
            id = getResult(st);
        } catch (SQLException e) {
            LOG.info("Ошибка");
            LOG.error(e.getMessage(), e);
            throw new SQLException();
        }
        return String.valueOf(id);
    }

    /**
     * @param st Statement
     * @return id
     */
    protected int getResult(final PreparedStatement st) throws SQLException {
        int id = 0;
        try (ResultSet rs = st.getGeneratedKeys()) {
            //id = rs.next() ? rs.getInt(1) : 0;
            //if (rs.next()) {
            //    id = rs.getInt(1);
            //}
            boolean b = rs.next();
            id = rs.getInt(1);
        } catch (SQLException e) {
            LOG.info("Ошибка");
            LOG.error(e.getMessage(), e);
            throw new SQLException();
        }
        return id;
    }

    /**
     * @param id   id.
     * @param item item
     * @return idAdd aded id
     */
    @Override
    public String addInID(final String id, final Item item) throws SQLException {
        String idAdd = "-1";
        if (findById(id, item.getTable()) == null) {
            idStr = "id ,";
            idArg = id.concat(" ,");
            idAdd = add(item);
            idStr = "";
            idArg = "";
        }
        return idAdd;
    }

    /**
     * delete.
     *
     * @param id id
     * @return True if deleted
     */
    @SuppressFBWarnings("SQL_INJECTION_JDBC")
    @Override
    public boolean delete(final String id, final Table table) {
        String tableQuery = String.format(
                "DELETE FROM %s WHERE id = %s",
                table.getName(), id
        );
        try (PreparedStatement st = cn.prepareStatement(tableQuery)) {
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * truncate Table.
     *
     * @param table table
     */
    public void truncate(final Table table) {
        String tableQuery = String.format(
                "TRUNCATE TABLE %s CASCADE",
                table.getName()
        );
        try (PreparedStatement st = cn.prepareStatement(tableQuery)) {
            st.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * replace.
     *
     * @param id   id
     * @param item item
     * @return true если успешно
     */
    @Override
    public boolean replace(final String id, final Item item) throws SQLException {
        if (delete(id, item.getTable())) {
            idStr = "id ,";
            idArg = id.concat(" ,");
            add(item);
            idStr = "";
            idArg = "";
            return true;
        }
        return false;
    }

    /**
     * findByName.
     *
     * @param key скорее всего name ключевой колонки
     * @return List<Item>
     */
    @Override
    public List<Item> findByName(final String key, final Table table) {
        String tableQuery = String.format("SELECT * FROM %s  WHERE name = '%s'",
                table.getName(), key);
        return select(tableQuery, table);
    }

    /**
     * findAll.
     *
     * @return list of all Items
     */
    @Override
    public List<Item> findAll(final Table table) {
        String tableQuery = String.format("SELECT * FROM %s", table.getName());
        return select(tableQuery, table);
    }

    @SuppressFBWarnings({"SQL_INJECTION_JDBC", "RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE"})
    private List<Item> select(final String tableQuery, final Table table) {
        List<Item> list = new ArrayList<>();
        try (Statement statement = cn.createStatement();
             ResultSet rs = statement.executeQuery(tableQuery)) {
            while (rs.next()) {
                list.add(newItem(rs, table));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * findById.
     *
     * @param id id
     * @return item finded item
     */
    @SuppressFBWarnings({"RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE", "SQL_INJECTION_JDBC"})
    @Override
    public Item findById(final String id, final Table table) {
        String tableQuery = String.format("SELECT * FROM %s  WHERE id = %s",
                table.getName(), id);
        Item item = null;
        try (Statement statement = cn.createStatement();
             ResultSet rs = statement.executeQuery(tableQuery)) {
            if (rs.next()) {
                item = newItem(rs, table);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return item;
    }

    private Item newItem(final ResultSet rs, final Table table) throws SQLException {
        int columns = rs.getMetaData().getColumnCount();
        if (columns < 3) {
            return new Item(table, rs.getString(2));
        }
        return new Item(
                table,
                rs.getString(2),
                rs.getInt(3),
                rs.getString(4),
                rs.getBigDecimal(5)
        );
    }

    /**
     * close Connection.
     *
     * @throws Exception Exception
     */
    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    /**
     * для большего покрытия теста.
     *
     * @param cn Connection
     */
    public final void setCn(final Connection cn) {
        this.cn = cn;
    }
}

//
//@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
//class CollaboratorWithStaticMethods {
//    public static String firstMethod(final String name) {
//        return "Hello " + name + " !";
//    }
//
//    public static String secondMethod() {
//        return "Hello no one!";
//    }
//
//    public static String thirdMethod() {
//        return "Hello no one again!";
//    }
//}