package ru.job4j.sqltracker;

import java.sql.SQLException;
import java.util.List;

public interface Store extends AutoCloseable {
    void init();

    void close() throws Exception;

    String add(Item item) throws SQLException;

    boolean delete(String id, Table table);

    List<Item> findByName(String key, Table table) throws SQLException;

    List<Item> findAll(Table table);

    /**
     * Добавить строку в id указанной таблицы.
     *
     * @param id   id.
     * @param item item
     * @return idAdd aded id
     */
    String addInID(String id, Item item) throws SQLException;

    /**
     * replace по id в указанной таблице.
     *
     * @param id   id
     * @param item item
     * @return true если успешно
     */
    boolean replace(String id, Item item) throws SQLException;

    /**
     * Find by id item.
     * поиск записи по id в указанной таблице
     *
     * @param id    the id
     * @param table the table таблица поиска
     * @return the item
     */
    Item findById(String id, Table table);

    /**
     * Set all tables.
     * Если имеются таблицы, вывести их содержимое
     *
     * @return the boolean
     */
    boolean isAnyTable();

    /**
     * Выполнить как-то запрос/команду
     *
     * @param query query + строка для вывода в лог
     */
    void doQuery(String... query);

    /**
     * Display tables by name in List.
     *
     * @param list the List<String> of tables
     */
    void displayTables(List<String> list);

    /**
     * truncate указанную Table.
     *
     * @param table table
     */
    void truncate(Table table);
}
