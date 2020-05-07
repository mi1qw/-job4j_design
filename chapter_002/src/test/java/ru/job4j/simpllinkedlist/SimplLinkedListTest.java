package ru.job4j.simpllinkedlist;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimplLinkedListTest {
    private SimplLinkedList<Integer> simplLinkedList;
    private SimplLinkedList<String> simplLinkedListIt;

    @Before
    public void setUp() {
        simplLinkedList = new SimplLinkedList<>();
        simplLinkedListIt = new SimplLinkedList<>();
        simplLinkedListIt.add("A");
        simplLinkedListIt.add("B");
        simplLinkedListIt.add("C");
        simplLinkedListIt.add("D");
    }

    @Test
    public void add() {
        simplLinkedList.add(1);
        simplLinkedList.add(2);
        simplLinkedList.add(3);
        assertThat(simplLinkedList.size(), is(3));
        System.out.println(simplLinkedList);
    }

    @Test
    public void get() {
        simplLinkedList.add(11);
        simplLinkedList.add(5);
        simplLinkedList.add(77);
        System.out.println(simplLinkedList);
        assertThat(simplLinkedList.get(0), is(11));
        assertThat(simplLinkedList.get(1), is(5));
        assertThat(simplLinkedList.get(2), is(77));
    }

    @Test
    public void whenMultiCallhasNextThenTrue() {
        Iterator<String> it = simplLinkedListIt.iterator();

        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("A"));
        assertThat(it.next(), is("B"));
        assertThat(it.next(), is("C"));
        assertThat(it.next(), is("D"));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNextFromEmpty() {
        SimplLinkedList<Integer> simplLinkedList = new SimplLinkedList<>();
        Iterator<Integer> it = simplLinkedList.iterator();
        it.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenCorruptedIt() {
        Iterator<String> it = simplLinkedListIt.iterator();
        simplLinkedListIt.add("second");
        it.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenGetEmptyFromIt() {
        simplLinkedList.add(1);
        Iterator<Integer> it = simplLinkedList.iterator();
        it.next();
        it.next();
    }
}
