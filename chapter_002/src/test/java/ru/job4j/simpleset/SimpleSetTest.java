package ru.job4j.simpleset;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleSetTest {
    private SimpleSet<Integer> set;
    private SimpleSet<Integer> initSet;
    private Iterator<Integer> it;

    @Before
    public void setUp() {
        set = new SimpleSet<Integer>(5);
        set.add(0);
        set.add(1);
        set.add(2);
        set.add(3);
        it = set.iterator();

        Integer[] i = {99, 98, 1, 2, 97, 4, 96, 6, 7};
        initSet = new SimpleSet<Integer>(i);
    }

    @Test
    public void whenAddDuplicateValue() {
        assertThat(set.add(2), is(false));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addOutOfBounds() {
        initSet.add(20);
    }

    @Test
    public void whenMultiCallhasNextThenTrue() {
        it = set.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
    }

    @Test
    public void whenReadSequence() {
        it = set.iterator();
        assertThat(it.next(), is(0));
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNextFromEmpty() {
        new SimpleSet<Integer>().iterator().next();
    }

    @Test
    public void whenRemove() {
        assertThat(set.contains(2), is(true));
        set.remove(2);
        assertThat(set.contains(2), is(false));
        System.out.println(set);

        assertThat(initSet.contains(98), is(true));
        initSet.remove(98);
        assertThat(initSet.contains(98), is(false));
        System.out.println(initSet);
    }

    @Test
    public void removeOutOfBounds() {
        assertThat(set.remove(20), is(false));
    }

    @Test
    public void getSizethSet() {
        assertThat(initSet.size(), is(9));
        assertThat(set.size(), is(4));
    }
}