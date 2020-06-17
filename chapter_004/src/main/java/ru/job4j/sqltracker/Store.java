package ru.job4j.sqltracker;

import java.sql.SQLException;
import java.util.List;

public interface Store extends AutoCloseable {
    void init();

    void close() throws Exception;

    String add(Item item) throws SQLException;

    String addInID(String id, Item item) throws SQLException;

    boolean delete(String id, Table table);

    boolean replace(String id, Item item) throws SQLException;

    List<Item> findByName(String key, Table table) throws SQLException;

    List<Item> findAll(Table table);

    Item findById(String id, Table table);
}
