package ru.job4j.sqltracker2;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.job4j.sqltracker2.SqlTracker2.Item;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SqlTracker2Test {
    private static SqlTracker2 type;
    private static SqlTracker2 product;

    @BeforeClass
    public static void beforeClass() throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        product = new SqlTracker2("product");
        product.init();
        type = new SqlTracker2("type");
        type.init();
        type.doQuery("SELECT SETVAL('type_id_seq', 1, false)");
        product.doQuery("SELECT SETVAL('product_id_seq', 1, false)");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        product.truncate();
        type.truncate();
        product.close();
        type.close();
    }

    @Test
    public void a1add() {
        Item itemType = new Item(Integer.valueOf("1"), "Dessert Test");
        type.add(itemType);
        Item itemProduct = new Item(Integer.valueOf("1"), "Ice Cream Test", Integer.valueOf("1"),
                Timestamp.valueOf("2020-6-28 00:00:00"), new BigDecimal("10.52"));
        product.add(itemProduct);
    }

    @Test
    public void a2findAll() {
        List<Item> items = product.findAll();
        System.out.println(items);
    }

    @Test
    public void a3findByName() {
        List<Item> items = product.findByName("Ice Cream Test");
        assertTrue(items.size() != 0);
    }

    @Test
    public void a4findById() {
        Item items = product.findById("1");
        System.out.println(items);
    }

    @Test
    public void a5replace() {
        Item itemProduct = new Item(null, "q3", Integer.valueOf("1"),
                Timestamp.valueOf("2020-6-28 00:00:00"), new BigDecimal("10.52"));
        boolean items = product.replace("1", itemProduct);
    }

    @Test
    public void a6delete() {
        product.isAnyTable();
        boolean items = product.delete("2");
        System.out.println(items);
        product.isAnyTable();
    }
}