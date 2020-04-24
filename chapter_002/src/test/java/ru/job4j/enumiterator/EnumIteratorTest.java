package ru.job4j.enumiterator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EnumIteratorTest {
    private final Integer[] arrayInt = {1, 1, 2, 3, 4, 5, 6, 7, 3571, 8};
    private final Short[] arrayShort = {-4000, -115, 200, 20000, 15000, 12111, 31333};
    private final Long[] arrayLong = {7863245990L, 2863245995L, 11453115050L, 2863245995L};

    @Test
    public void iterateArrayInt() {
        EnumIterator<Integer> it = new EnumIterator<>(arrayInt);
        ArrayList<Integer> actual = new ArrayList<>();
        while (it.hasNext()) {
            actual.add(it.next());
        }

        int[] expected = {2, 4, 6, 8};
        assertThat(actual.toArray(), is(expected));
    }

    @Test
    public void iterateArrayShort() {
        EnumIterator<Short> it = new EnumIterator<>(arrayShort);
        ArrayList<Short> actual = new ArrayList<>();
        while (it.hasNext()) {
            actual.add(it.next());
        }

        Short[] expected = {-4000, 200, 20000, 15000};
        assertThat(actual.toArray(), is(expected));
    }

    @Test
    public void iterateArrayLong() {
        EnumIterator<Long> it = new EnumIterator<>(arrayLong);
        ArrayList<Long> actual = new ArrayList<>();
        while (it.hasNext()) {
            actual.add(it.next());
        }

        Long[] expected = {7863245990L, 11453115050L};
        assertThat(actual.toArray(), is(expected));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNextMoreThanArray() {
        EnumIterator<Integer> it = new EnumIterator<>(arrayInt);
        it.display();
        it.next();
    }
}
