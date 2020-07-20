package ru.job4j.sqltracker2;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.sqltracker2.SqlTracker2.Item;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SqlTracker2Test {
    private static SqlTracker2 type;
    private static SqlTracker2 product;
    private String fileDb = Objects.requireNonNull(SqlTracker2.class.getClassLoader().
            getResource("sqltracker.properties")).getFile();
    private static final Logger LOG = LoggerFactory.getLogger(SqlTracker2Test.class);

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

    private Connection init() {
        Connection cn = null;
        try (FileInputStream in = new FileInputStream(fileDb)) {
            Properties cfg = new Properties();
            cfg.load(in);
            Class.forName(cfg.getProperty("jdbc.driver"));
            cn = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password"));
        } catch (IOException | ClassNotFoundException | SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return cn;
    }
}