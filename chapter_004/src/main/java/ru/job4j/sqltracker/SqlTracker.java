package ru.job4j.sqltracker;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SuppressFBWarnings({"SQL_INJECTION_JDBC", "RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE"})
public class SqlTracker implements Store {
    public static final String LN = System.lineSeparator();
    private static final Logger LOG = LoggerFactory.getLogger(SqlTracker.class);
    private Connection cn;
    private String idStr = "";
    private String idArg = "";

    /**
     * Set all tables.
     * Если имеются таблицы, вывести их содержимое
     *
     * @return the boolean
     */
    @Override
    public boolean isAnyTable() {
        boolean res = false;
        List<String> list = new ArrayList<>();
        try (ResultSet mrs = cn.getMetaData().getTables(null, null,
                null, new String[]{"TABLE"})) {
            while (mrs.next()) {
                list.add(mrs.getString(3));
            }
            if (!list.isEmpty()) {
                res = true;
                displayTables(list);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * Display tables by name.
     *
     * @param list the List<String> of tables
     */
    @Override
    public void displayTables(final List<String> list) {
        for (String name : list) {
            doQuery(String.format("SELECT * FROM %s", name), name);
        }
    }

    /**
     * Выполнить как-то запрос/команду
     *
     * @param query query + строка для вывода в лог
     */
    @Override
    public void doQuery(final String... query) {
        try (Statement st = cn.createStatement()) {
            boolean isResult = st.execute(query[0]);
            if (isResult) {
                StringBuilder sb = new StringBuilder();
                try (ResultSet resalt = st.getResultSet()) {
                    while (resalt.next()) {
                        for (int n = 1; n <= resalt.getMetaData().getColumnCount(); ++n) {
                            sb.append(resalt.getObject(n));
                            sb.append(" ");
                        }
                        sb.append(LN);
                    }
                }
                String str = query.length > 1 ? query[1].concat(LN) : "";
                LOG.info("Query {}{}", str, sb);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Prod prepared statement.
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
     * Type prepared statement.
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
            StringBuilder sb = new StringBuilder();
            sb.append("DataBase ").append(cn.getCatalog()).append(LN).
                    append("URL ").append(cn.getMetaData().getURL()).append(LN).
                    append("UserName ").append(cn.getMetaData().getUserName()).append(LN);

            LOG.info("Connected to {}", sb);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * add.
     * Statement.RETURN_GENERATED_KEYS);
     *
     * @param item item
     * @return String id
     */
    @Override
    public String add(final Item item) {
        int id = 0;
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
            LOG.error(e.getMessage(), e);
        }
        return String.valueOf(id);
    }

    /**
     * Gets result.
     *
     * @param st Statement
     * @return id result
     */
    protected int getResult(final PreparedStatement st) throws SQLException {
        int id = 0;
        try (ResultSet rs = st.getGeneratedKeys()) {
            rs.next();
            id = rs.getInt(1);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return id;
    }

    /**
     * Добавить строку в id указанной таблицы.
     *
     * @param id   id.
     * @param item item
     * @return idAdd aded id
     */
    @Override
    public String addInID(final String id, final Item item) {
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
     * truncate указанную Table.
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
     * replace по id в указанной таблице.
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
     * Поиск по столбцу name в заданной таблице.
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
     * Find by id item.
     * поиск записи по id в указанной таблице
     *
     * @param id    the id
     * @param table the table таблица поиска
     * @return the item
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

    /**
     * New item item.
     * Одна строка с id из таблицы. По условию задания, строки не дублируются.
     * Создаёт данные item в зависимости от указанной table.
     * Для простоты только два варианта "строк" таблиц.
     * Таблица product и type. Если таблиц больше, нужна будет дополнительная
     * коллекция map, заодно со ссылками на конструкторы этих таблиц
     *
     * @param rs    the rs
     * @param table the table
     * @return the item
     * @throws SQLException the sql exception
     */
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
}
