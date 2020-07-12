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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

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
        String id = type.add(itemType);
        assertThat(type.findById(id).getValuesItem().get(0), is("Dessert Test"));
        Item itemProduct = new Item(Integer.valueOf("1"), "Ice Cream Test", Integer.valueOf("1"),
                Timestamp.valueOf("2020-6-28 00:00:00"), new BigDecimal("10.52"));
        assertThat(product.findById(product.add(itemProduct)).getValuesItem().get(0),
                is("Ice Cream Test"));
    }

    @Test
    public void a2findAll() {
        Item item = new Item(null, "Not Ice Cream", Integer.valueOf("1"),
                Timestamp.valueOf("2020-6-28 00:00:00"), new BigDecimal("10.52"));
        product.add(item);
        List<Item> items = product.findAll();
        assertThat(items.get(0).getValuesItem().get(0).toString().concat(
                items.get(1).getValuesItem().get(0).toString()),
                is("Ice Cream TestNot Ice Cream"));
    }

    @Test
    public void a3findByName() {
        List<Item> items = product.findByName("Not Ice Cream");
        assertTrue(items.size() != 0);
        List<Item> itemstype = type.findByName("Dessert Test");
        assertTrue(itemstype.size() != 0);
    }

    @Test
    public void a4findById() {
        Item items = product.findById("1");
        assertThat(items.getValuesItem().get(0), is("Ice Cream Test"));
        assertThat(type.findById("1").getValuesItem().get(0), is("Dessert Test"));
        assertNull(type.findById("10"));
    }

    @Test
    public void a5replace() {
        product.isAnyTable();
        Item itemProduct = new Item(null, "something", Integer.valueOf("1"),
                Timestamp.valueOf("2020-6-28 00:00:00"), new BigDecimal("10.52"));
        Item itemType = new Item(Integer.valueOf("1"), "replace Test");
        assertTrue(product.replace("1", itemProduct));
        assertTrue(type.replace("1", itemType));
        assertThat(product.findByName("something").get(0).getValuesItem().get(0),
                is("something"));
        assertThat(type.findByName("replace Test").get(0).getValuesItem().get(0),
                is("replace Test"));
        product.isAnyTable();
    }

    @Test
    public void a6notExixtingIdreplace() {
        Item something = new Item(null, "something", Integer.valueOf("1"),
                Timestamp.valueOf("2020-6-28 00:00:00"), new BigDecimal("1"));
        assertFalse(product.replace("10", something));
    }

    @Test
    public void a7delete() {
        boolean items = product.delete("1");
        assertThat(product.findByName("something").size(), is(0));
        assertFalse(type.delete("10"));
    }
}