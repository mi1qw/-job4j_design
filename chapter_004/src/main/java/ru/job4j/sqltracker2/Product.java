package ru.job4j.sqltracker2;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Product<T extends Product.Item> implements StorePT<T> {
    //private Integer id;
    //private String name;
    //private Type typeid;
    //private Timestamp expireddate;
    //private BigDecimal price;
    private SqlTracker2 sql;
    public static final String PRODUCT = "product";
    //
    //public Product(final Integer id, final String name, final Type typeid, final Timestamp expireddate,
    //               final BigDecimal price) {
    //    this.id = id;
    //    this.name = name;
    //    this.typeid = typeid;
    //    this.expireddate = expireddate;
    //    this.price = price;
    //
    //}

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
    public String add(final T item) {
        //return sql.add(item);
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
    public List<T> findByName(final String key) {
        //return sql.findByName(key);
        return null;
    }

    /**
     * @return Item.
     */
    @Override
    public List<T> findAll() {
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
    public boolean replace(final String id, final T item) {
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
    public T findById(final String id) {
        return null;
    }

    public static class Item {
        private Integer id;
        private String name;
        private Type.Item typeid;
        private Timestamp expireddate;
        private BigDecimal price = BigDecimal.ZERO;

        public Item(final Integer id, final String name, final Type.Item typeid,
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


class Type<T extends Type.Item> implements StorePT<T> {
    private SqlTracker2 sql;
    public static final String TYPE = "type";
    //private Integer id;
    //private String name;
    //
    //Type(final Integer id, final String name) {
    //    this.id = id;
    //    this.name = name;
    //}

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
    public String add(final T item) {
        sql.add(item);
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
    public List<T> findByName(final String key) {
        return null;
    }

    /**
     * @return Item.
     */
    @Override
    public List<T> findAll() {
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
    public boolean replace(final String id, final T item) {
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
    public T findById(final String id) {
        return null;
    }

    public static class Item {
        private Integer id;
        private String name;

        Item(final Integer id, final String name) {
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