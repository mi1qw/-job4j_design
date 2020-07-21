package ru.job4j.sqltracker2;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class SqlTracker2 implements Store {
    public static final String LN = System.lineSeparator();
    private static final Logger LOG = LoggerFactory.getLogger(SqlTracker2.class);
    private Connection cn;
    /**
     * имя таблицы.
     */
    private String nameTable;
    /**
     * Конструктор для создания Item.
     */
    private Constructor<Item> constructor;
    /**
     * Имена столбцов.
     */
    private String fieldsNames;
    /**
     * Список типов столбцов в int.
     */
    private List<Integer> fieldType = new ArrayList<>();
    /**
     * Аргументы в виде ?,?..
     */
    private String args;
    /**
     * values для INSERT.
     * столбец1 = ?, столбец2 = ?..
     */
    private String values;
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
        this.values = values();
    }

    /**
     * Set all tables.
     * Если имеются таблицы, вывести их содержимое
     *
     * @return the boolean
     */
    @SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE")
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
            if ("databasechangeloglock".equals(name) || "databasechangelog".equals(name)) {
                continue;
            }
            doQuery(String.format(SELECTFROMS, name), name);
        }
    }

    /**
     * Выполнить как-то запрос/команду
     *
     * @param query query + строка для вывода в лог
     */
    @SuppressFBWarnings({"RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE", "SQL_INJECTION_JDBC", "RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE"})
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
                if (query.length > 1) {
                    LOG.info("Query {}{}", str, sb);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * truncate указанную Table.
     */
    @SuppressFBWarnings("SQL_INJECTION_JDBC")
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
     * delete.
     *
     * @param id id
     * @return True if deleted
     */
    @SuppressFBWarnings("SQL_INJECTION_JDBC")
    @Override
    public boolean delete(final String id) {
        String tableQuery = String.format("DELETE FROM %s WHERE id = ?", nameTable);
        try (PreparedStatement st = cn.prepareStatement(tableQuery)) {
            st.setInt(1, Integer.parseInt(id));
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
    @SuppressFBWarnings({"SQL_INJECTION_JDBC", "NP_GUARANTEED_DEREF_ON_EXCEPTION_PATH"})
    @Override
    public Item findById(final String id) {
        ResultSet rs = null;
        String tableQuery = String.format("SELECT * FROM %s  WHERE id = ?", nameTable);
        Item item = null;
        try (PreparedStatement statement = cn.prepareStatement(tableQuery)) {
            statement.setInt(1, Integer.parseInt(id));
            rs = statement.executeQuery();
            if (rs.next()) {
                item = newItem(rs);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                Objects.requireNonNull(rs).close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
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
    @SuppressFBWarnings({"SQL_INJECTION_JDBC", "RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE"})
    @Override
    public String add(final Item item) {
        int id = 0;
        try (PreparedStatement prst = cn.prepareStatement(
                String.format("INSERT INTO %s VALUES(DEFAULT,%s)", nameTable, args),
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
    @SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE")
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
        String tableQuery = String.format("SELECT * FROM %s  WHERE name = ?", this.nameTable);
        return select(tableQuery, key);
    }

    /**
     * findAll.
     *
     * @return list of all Items
     */
    @Override
    public List<Item> findAll() {
        String tableQuery = String.format(SELECTFROMS, this.nameTable);
        return select(tableQuery, null);
    }

    @SuppressFBWarnings({"SQL_INJECTION_JDBC", "NP_GUARANTEED_DEREF_ON_EXCEPTION_PATH"})
    private List<Item> select(final String tableQuery, final String key) {
        List<Item> list = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement statement = cn.prepareStatement(tableQuery)) {
            if (key != null) {
                statement.setString(1, key);
            }
            rs = statement.executeQuery();
            while (rs.next()) {
                list.add(newItem(rs));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                Objects.requireNonNull(rs).close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
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
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return item;
    }

    private String values() {
        String[] value = fieldsNames.split(",");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            sb.append(value[i]).append("=?");
            if (i < value.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    /**
     * replace по id в указанной таблице.
     *
     * @param id   id
     * @param item item
     * @return true если успешно
     */
    @SuppressFBWarnings("SQL_INJECTION_JDBC")
    @Override
    public boolean replace(final String id, final Item item) {
        int res = 0;
        int n;
        try (PreparedStatement statement = cn.prepareStatement(
                String.format("UPDATE %s SET %s WHERE id = ?", nameTable, values))) {
            for (n = 1; n <= item.argItem.size(); ++n) {
                maps.get(fieldType.get(n)).setPS(statement, n, item.argItem.get(n - 1));
            }
            statement.setInt(n, Integer.parseInt(id));
            res = statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return res > 0;
    }

    interface GetResultSet<T> {
        T getRS(ResultSet rs, int column) throws SQLException;
    }


    interface SetPrepStatement {
        void setPS(PreparedStatement prs, int column, Object val) throws SQLException;
    }


    private Map<Integer, GetResultSet<?>> map = Map.of(
            4, this::getInt,
            12, this::getString,
            93, this::getTimestamp,
            2, this::getBigDecimal
    );
    private Map<Integer, SetPrepStatement> maps = Map.of(
            4, (prs, column, intval) -> setInt(prs, column, (Integer) intval),
            12, (prs, column, str) -> setString(prs, column, String.valueOf(str)),
            93, (prs, column, time) -> setTimestamp(prs, column, (Timestamp) time),
            2, (prs, column, bigDecimal)
                    -> setBigDecimal(prs, column, (BigDecimal) bigDecimal)
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

    private void setString(final PreparedStatement prs, final int column, final String str) throws SQLException {
        prs.setString(column, str);
    }

    private void setInt(final PreparedStatement prs, final int column, final int intval) throws SQLException {
        prs.setInt(column, intval);
    }

    private void setTimestamp(final PreparedStatement prs, final int column, final Timestamp time) throws SQLException {
        prs.setTimestamp(column, time);
    }

    private void setBigDecimal(final PreparedStatement prs, final int column,
                               final BigDecimal bigDecimal) throws SQLException {
        prs.setBigDecimal(column, bigDecimal);
    }

    @SuppressFBWarnings({"RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE", "SQL_INJECTION_JDBC", "RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE"})
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
                    this.fieldType.add(rsm.getColumnType(n));
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
        private List<Object> argItem;

        public Item(final Integer id, final String name) {
            this.id = id;
            this.name = name;
            argItem = List.of(name);
        }

        @SuppressFBWarnings("EI_EXPOSE_REP2")
        public Item(final Integer id, final String name, final Integer typeid,
                    final Timestamp expireddate, final BigDecimal price) {
            this.id = id;
            this.name = name;
            this.typeid = typeid;
            this.expireddate = expireddate;
            this.price = price;
            argItem = List.of(name, typeid, expireddate, price);
        }

        /**
         * Gets values item.
         *
         * @return the values item
         */
        public List<Object> getValuesItem() {
            return argItem;
        }
    }
}
