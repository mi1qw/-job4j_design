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
        User user3 = new User("Ann1",
                1,
                new GregorianCalendar(1996, Calendar.FEBRUARY, 26));

        Map<User, String> map = new HashMap<>();
        map.put(user1, "one");
        map.put(user2, "two");
        System.out.println(map);
        System.out.println("user1 == user2 ? - " + (user1 == user2));
        System.out.println("user1 equals user2 ? - " + (user1.equals(user2)));
        System.out.println("user1 equals user3 ? - " + (user1.equals(user3)));
        System.out.println("user1 equals `user3` ? - " + (user1.equals("user3")));
        System.out.println("user1 equals user1 ? - " + (user1.equals(user1)));

        assertThat(map.get(user2), is("two"));
    }
}