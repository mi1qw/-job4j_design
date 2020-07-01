package ru.job4j.sqltracker;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The interface Statement.
 * ссылка на метод, в класса таблиц
 * {@link Table}, поле {@code Statement st}
 * подготавливает PreparedStatement значения в соответствии
 * с типом данных всех столбцов конкретной таблицы
 */
interface Statements {
    void statement(PreparedStatement st, Item item) throws SQLException;
}
