package ru.job4j.user;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserTest {
    static final String LN = System.lineSeparator();
    private String field1 = "1";
    private String field2 = "2";

    /**
     * @param num
     * @return
     */
    public int hashCode(final String num) {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(17, 37).
                append(num).
                toHashCode();
    }

    //public int getField1() {
    //    return field1;
    //}
    //
    //public int getField2() {
    //    return field1;
    //}

    @Test
    public void createUser() {

        //for (int n = -500; n < 500; n += 100) {
        //    //System.out.println(n + " " + Integer.valueOf(n).hashCode() % 1001);
        //    System.out.println(n + " " + (Integer.valueOf(n).hashCode() & 999));
        //}

        //hash = hash * 31 + Integer.hashCode(getField1());
        int a = 0;

        int length = 1 << 3;
        int index;

        int hash = 17;
        hash = hashCode(field1);
        //hash = hash * 31 + hashCode(field2);
        hash = ((hash << 5) - hash) + hashCode(field2);

        index = hash & (length - 1);
        //index = hash % length;

        field1.hashCode();
        System.out.println(hashCode("A"));
        System.out.println("hash");
        int length0 = 1 << 5;
        for (int n = 0; n < length0; ++n) {
            System.out.println(n + " " + (Integer.valueOf(n).hashCode() & (length0 - 1)));
        }

        System.out.println();
        int i = 232;
        //число 232 в двоичной системе
        System.out.println(Integer.toBinaryString(i));
        //обратыный перевод из двоичной системы в десятичную
        System.out.println(Integer.parseInt("11101000", 2));
        System.out.println();

        //усекает диапазон
        //int h = 0b1011;
        //System.out.println(h);
        //int length = 0b1001;
        //System.out.println(
        //        h & (length));
        //System.out.println(
        //        h & (length - 1));

        System.out.println();

        //вычитание
        int g = 0b1111;
        System.out.println(g);
        int length1 = 0b0100;
        System.out.println(
                g ^ length1);

        System.out.println();

        //(i << 5) - i
        System.out.println(31 * 3);
        System.out.println((3 << 5) - 3);

        System.out.println();
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