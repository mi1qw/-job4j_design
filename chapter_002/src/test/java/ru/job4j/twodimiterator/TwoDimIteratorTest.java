package ru.job4j.twodimiterator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TwoDimIteratorTest {
    private int[][] array = {{1}, {2, 3, 4, 5}, {6, 7}, {8, 9, 10, 11, 12, 13, 14}};

    @Test
    public void iterateArray() {
        TwoDimIterator it = new TwoDimIterator(array);
        ArrayList<Integer> actual = new ArrayList<Integer>();
        while (it.hasNext()) {
            actual.add(it.next());
        }
        actual.toArray();
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

        assertThat(actual.toArray(), is(expected));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNextMoreThanArray() {
        TwoDimIterator it = new TwoDimIterator(array);
        while (it.hasNext()) {
            it.next();
        }
        it.next();
    }
}



