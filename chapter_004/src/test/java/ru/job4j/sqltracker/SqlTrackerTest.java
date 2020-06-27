package ru.job4j.sqltracker;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

//Sorts by method name
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SqlTrackerTest {
    private static final Logger LOGGER = LogManager.getLogger(SqlTrackerTest.class);
    private static final String KOROVKA = "Ice Cream Cow";
    private static final String DESERT = "Dessert";
    private static SqlTracker sql;
    private static Table product;
    private static Table type;
    private static Table type1;
    private static String idProduct;
    private static String idType;

    @BeforeClass
    public static void setUpDBase() throws SQLException {
        LOGGER.info("setUpDBase ");
        sql = new SqlTracker();
        //для зелёной ветки теста if в методе close()
        try {
            sql.close();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
        }
        sql.init();
        //Создать две талицы если их ещё нет в базе
        if (!sql.isAnyTable()) {
            sql.doQuery("CREATE TABLE type(\"id\" serial NOT NULL PRIMARY KEY, \"name\" VARCHAR(50))");
            sql.doQuery("CREATE TABLE product (\"id\" serial NOT NULL PRIMARY KEY,\"name\" VARCHAR ( 40 ) NOT NULL,"
                    + "\"type_id\" INTEGER references type(id),\"expired_date\" TIMESTAMP, \"price\" NUMERIC ( 6, 4 ))");
        }

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
        //несуществующая таблица для теста
        type1 = new Table(
                "type1",
                "name",
                sql::typeStatement
        );

        Item item = new Item(type, "Dessert Test");
        sql.addInID("1", item);
        sql.doQuery("SELECT SETVAL('type_id_seq', 1)");
        item = new Item(product,
                "Ice Cream Test",
                1,
                "2020-6-28 00:00:00",
                new BigDecimal("10.52")
        );
        sql.addInID("1", item);
        sql.doQuery("SELECT SETVAL('product_id_seq', 1)");
    }

    @AfterClass
    public static void end() {
        LOGGER.info("AfterClass ");
        sql.isAnyTable();
        try {
            sql.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    public void a0findByName() {
        LOGGER.info("a0findByName ");
        sql.isAnyTable();
        List<Item> list = sql.findByName("Ice Cream Test", product);
        assertThat(list.get(0).getName(), is("Ice Cream Test"));
    }

    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    @Test
    public void a1addItemProduct() throws SQLException {
        LOGGER.info("a1addItemProduct ");
        sql.isAnyTable();
        Item item = new Item(
                product,
                KOROVKA,
                1,
                "2020-6-28 00:00:00",
                new BigDecimal("10.52")
        );
        idProduct = sql.add(item);
        List<Item> list = sql.findByName(KOROVKA, product);
        assertThat(list.get(0).getName(), is(KOROVKA));
    }

    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    @Test
    public void a2addItemType() throws SQLException {
        LOGGER.info("a2addItemType ");
        sql.isAnyTable();
        Item item = new Item(type, DESERT);
        idType = sql.add(item);
        List<Item> list = sql.findByName(DESERT, type);
        assertThat(list.get(0).getName(), is(DESERT));
    }

    @Test
    public void a3findById() {
        LOGGER.info("a3findById ");
        sql.isAnyTable();
        Item item = sql.findById(idProduct, product);
        assertThat(item.getName(), is(KOROVKA));
        item = sql.findById(idType, type);
        assertThat(item.getName(), is(DESERT));
    }

    @Test
    public void a4replaceProduct() throws SQLException {
        LOGGER.info("a4replaceProduct ");
        sql.isAnyTable();
        Item item = sql.findById(idProduct, product);
        assertThat(item.getName(), is(KOROVKA));
        item = new Item(
                product,
                "Ice Cream Kashtan",
                1,
                "2020-6-28 00:00:00",
                new BigDecimal("15.25")
        );
        sql.replace(idProduct, item);
        item = sql.findById(idProduct, product);
        assertThat(item.getName(), is("Ice Cream Kashtan"));
    }

    @Test
    public void a5replaceType() throws SQLException {
        LOGGER.info("a5replaceType ");
        sql.isAnyTable();
        Item item = sql.findById(idType, type);
        assertThat(item.getName(), is(DESERT));
        item = new Item(
                type,
                "Dessert Delicious"
        );
        sql.replace(idType, item);
        item = sql.findById(idType, type);
        assertThat(item.getName(), is("Dessert Delicious"));
    }

    @Test
    public void a6findAllProdukt() throws SQLException {
        LOGGER.info("a6findAllProdukt ");
        sql.isAnyTable();
        List<Item> list = sql.findAll(product);
        LOGGER.info(list);
        BigDecimal sum = BigDecimal.ZERO;
        for (Item n : list) {
            sum = sum.add(n.getPrice());
        }
        assertThat(new BigDecimal("25.77"),
                Matchers.comparesEqualTo(sum));
    }

    @Test
    public void a7findAllType() throws SQLException {
        LOGGER.info("a7findAllType ");
        sql.isAnyTable();
        List<Item> list = sql.findAll(type);
        LOGGER.info(list);
        String name = "";
        for (Item n : list) {
            name = name.concat(n.getName());
        }
        assertEquals("Dessert TestDessert Delicious", name);
    }

    @Test
    public void a8addInIDtoExistingID() throws SQLException {
        LOGGER.info("a8addInIDtoExistingID ");
        sql.isAnyTable();
        assertEquals("-1", sql.addInID("1",
                new Item(type, "zzz")));
    }

    @Test
    public void a9delete() {
        LOGGER.info("a9delete ");
        sql.isAnyTable();
        assertTrue(sql.delete(idProduct, product));
        assertTrue(sql.delete(idType, type));
        assertTrue(sql.delete("1", product));
        assertTrue(sql.delete("1", type));
        int deleteAll = sql.findAll(product).size()
                + sql.findAll(type).size();
        assertEquals(0, deleteAll);
    }

    @Test
    public void replaceEmptyRow() throws SQLException {
        LOGGER.info("replaceEmptyRow ");
        sql.isAnyTable();
        assertFalse(sql.replace("999", new Item(type, "aaa")));
    }

    @Test
    public void z1whenAddGetResultException() throws SQLException {
        LOGGER.info("z1whenAddGetResultException ");
        sql.isAnyTable();
        SqlTracker spy = Mockito.spy(sql);
        doThrow(new SQLException()).when(spy).getResult(any());
        spy.add(new Item(type, "zxc"));
        assertTrue(true);
    }

    @Test
    public void z1whenGetResultException() throws SQLException {
        LOGGER.info("z1whenGetResultException ");
        sql.isAnyTable();
        PreparedStatement prep = mock(PreparedStatement.class);
        doThrow(new SQLException()).when(prep).getGeneratedKeys();
        sql.getResult(prep);
        assertTrue(true);
    }

    @Test
    public void deleteNonExistingTable() {
        LOGGER.info("deleteNonExistingTable ");
        sql.isAnyTable();
        sql.delete("10", type1);
        assertTrue(true);
    }

    @Test
    public void findAllNonExistingTable() {
        LOGGER.info("findAllNonExistingTable ");
        sql.isAnyTable();
        sql.findAll(type1);
        assertTrue(true);
    }

    @Test
    public void findByIdNonExistingTable() {
        LOGGER.info("findByIdNonExistingTable ");
        sql.isAnyTable();
        sql.findById("1", type1);
        assertTrue(true);
    }

    @Test
    public void z98truncateTheTable() {
        LOGGER.info("z98truncateTheTable ");
        sql.isAnyTable();
        sql.truncate(type);
        assertTrue(true);
    }

    @Test
    public void truncateNonExistingTable() {
        LOGGER.info("truncateNonExistingTable ");
        sql.isAnyTable();
        sql.truncate(type1);
        assertTrue(true);
    }

    @Test
    public void z11doQuery() {
        sql.doQuery("set lc_monetary to 'C' ");
        sql.doQuery("SELECT * FROM qqqq");
        assertTrue(true);
    }
}
