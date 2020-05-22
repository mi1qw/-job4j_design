package ru.job4j.user;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class UserTest {
    @Test
    public void createUser() {
        User user1 = new User("Ann",
                1,
                new GregorianCalendar(1995, Calendar.JANUARY, 25));
        User user2 = new User("Ann",
                1,
                new GregorianCalendar(1995, Calendar.JANUARY, 25));

        Map<User, Object> map = new HashMap<>();
        map.put(user1, this);
        map.put(user2, this);
        System.out.println(map);
        System.out.println("user1 равен user2 ? - " + (user1 == user2));
        assertTrue(true);
    }
}