package ru.job4j.simplearrayt;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class SimpleArrayTest {
    private SimpleArray<Integer> array;
    private SimpleArray<Integer> initarray;
    private Iterator<Integer> it;

    @Before
    public void setUp() {
        array = new SimpleArray<>(5);
        array.add(0);
        array.add(1);
        array.add(2);
        array.add(3);
        it = array.iterator();

        Integer[] i = {null, null, 1, 2, null, 4, null, 6, 7};
        initarray = new SimpleArray<>(i);
    }

    @Test
    public void whenMultiCallhasNextThenTrue() {
        it = array.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
    }

    @Test
    public void whenReadSequence() {
        it = array.iterator();
        assertThat(it.next(), is(0));
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNextFromEmpty() {
        Iterator<Integer> it = new SimpleArray<Integer>(0).iterator();
        it.next();
    }

    @Test
    public void get() {
        assertThat(array.get(0), is(0));
        assertThat(array.get(1), is(1));
    }

    @Test
    public void set() {
        array.set(0, 5);
        assertThat(array.get(0), is(5));
        initarray.set(2, 11);
        assertThat(initarray.get(2), is(11));
    }

    @Test
    public void remove() {
        array.remove(2);
        assertThat(array.get(2), is(3));

        System.out.println(initarray);
        initarray.dispIterator();
        System.out.print("remove - " + initarray.get(2));
        System.out.println("   length array " + initarray.length());
        initarray.remove(2);
        System.out.println(initarray);
        initarray.dispIterator();
        System.out.println("length array " + initarray.length());
        System.out.println();
        assertThat(initarray.get(2), is(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeOutOfBounds() {
        array.remove(20);
    }

    @Test
    public void add() {
        System.out.println("length array " + initarray.length());
        System.out.println(initarray);
        initarray.dispIterator();

        System.out.println("Add 555");
        initarray.add(555);
        System.out.println("length array " + initarray.length());
        System.out.println(initarray);
        initarray.dispIterator();
        assertThat(initarray.get(0), is(555));
        initarray.add(555);
        assertThat(initarray.get(1), is(555));
        initarray.add(555);
        assertThat(initarray.get(4), is(555));
    }

    @Test
    public void getLengthArray() {
        assertThat(initarray.length(), is(5));
        assertThat(array.length(), is(4));
    }

    @Test
    public void whenCalltoString() {
        SimpleArray<Integer> s = new SimpleArray<Integer>(3);
        s.add(1);
        s.add(2);
        assertEquals("1 2 null ", s.toString());
    }
}