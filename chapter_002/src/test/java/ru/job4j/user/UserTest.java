package ru.job4j.user;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserTest {
    @Test
    public void createUser() {
        User user1 = new User("Ann",
                1,
                new GregorianCalendar(1995, Calendar.JANUARY, 25));
        User user2 = new User("Ann",
                1,
                new GregorianCalendar(1995, Calendar.JANUARY, 25));

        Map<User, String> map = new HashMap<>();
        map.put(user1, "one");
        map.put(user2, "two");
        System.out.println(map);
        System.out.println("user1 == user2 ? - " + (user1 == user2));
        System.out.println("user1 equals user2 ? - " + (user1.equals(user2)));
        assertThat(user1.equals(user2), is(true));
    }
}