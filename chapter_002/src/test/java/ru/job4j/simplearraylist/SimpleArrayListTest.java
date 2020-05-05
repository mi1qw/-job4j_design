package ru.job4j.simplearraylist;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleArrayListTest {
    @Test
    public void whenAddThenGet() {
        SimpleArrayList<String> array = new SimpleArrayList<>();
        array.add("first");
        String rsl = array.get(0);
        assertThat(rsl, is("first"));
    }

    @Test
    public void whenAddThenIt() {
        SimpleArrayList<String> array = new SimpleArrayList<>();
        array.add("first");
        String rsl = array.iterator().next();
        assertThat(rsl, is("first"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenGetEmpty() {
        SimpleArrayList<String> array = new SimpleArrayList<>();
        array.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenGetOutBound() {
        SimpleArrayList<String> array = new SimpleArrayList<>();
        array.add("first");
        array.get(1);
    }

    @Test(expected = NoSuchElementException.class)
    public void whenGetEmptyFromIt() {
        SimpleArrayList<String> array = new SimpleArrayList<>();
        array.iterator().next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenCorruptedIt() {
        SimpleArrayList<String> array = new SimpleArrayList<>();
        array.add("first");
        Iterator<String> it = array.iterator();
        array.add("second");
        it.next();
    }
}