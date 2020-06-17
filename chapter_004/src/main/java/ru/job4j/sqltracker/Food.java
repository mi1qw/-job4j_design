package ru.job4j.sqltracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

interface Statement {
    void statement(PreparedStatement st, Item item) throws SQLException;
}


interface ItemProduct {
    Item getNew(Table table, String name, int typeId,
                String expiredDate, BigDecimal price);
}


interface ItemType {
    Item getNew(Table table, String name);
}


public class Food {
    private static final Logger LOG = LoggerFactory.getLogger(Food.class);
    //private List<Item> list;
    //private Store sql = new SqlTracker();

    public static void main(final String[] args) {
        new Food().go();
    }

    private void go() {

        //Table product = new Table("product",
        //        "name, type_id, expired_date, price",
        //        this::prodStatement);

        //Table type = new Table("type",
        //        "(name)",
        //        this::typeStatement);

        //"Мороженое Коровка",
        //        "1",
        //        "2020-6-28 00:00:00",
        //        "10.52"

        //"Сыр плавленный Дружба",
        //        "2",
        //        "2020-6-29 00:00:00",
        //        "99.99"

        //"Десерт"
        //"Сыр"

        //List<Table<?>> products = List.of(
        //        new Table<>("product", this::prodStatement,
        //                new Object[]{
        //                        "Мороженое Коровка",
        //                        1,
        //                        "2020-06-25 10:23:54",
        //                        10.52
        //                })
        //,
        //new Table("product", new Object[]{
        //        "Сыр плавленный Дружба",
        //        "2",
        //        "2020-07-15 10:23:54",
        //        "99.99"
        //}),
        //new Table("product", new Object[]{
        //        "Сыр плавленный Янтарь",
        //        "2",
        //        "2020-07-15 10:23:54",
        //        "80.10"
        //}),
        //new Table("product", new Object[]{
        //        "Молоко",
        //        "3",
        //        "2020-06-25 10:23:54",
        //        "21"
        //}),
        //new Table("product", new Object[]{
        //        "Яйца",
        //        "4",
        //        "2020-06-25 10:23:54",
        //        "17.50"
        //}),
        //new Table("product", new Object[]{
        //        "Мороженое геркулес",
        //        "1",
        //        "2020-07-19 10:23:54",
        //        "17.50"
        //}),
        //new Table("product", new Object[]{
        //        "Творог",
        //        "3",
        //        "2020-06-20 10:23:54",
        //        "21"
        //}),
        //new Table("product", new Object[]{
        //        "кефир",
        //        "3",
        //        "2020-06-19 10:23:54",
        //        "21"
        //})
        //);

        //Table product = new Table("product", new String[]{"Мороженое Коровка"});
        //Table product = new Table("product", new String[]{
        //        "Мороженое Коровка",
        //        "1",
        //        "2020-06-25 10:23:54",
        //        "10.52"
        //});

        //String url = "jdbc:postgresql://localhost:5432/product";
        //String username = "postgres";
        //String password = "postgres";
        //
        //try (Connection conn = DriverManager.getConnection(
        //        url, username, password)) {
        //
        //    String table = String.format("INSERT INTO %s %s VALUES(%s)",
        //            product.getName(), product.getColumn(), product.getArgs());
        //    try (PreparedStatement st = conn.prepareStatement(table)) {
        //
        //        product.getSt().statement(st, new String[]{
        //                "Мороженое Коровка",
        //                "1",
        //                "2020-6-28 00:00:00",
        //                "10.52"
        //        });
        //
        //        st.executeUpdate();
        //    }
        //} catch (Exception e) {
        //    LOG.error(e.getMessage(), e);
        //    e.printStackTrace();
        //}
    }
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
    private ItemProduct item;

    Table(final String name, final String column, final Statement st) {
        this.name = name;
        this.column = column;
        this.st = st;
        this.args = "?".concat(", ?".repeat(column.split(",").length - 1));
        String[] colums = column.split(",\\s*");
    }

    public ItemProduct getItem() {
        return item;
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

    //public Statement getSt() {
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

    //private String[] item;

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