package ru.job4j.sqltracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * The type Item.
 * Поля класса содержат конечные значения конкретной таблицы, готовые к
 * использованию в коде Java.
 * Так же содержит поле-ссылку на тип таблицы.
 * Имена полей должны совпадать сименами столбцов таблицы базы SQL
 * тип полей:строчный, чиловой(int, BigDecimal)...
 */
public class Item {
    private static final Logger LOG = LoggerFactory.getLogger(Item.class);
    /**
     * The Table - ссылка на конкретный тип таблицы {@link Table}.
     */
    private Table table;
    private String name;
    private int typeid;
    private String expireddate = "";
    private BigDecimal price = BigDecimal.ZERO;

    /**
     * Instantiates a new Item.
     * Конструктор для таблицы продуктов - product
     *
     * @param table       the table
     * @param name        the name
     * @param typeid      the typeid
     * @param expireddate the expireddate
     * @param price       the price
     */
    public Item(final Table table, final String name, final int typeid,
                final String expireddate, final BigDecimal price) {
        this.name = name;
        this.table = table;
        this.typeid = typeid;
        this.expireddate = expireddate;
        this.price = price;
    }

    /**
     * Instantiates a new Item.
     * Конструктор для таблицы товарных групп продуктов - type
     *
     * @param table the table
     * @param name  the name
     */
    public Item(final Table table, final String name) {
        this.name = name;
        this.table = table;
    }

    /**
     * Gets table.
     *
     * @return the table ссылка на тип таблицы {@link Table}
     */
    public Table getTable() {
        return table;
    }

    /**
     * Gets name.
     *
     * @return the стоблбец name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets typeid.
     *
     * @return the стоблбец typeid
     */
    public int getTypeid() {
        return typeid;
    }

    /**
     * Gets expireddate.
     *
     * @return the стоблбец expireddate
     */
    public String getExpireddate() {
        return expireddate;
    }

    /**
     * Gets price.
     *
     * @return the стоблбец price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Переопределяем toString.
     * Ипользую reflect API, чтобы вывести только значения столбцов
     * из поля column класса {@link Table}
     * по названию соответсвующей переменной, имена совпадают
     * Предварительно убираю "_" из названий столбцов, если они есть.
     *
     * @return String
     */
    @Override
    public String toString() {
        String columns = table.getColumn().replace("_", "");
        String[] names = columns.split(",\\s*");
        StringBuilder builder = new StringBuilder();
        builder.append("{ ");
        try {
            for (String n : names) {
                final Field field = this.getClass().getDeclaredField(n);
                builder.append(n).append("=").append(field.get(this)).append(" ");
            }
        } catch (ReflectiveOperationException e) {
            LOG.error(e.getMessage(), e);
        }
        return builder.append("}").append(System.lineSeparator()).toString();
    }
}