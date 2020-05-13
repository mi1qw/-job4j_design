package ru.job4j.forwardlinked;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ForwardLinkedTest {
    private ForwardLinked<Integer> linked;

    @Before
    public void setUp() {
        linked = new ForwardLinked<>();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenDeleteFirst() {
        linked.add(1);
        linked.deleteFirst();
        linked.iterator().next();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenDeleteEmptyLinked() {
        linked.deleteFirst();
    }

    @Test
    public void whenMultiDelete() {
        linked.add(1);
        linked.add(2);
        linked.deleteFirst();
        Iterator<Integer> it = linked.iterator();
        assertThat(it.next(), is(2));
    }

    @Test
    public void whenDeleteLast() {
        linked.add(10);
        linked.add(20);
        linked.add(25);
        linked.deleteLast();
        Iterator<Integer> it = linked.iterator();
        assertThat(it.next(), is(10));
        assertThat(it.next(), is(20));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenDeleteLastSize1() {
        linked.add(11);
        linked.deleteLast();
        Iterator<Integer> it = linked.iterator();
        it.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenDeleteLastSize0() {
        linked.deleteLast();
    }

    @Test
    public void getSize() {
        linked.add(111);
        linked.add(112);
        assertThat(linked.size(), is(2));
    }
}
