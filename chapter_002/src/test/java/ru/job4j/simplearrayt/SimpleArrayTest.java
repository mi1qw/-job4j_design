package ru.job4j.simplearrayt;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleArrayTest {
    private SimpleArray<Integer> array;
    private Iterator<Integer> it;

    @Before
    public void setUp() {
        array = new SimpleArray<>(5);
        array.add(0);
        array.add(1);
        array.add(2);
        array.add(3);
        it = array.iterator();
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

    @Test(expected = ArrayIndexOutOfBoundsException.class)
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
    }

    @Test
    public void remove() {
        System.out.println(array.length());
        array.remove(2);
        System.out.println(array.length());
        assertThat(array.get(2), is(3));
    }

    @Test
    public void remove1() {
        System.out.println(array.length());
        array.remove(2);
        System.out.println(array.length());
        assertThat(array.get(2), is(3));
    }

    @Test
    public void add() {
        System.out.println("size " + array.length());
        array.display();
        dispIterator(array);
        System.out.println();

        array.remove(1);
        array.display();
        System.out.println("size " + array.length());
        dispIterator(array);
        System.out.println();

        array.set(1, null);
        array.display();
        dispIterator(array);
        System.out.println("size " + array.length());
        System.out.println();

        array.add(555);
        array.display();
        dispIterator(array);
        System.out.println("size " + array.length());
        System.out.println();

        array.add(111);
        array.display();
        dispIterator(array);
        System.out.println("size " + array.length());
        System.out.println();

        array.add(222);
        array.display();
        dispIterator(array);
        System.out.println("size " + array.length());
        System.out.println();

        //array.add(222);
        //array.display();
        //System.out.println("size " + array.length());

        dispIterator(array);
    }

    @Test
    public void display() {
    }

    @Test
    public void length() {
    }

    final void dispIterator(final SimpleArray<Integer> array) {
        Iterator<Integer> it = array.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();
    }
}