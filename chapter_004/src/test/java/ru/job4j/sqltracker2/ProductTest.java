package ru.job4j.sqltracker2;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.Assert.assertTrue;

public class ProductTest {
    private static Type<Type.Item> type;
    private static Product<Product.Item> product;
    private static Type.Item itemType;
    private static Product.Item itemProduct;

    @BeforeClass
    public static void beforeClass() {
        itemType = new Type.Item(Integer.valueOf("1"), "Dessert Test");
        itemProduct = new Product.Item(
                Integer.valueOf("1"),
                "Ice Cream Test",
                itemType,
                Timestamp.valueOf("2020-6-28 00:00:00"),
                new BigDecimal("10.52"));
        type = new Type<>();
        type.init();
        //product = new Product<>();
        //product.init();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        type.close();
        //product.close();
    }

    @Test
    public void add() {
        type.add(itemType);
        assertTrue(true);
    }
}