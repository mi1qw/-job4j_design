package ru.job4j.forwardlinklist;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ForwardLinkListTest {
    private ForwardLinkList<Integer> linked;

    @Before
    public void setUp() {
        linked = new ForwardLinkList<>();
    }

    @Test
    public void whenAddThenIter() {

        linked.add(1);
        linked.add(2);
        Iterator<Integer> it = linked.iterator();
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenEmptyIter() {
        Iterator<Integer> it = linked.iterator();
        it.next();
    }

    @Test
    public void whenAddAndRevertThenIter() {

        linked.add(1);
        linked.add(2);
        linked.revert();
        Iterator<Integer> it = linked.iterator();
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(1));
    }

    @Test
    public void whenAddAndRevertThenIter4() {

        linked.add(1);
        linked.add(2);
        linked.add(3);
        linked.add(4);
        linked.revert();

        Iterator<Integer> it = linked.iterator();
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(3));
    }
}