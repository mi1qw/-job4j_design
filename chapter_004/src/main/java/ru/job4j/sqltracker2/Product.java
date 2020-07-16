package ru.job4j.sqltracker2;

//import ru.job4j.sqltracker2.Type.Item;

import ru.job4j.sqltracker2.SqlTracker2.Item;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

//import ru.job4j.sqltracker2.Type.Item;


public class Product implements Store {
    private Integer id;
    private String name;
    private String typeName;
    private Timestamp expireddate;
    private BigDecimal price;
    private SqlTracker2 sql;
    public static final String PRODUCT = "product";

    public Product(final Integer id, final String name, final String typeName, final Timestamp expireddate,
                   final BigDecimal price) {
        this.id = id;
        this.name = name;
        this.typeName = typeName;
        this.expireddate = expireddate;
        this.price = price;
    }

    /**
     * init.
     */
    @Override
    public void init() {
        sql = new SqlTracker2(PRODUCT);
        sql.init();
    }

    /**
     * close.
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        sql.close();
    }

    /**
     * add Item.
     *
     * @param item
     * @return
     */
    @Override
    public String add(final Item item) {
        //return sql.add(item);
        Item item
        return null;
    }

    /**
     * delete Item with id.
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(final String id) {
        return sql.delete(id);
    }

    /**
     * @return Item.
     */
    @Override
    public List<Item> findByName(final String key) {
        //return sql.findByName(key);
        return null;
    }

    /**
     * @return Item.
     */
    @Override
    public List<Item> findAll() {
        return null;
    }

    /**
     * replace по id.
     *
     * @param id   id
     * @param item item
     * @return true если успешно
     */
    @Override
    public boolean replace(final String id, final Item item) {
        return false;
    }

    /**
     * Find by id.
     * поиск записи по id в указанной таблице
     *
     * @param id the id
     * @return the item
     */
    @Override
    public Item findById(final String id) {
        return null;
    }

    /**
     * Выполнить как-то запрос/команду
     *
     * @param query query + строка для вывода в лог
     */
    @Override
    public void doQuery(String... query) {

    }

    /**
     * Display tables by name in List.
     *
     * @param list the List<String> of tables
     */
    @Override
    public void displayTables(List<String> list) {

    }

    /**
     * Set all tables.
     * Если имеются таблицы, вывести их содержимое
     *
     * @return the boolean
     */
    @Override
    public boolean isAnyTable() {
        return false;
    }

    /**
     * truncate Table.
     */
    @Override
    public void truncate() {

    }
}


class Type implements Store {
    private SqlTracker2 sql;
    public static final String TYPE = "type";
    private Integer id;
    private String name;

    Type(final Integer id, final String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void init() {
        sql = new SqlTracker2(TYPE);
        sql.init();
    }

    @Override
    public void close() {

    }

    /**
     * add Item.
     *
     * @param item
     * @return
     */
    @Override
    public String add(final Item item) {
        sql.add(Item item);
        return null;
    }

    /**
     * delete Item with id.
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(final String id) {
        return false;
    }

    /**
     * @return Item.
     */
    @Override
    public List<Item> findByName(final String key) {
        return null;
    }

    /**
     * @return Item.
     */
    @Override
    public List<Item> findAll() {
        return null;
    }

    /**
     * replace по id.
     *
     * @param id   id
     * @param item item
     * @return true если успешно
     */
    @Override
    public boolean replace(final String id, final Item item) {
        return false;
    }

    /**
     * Find by id.
     * поиск записи по id в указанной таблице
     *
     * @param id the id
     * @return the item
     */
    @Override
    public Item findById(final String id) {
        return null;
    }

    /**
     * Выполнить как-то запрос/команду
     *
     * @param query query + строка для вывода в лог
     */
    @Override
    public void doQuery(String... query) {

    }

    /**
     * Display tables by name in List.
     *
     * @param list the List<String> of tables
     */
    @Override
    public void displayTables(List<String> list) {

    }

    /**
     * Set all tables.
     * Если имеются таблицы, вывести их содержимое
     *
     * @return the boolean
     */
    @Override
    public boolean isAnyTable() {
        return false;
    }

    /**
     * truncate Table.
     */
    @Override
    public void truncate() {

    }
}