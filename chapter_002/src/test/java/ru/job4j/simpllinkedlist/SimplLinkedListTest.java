package ru.job4j.simpllinkedlist;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimplLinkedListTest {
    private SimplLinkedList<Integer> list;
    private SimplLinkedList<String> listIt;

    @Before
    public void setUp() {
        list = new SimplLinkedList<>();
        listIt = new SimplLinkedList<>();
        listIt.add("A");
        listIt.add("B");
        listIt.add("C");
        listIt.add("D");
    }

    @Test
    public void add() {
        list.add(1);
        list.add(2);
        list.add(3);
        assertThat(list.size(), is(3));
        System.out.println(list);
    }

    @Test
    public void get() {
        list.add(11);
        list.add(5);
        list.add(77);
        System.out.println(list);
        assertThat(list.get(0), is(11));
        assertThat(list.get(1), is(5));
        assertThat(list.get(2), is(77));
    }

    @Test
    public void whenMultiCallhasNextThenTrue() {
        Iterator<String> it = listIt.iterator();

        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        //System.out.println(it.next());
        //System.out.println(it.next());
        //System.out.println(it.next());
        //System.out.println(it.next());
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
        Iterator<String> it = listIt.iterator();
        listIt.add("second");
        it.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenGetEmptyFromIt() {
        list.add(1);
        Iterator<Integer> it = list.iterator();
        it.next();
        it.next();
    }
}
