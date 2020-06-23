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
    private static final String KOROVKA = "Мороженое Коровка";
    private static final String DESERT = "Десерт";
    private static SqlTracker sql;
    private static Table product;
    private static Table type;
    private static Table type1;
    private static String idProduct;
    private static String idType;

    @BeforeClass
    public static void setUpDBase() throws SQLException {
        sql = new SqlTracker();
        sql.setCn(null);
        try {
            sql.close();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
        }
        sql.init();
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
        //не существующая таблица
        type1 = new Table(
                "type1",
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
        sql.truncate(type);
        try {
            sql.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        sql.close();
    }

    @Test
    public void a0findByName() throws SQLException {
        List<Item> list = sql.findByName("Мороженое Тестовое", product);
        assertThat(list.get(0).getName(), is("Мороженое Тестовое"));
    }

    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    @Test
    public void a1addItemProduct() throws SQLException {
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
        Item item = new Item(type, DESERT);
        idType = sql.add(item);
        List<Item> list = sql.findByName(DESERT, type);
        assertThat(list.get(0).getName(), is(DESERT));
    }

    @Test
    public void a3findById() {
        Item item = sql.findById(idProduct, product);
        assertThat(item.getName(), is(KOROVKA));
        item = sql.findById(idType, type);
        assertThat(item.getName(), is(DESERT));
    }

    @Test
    public void a4replaceProduct() throws SQLException {
        Item item = sql.findById(idProduct, product);
        assertThat(item.getName(), is(KOROVKA));
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
        assertThat(item.getName(), is(DESERT));
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
        List<Item> list = sql.findAll(type);
        LOGGER.info(list);
        String name = "";
        for (Item n : list) {
            name = name.concat(n.getName());
        }
        assertEquals("Десерт ТестовыйДесерт Вкусный", name);
    }

    @Test
    public void a8addInIDtoExistingID() throws SQLException {
        assertEquals("-1", sql.addInID("1",
                new Item(type, "zzz")));
    }

    @Test
    public void a9delete() {
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
        assertFalse(sql.replace("999", new Item(type, "aaa")));
    }

    @Test(expected = SQLException.class)
    public void z1whenException() throws SQLException {
        SqlTracker spy = Mockito.spy(sql);
        doThrow(new SQLException()).when(spy).getResult(any());
        spy.add(new Item(type, "zzz"));
    }

    @Test(expected = SQLException.class)
    public void z1() throws SQLException {
        PreparedStatement prep = mock(PreparedStatement.class);
        doThrow(new SQLException()).when(prep).getGeneratedKeys();
        sql.getResult(prep);
    }

    @Test
    public void deleteNonExistingTable() {
        sql.delete("10", type1);
        assertTrue(true);
    }

    @Test
    public void truncateNonExistingTable() {
        sql.truncate(type1);
        assertTrue(true);
    }

    @Test
    public void findAllNonExistingTable() {
        sql.findAll(type1);
        assertTrue(true);
    }

    @Test
    public void findByIdNonExistingTable() {
        sql.findById("1", type1);
        assertTrue(true);
    }

    @Test
    public void z98truncateTheTable() {
        sql.truncate(type);
        assertTrue(true);
    }

    //@PrepareForTest(DriverManager.class)
    //@Test(expected = Exception.class)
    //public void init() throws IOException, ClassNotFoundException, SQLException {
    //    SqlTracker spy = Mockito.spy(sql);
    //    //doThrow(new SQLException()).when(spy).
    //    //DriverManager conn = mock(DriverManager.class);
    //    //Properties conn = mock(Properties.class);
    //    //doThrow(new Exception()).when(spy).init();
    //    //InputStream conn = mock(InputStream.class);
    //    //doThrow(new Exception()).when(conn).available();
    //    //when(conn.available()).thenThrow(new SQLException());
    //
    //    //Class conn = mock(Class.class);
    //    //doThrow(new Exception()).when(conn).forName(anyString());
    //
    //    Connection connection = mock(Connection.class);
    //
    //    mockStatic(DriverManager.class);
    //
    //    //PowerMockito.when(DriverManager.getConnection(
    //    //        anyString(), anyString(), anyString())).
    //    //        //thenThrow(new Exception());
    //    //        thenReturn(any(Connection.class));
    //
    //    PowerMockito.doThrow(new Exception()).when(DriverManager.class);
    //
    //    //doThrow(new Exception()).
    //    spy.init();
    //}
}

