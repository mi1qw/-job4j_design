package ru.job4j.sqltracker2;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Product implements Store {
    private Integer id;
    private String name;
    private Type typeid;
    private Timestamp expireddate;
    private BigDecimal price;

    private

    public Product(final Integer id, final String name, final Type typeid, final Timestamp expireddate,
                   final BigDecimal price) {
        this.id = id;
        this.name = name;
        this.typeid = typeid;
        this.expireddate = expireddate;
        this.price = price;
    }

    @Override
    public void init() {

    }

    @Override
    public void close() {

    }

    @Override
    public String add(final Item item) {
        return null;
    }

    @Override
    public boolean delete(final String id) {
        return false;
    }

    @Override
    public List<Item> findByName(final String key) {
        return null;
    }

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
     * Set all tables.
     * Если имеются таблицы, вывести их содержимое
     *
     * @return the boolean
     */
    @Override
    public boolean isAnyTable() {
        return false;
    }

    public static class Item {
        private Integer id;
        private String name;
        private Integer typeid;
        private Timestamp expireddate;
        private BigDecimal price = BigDecimal.ZERO;

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


public class Type implements Store {
    private Integer id;
    private String name;

    public Type(final Integer id, final String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void init() {

    }

    @Override
    public void close() {

    }

    @Override
    public String add(final Item item) {
        return null;
    }

    @Override
    public boolean delete(final String id) {
        return false;
    }

    @Override
    public List<Item> findByName(final String key) {
        return null;
    }

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
     * Set all tables.
     * Если имеются таблицы, вывести их содержимое
     *
     * @return the boolean
     */
    @Override
    public boolean isAnyTable() {
        return false;
    }

    public static class Item {
        private Integer id;
        private String name;

        public Item(final Integer id, final String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * Returns a string representation of the object.
         *
         * @return a string representation of the object.
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(id).append(" ").append(name);
            return sb.toString();
        }
    }
}