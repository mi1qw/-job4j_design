package ru.job4j.sqltracker2;

import java.util.List;

public interface StorePT<T> extends AutoCloseable {
    void init();

    void close() throws Exception;

    String add(T item);

    boolean delete(String id);

    List<T> findByName(String key);

    List<T> findAll();

    /**
     * replace по id.
     *
     * @param id   id
     * @param item item
     * @return true если успешно
     */
    boolean replace(String id, T item);

    /**
     * Find by id.
     * поиск записи по id в указанной таблице
     *
     * @param id the id
     * @return the item
     */
    T findById(String id);
}
