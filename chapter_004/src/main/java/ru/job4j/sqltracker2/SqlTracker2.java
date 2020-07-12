package ru.job4j.sqltracker2;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SqlTracker2 implements Store {
    public static final String LN = System.lineSeparator();
    private static final Logger LOG = LoggerFactory.getLogger(SqlTracker2.class);
    private Connection cn;
    private String nameTable;
    private Constructor<Item> constructor;
    private String fieldsNames;
    private String args;
    public static final String SELECTFROMS = "SELECT * FROM %s";
    /**
     * The St ссылка на метод, интерфейса {@link Statements}.
     * подготавливает PreparedStatement значения в соответствии
     * с типом данных всех столбцов конкретной таблицы
     */
    private Statements stm;

    public SqlTracker2(final String nameTable) {
        this.nameTable = nameTable;
    }

    /**
     * init.
     */
    @SuppressWarnings("ConstantConditions")
    @SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE")
    @Override
    public void init() {
        try (InputStream in = SqlTracker2.class.getClassLoader()
                .getResourceAsStream("sqltracker.properties")) {
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
        this.constructor = getConstructor();
    }

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
            doQuery(String.format(SELECTFROMS, name), name);
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
     * truncate указанную Table.
     */
    public void truncate() {
        String tableQuery = String.format(
                "TRUNCATE TABLE %s CASCADE", nameTable);
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
    public boolean replace(final String id, final Item item) {
        if (delete(id)) {
            add(item);
            return true;
        }
        return false;
    }

    /**
     * delete.
     *
     * @param id id
     * @return True if deleted
     */
    @SuppressFBWarnings("SQL_INJECTION_JDBC")
    @Override
    public boolean delete(final String id) {
        String tableQuery = String.format(
                "DELETE FROM %s WHERE id = %s",
                nameTable, id
        );
        try (PreparedStatement st = cn.prepareStatement(tableQuery)) {
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * Find by id item.
     * поиск записи по id в указанной таблице
     *
     * @param id the id
     * @return the item
     */
    @SuppressFBWarnings({"RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE", "SQL_INJECTION_JDBC"})
    @Override
    public Item findById(final String id) {
        String tableQuery = String.format("SELECT * FROM %s  WHERE id = %s",
                nameTable, id);
        Item item = null;
        try (Statement statement = cn.createStatement();
             ResultSet rs = statement.executeQuery(tableQuery)) {
            if (rs.next()) {
                item = newItem(rs);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return item;
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
     * add.
     * Statement.RETURN_GENERATED_KEYS);
     *
     * @param item item
     * @return String id
     */
    @Override
    public String add(final Item item) {
        int id = 0;
        String tableQuery = String.format("INSERT INTO %s (%s) VALUES(%s)",
                this.nameTable,
                this.fieldsNames,
                this.args
        );
        try (PreparedStatement prst = cn.prepareStatement(tableQuery,
                Statement.RETURN_GENERATED_KEYS)) {
            this.stm.statement(prst, item);
            prst.executeUpdate();
            id = getResult(prst);
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
    protected int getResult(final PreparedStatement st) {
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
     * Поиск по столбцу name в заданной таблице.
     *
     * @param key скорее всего name ключевой колонки
     * @return List<Item>
     */
    @Override
    public List<Item> findByName(final String key) {
        String tableQuery = String.format("SELECT * FROM %s  WHERE name = '%s'",
                nameTable, key);
        return select(tableQuery);
    }

    /**
     * findAll.
     *
     * @return list of all Items
     */
    @Override
    public List<Item> findAll() {
        String tableQuery = String.format(SELECTFROMS, this.nameTable);
        return select(tableQuery);
    }

    @SuppressFBWarnings({"SQL_INJECTION_JDBC", "RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE"})
    private List<Item> select(final String tableQuery) {
        List<Item> list = new ArrayList<>();
        try (Statement statement = cn.createStatement();
             ResultSet rs = statement.executeQuery(tableQuery)) {
            while (rs.next()) {
                list.add(newItem(rs));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * New item item.
     * Одна строка с id из таблицы. По условию задания, строки не дублируются.
     * Создаёт данные item в зависимости от указанной table.
     * Для простоты только два варианта "строк" таблиц.
     * Таблица product и type. Если таблиц больше, нужна будет дополнительная
     * коллекция map, заодно со ссылками на конструкторы этих таблиц
     *
     * @param rs the rs
     * @return the item
     * @throws SQLException the sql exception
     */
    private Item newItem(final ResultSet rs) throws SQLException {
        Item item = null;
        int columns = rs.getMetaData().getColumnCount();
        Object[] params = new Object[columns];
        for (int i = 1; i <= columns; i++) {
            int n = rs.getMetaData().getColumnType(i);
            params[i - 1] = map.get(n).getRS(rs, i);
        }
        try {
            item = constructor.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LOG.error(e.getMessage(), e);
        }
        return item;
    }

    interface GetResultSet<T> {
        T getRS(ResultSet rs, int column) throws SQLException;
    }


    private Map<Integer, GetResultSet<?>> map = Map.of(
            4, this::getInt,
            12, this::getString,
            93, this::getTimestamp,
            2, this::getBigDecimal
    );

    private String getString(final ResultSet rs, final int column) throws SQLException {
        return rs.getString(column);
    }

    private Integer getInt(final ResultSet rs, final int column) throws SQLException {
        return rs.getInt(column);
    }

    private Timestamp getTimestamp(final ResultSet rs, final int column) throws SQLException {
        return rs.getTimestamp(column);
    }

    private BigDecimal getBigDecimal(final ResultSet rs, final int column) throws SQLException {
        return rs.getBigDecimal(column);
    }

    private List<Class<?>> getFields() {
        List<Class<?>> fields = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try (Statement st = cn.createStatement()) {
            st.execute(String.format(SELECTFROMS, this.nameTable));
            try (ResultSet resalt = st.getResultSet()) {
                resalt.next();
                ResultSetMetaData rsm = resalt.getMetaData();
                int m = rsm.getColumnCount();
                for (int n = 1; n <= m; ++n) {
                    fields.add(Class.forName(rsm.getColumnClassName(n)));
                    sb.append(rsm.getColumnName(n));
                    if (n < m) {
                        sb.append(",");
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        // without column id
        this.fieldsNames = sb.substring(sb.indexOf(",") + 1);
        this.args = "?".concat(", ?".repeat(fieldsNames.split(",").length - 1));
        setStatement();
        return fields;
    }

    private void setStatement() {
        if ("product".equals(nameTable)) {
            this.stm = this::prodStatement;
        } else {
            this.stm = this::typeStatement;
        }
    }

    private Constructor<Item> getConstructor() {
        Constructor<Item> construct = null;
        try {
            List<Class<?>> fields = getFields();
            Class<?>[] field = new Class[fields.size()];
            fields.toArray(field);
            construct = Item.class.getConstructor(field);
        } catch (NoSuchMethodException e) {
            LOG.error(e.getMessage(), e);
        }
        return construct;
    }

    /**
     * The interface Statement.
     * ссылка на метод, в класса таблиц
     * поле {@code Statement st}
     * подготавливает PreparedStatement значения в соответствии
     * с типом данных всех столбцов конкретной таблицы
     */
    interface Statements {
        void statement(PreparedStatement st, Item item) throws SQLException;
    }

    /**
     * Product prepared statement.
     *
     * @param st   the st
     * @param item the item
     * @throws SQLException the sql exception
     */
    protected void prodStatement(final PreparedStatement st, final Item item)
            throws SQLException {
        st.setString(1, item.name);
        st.setInt(2, item.typeid);
        st.setTimestamp(3, item.expireddate);
        st.setBigDecimal(4, new BigDecimal(String.valueOf(item.price)));
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
        st.setString(1, item.name);
    }

    public static class Item {
        private Integer id;
        private String name;
        private Integer typeid;
        private Timestamp expireddate;
        private BigDecimal price = BigDecimal.ZERO;

        public Item(final Integer id, final String name) {
            this.id = id;
            this.name = name;
        }

        public Item(final Integer id, final String name, final Integer typeid,
                    final Timestamp expireddate, final BigDecimal price) {
            this.id = id;
            this.name = name;
            this.typeid = typeid;
            this.expireddate = expireddate;
            this.price = price;
        }

        /**
         * Returns a string representation of the object.
         *
         * @return a string representation of the object.
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(id).append(" ").append(name).append(" ").append(typeid).append(" ")
                    .append(expireddate).append(" ").append(price);
            return sb.toString();
        }
    }
}
