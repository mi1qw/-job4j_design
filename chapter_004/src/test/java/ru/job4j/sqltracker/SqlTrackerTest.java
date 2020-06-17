package ru.job4j.sqltracker;

import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//Sorts by method name
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SqlTrackerTest {
    private static SqlTracker sql;
    private static Table product;
    private static Table type;
    private static String idProduct;
    private static String idType;

    @BeforeClass
    public static void setUpDBase() throws SQLException {
        sql = new SqlTracker();
        sql.init();
        Food food = new Food();
        product = new Table(
                "product",
                "name, type_id, expired_date, price",
                sql::prodStatement
        );
        type = new Table(
                "type",
                "name",
                sql::typeStatement
        );

        Item item = new Item(type, "Десерт Тестовый");
        sql.addInID("1", item);
        item = new Item(
                product,
                "Мороженое Тестовое",
                1,
                "2020-6-28 00:00:00",
                new BigDecimal("10.52")
        );
        sql.addInID("1", item);
    }

    //@Before
    //public void setUp() {
    //    sql = new SqlTracker();
    //    sql.init();
    //}

    //@After
    @AfterClass
    public static void end() throws Exception {
        sql.close();
    }

    @Test
    public void a0findByName() throws SQLException {
        List<Item> list = sql.findByName("Мороженое Тестовое", product);
        assertThat(list.get(0).getName(), is("Мороженое Тестовое"));
    }

    @Test
    public void a1addItemProduct() throws Exception {
        Item item = new Item(
                product,
                "Мороженое Коровка",
                1,
                "2020-6-28 00:00:00",
                new BigDecimal("10.52")
        );
        idProduct = sql.add(item);
        List<Item> list = sql.findByName("Мороженое Коровка", product);
        assertThat(list.get(0).getName(), is("Мороженое Коровка"));
    }

    @Test
    public void a2addItemType() throws Exception {
        Item item = new Item(type, "Десерт");
        idType = sql.add(item);
        List<Item> list = sql.findByName("Десерт", type);
        assertThat(list.get(0).getName(), is("Десерт"));
    }

    @Test
    public void a3findById() {
        Item item = sql.findById(idProduct, product);
        assertThat(item.getName(), is("Мороженое Коровка"));
        item = sql.findById(idType, type);
        assertThat(item.getName(), is("Десерт"));
    }

    @Test
    public void a4replaceProduct() throws SQLException {
        Item item = sql.findById(idProduct, product);
        assertThat(item.getName(), is("Мороженое Коровка"));
        item = new Item(
                product,
                "Мороженое Каштан",
                1,
                "2020-6-28 00:00:00",
                new BigDecimal("15.25")
        );
        sql.replace(idProduct, item);
        item = sql.findById(idProduct, product);
        assertThat(item.getName(), is("Мороженое Каштан"));
    }

    @Test
    public void a5replaceType() throws SQLException {
        Item item = sql.findById(idType, type);
        assertThat(item.getName(), is("Десерт"));
        item = new Item(
                type,
                "Десерт Вкусный"
        );
        sql.replace(idType, item);
        item = sql.findById(idType, type);
        assertThat(item.getName(), is("Десерт Вкусный"));
    }

    @Test
    public void a6findAllProdukt() throws SQLException {
        List<Item> list = sql.findAll(product);
        System.out.println(list);
        BigDecimal sum = new BigDecimal("0");
        for (Item n : list) {
            sum = sum.add(n.getPrice());
        }
        assertThat(new BigDecimal("25.77"),
                Matchers.comparesEqualTo(sum));
    }

    @Test
    public void a7findAllType() throws SQLException {
        List<Item> list = sql.findAll(type);
        System.out.println(list);
        String name = "";
        for (Item n : list) {
            name = name.concat(n.getName());
        }
        assertEquals("Десерт ТестовыйДесерт Вкусный", name);
    }

    @Test
    public void a8delete() {
        assertTrue(sql.delete(idProduct, product));
        assertTrue(sql.delete(idType, type));
        assertTrue(sql.delete("1", product));
        assertTrue(sql.delete("1", type));
        int deleteAll = sql.findAll(product).size()
                + sql.findAll(type).size();
        assertEquals(0, deleteAll);
    }
}
