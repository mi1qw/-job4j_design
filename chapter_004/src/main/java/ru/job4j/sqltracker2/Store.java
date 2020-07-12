package ru.job4j.sqltracker2;

import ru.job4j.sqltracker2.SqlTracker2.Item;

import java.util.List;

public interface Store extends AutoCloseable {
    void init();

    void close() throws Exception;

    String add(Item item);

    boolean delete(String id);

    List<Item> findByName(String key);

    List<Item> findAll();

    /**
     * replace по id.
     *
     * @param id   id
     * @param item item
     * @return true если успешно
     */
    boolean replace(String id, Item item);

    /**
     * Find by id.
     * поиск записи по id в указанной таблице
     *
     * @param id the id
     * @return the item
     */
    Item findById(String id);

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
     * Set all tables.
     * Если имеются таблицы, вывести их содержимое
     *
     * @return the boolean
     */
    boolean isAnyTable();

    /**
     * truncate Table.
     */
    void truncate();
}
