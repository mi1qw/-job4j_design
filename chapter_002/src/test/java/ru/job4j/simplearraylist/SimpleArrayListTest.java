package ru.job4j.simplearraylist;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
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

    @Test
    public void whenCreateFromSmallArray() {
        SimpleArrayList<String> str = new SimpleArrayList<>(1);
        str.add("one");
        str.add("two");
        assertThat(str.get(0), is("one"));
        assertThat(str.get(1), is("two"));
        assertThat(str.size(), is(2));
    }

    @Test
    public void whenCreateEmptyArray() {
        SimpleArrayList<String> str = new SimpleArrayList<>();
        str.add("one");
        assertThat(str.get(0), is("one"));
        assertThat(str.size(), is(1));
    }

    @Test
    public void whenCreateFromCollection() {
        List<String> strings = List.of("one", "two");
        SimpleArrayList<String> str = new SimpleArrayList<>(strings);
        assertThat(str.get(0), is("one"));
        assertThat(str.get(1), is("two"));
    }

    @Test
    public void whenCreateEmptyCollection() {
        List<String> strings = List.of();
        SimpleArrayList<String> str = new SimpleArrayList<>(strings);
        assertThat(str.size(), is(0));
    }

    @Test
    public void whenCreateZeroCapacitiy() {
        SimpleArrayList<String> str = new SimpleArrayList<>(0);
        assertThat(str.size(), is(0));
    }
}
