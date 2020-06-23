package ru.job4j.sqltracker;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

interface Statement {
    void statement(PreparedStatement st, Item item) throws SQLException;
}


/**
 * название таблицы
 * чтение значащие столбцы
 * запись значащие столбцы
 * функция записи
 * функция чтения
 * другие типичные функции
 * ( "name", "type_id", "expired_date", "price" )
 */
class Table {
    private String name;
    private String column;
    private Statement st;
    private String args;

    Table(final String name, final String column, final Statement st) {
        this.name = name;
        this.column = column;
        this.st = st;
        this.args = "?".concat(", ?".repeat(column.split(",").length - 1));
    }

    public String getColumn() {
        return column;
    }

    public String getArgs() {
        return args;
    }

    public String getName() {
        return name;
    }

    public Statement getSt() {
        return st;
    }
}


class Item {
    private Table table;
    private String name;
    private int typeid;
    private String expireddate = "";
    private BigDecimal price = new BigDecimal("0");

    Item(final Table table, final String name, final int typeid,
         final String expireddate, final BigDecimal price) {
        this.name = name;
        this.table = table;
        this.typeid = typeid;
        this.expireddate = expireddate;
        this.price = price;
    }

    Item(final Table table, final String name) {
        this.name = name;
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    public String getName() {
        return name;
    }

    public int getTypeid() {
        return typeid;
    }

    public String getExpireddate() {
        return expireddate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String[] getArrayItem() {
        return new String[]{name, String.valueOf(typeid), expireddate, price.toString()};
    }

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
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return builder.append("}").append(System.lineSeparator()).toString();
    }
}