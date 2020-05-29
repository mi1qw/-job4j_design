package ru.job4j.simplemap;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleMapTest<T> {
    private SimpleMap<Integer, String> map;
    private Iterator<SimpleMap.Node<Integer, String>> itMap;

    @Before
    public void setUp() {
        map = new SimpleMap<>();
        map.insert(1, "AB");
        map.insert(2, "ab");
        map.insert(3, "abc");
        itMap = map.iterator();
    }

    @Test
    public void whenDeleteShouldChangeSize() {
        assertThat(map.size(), is(3));
        assertTrue(map.delete(1));
        assertThat(map.size(), is(2));
    }

    @Test
    public void whenDeleteAnotherElementThenNothingChange() {
        assertFalse(map.delete(4));
        assertThat(map.size(), is(3));
    }

    @Test
    public void whenDeleteFromEmptyTable() {
        SimpleMap<Integer, Integer> b = new SimpleMap<>();
        assertFalse(b.delete(1));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenCorruptedItByDelete() {
        map.delete(1);
        itMap.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenCorruptedItByPut() {
        map.insert(4, "4");
        itMap.next();
    }

    @Test
    public void whenMultiCallhasNextThenTrue() {
        assertThat(itMap.hasNext(), is(true));
        assertThat(itMap.hasNext(), is(true));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNextMoreThanArray() {
        SimpleMap<Integer, Integer> a = new SimpleMap<>();
        a.iterator().next();
    }

    @Test
    public void iterateMap() {
        assertThat(itMap.next().getValue(), is("AB"));
        assertThat(itMap.next().getValue(), is("ab"));
        assertThat(itMap.next().getValue(), is("abc"));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNextMoreThanArrayForTesr() {
        itMap.next();
        itMap.next();
        itMap.next();
        itMap.next();
    }

    @Test
    public void createMap() {
        SimpleMap<Integer, Integer> a = new SimpleMap<>(2, 0.75f);
        a.insert(1, 1);
        assertThat(a.size(), is(1));
    }

    @Test
    public void whenGetWrongNodeThenNull() {
        assertNull(map.get(4));
    }

    @Test
    public void whenGetExistingNodeThenReturnValue() {
        assertThat(map.get(1), is("AB"));
    }

    @Test
    public void whenCreateMapWithTwoParametrs() {
        SimpleMap<Integer, Integer> aa = new SimpleMap<>(1, 0.75f);
        assertNull(aa.get(1));
    }

    @Test
    public void whenPutInBusyNodeThenNull() {
        assertTrue(map.insert(1, "q"));
    }

    @Test
    public void whenGetCollision() {
        assertFalse(map.insert(10, "10"));
    }

    //map.insert(10, "10");
    @Test
    public void whenMapShouldGrow() {
        for (int n = 4; n != 8; ++n) {
            map.insert(n, "a");
        }
        assertThat(map.size(), is(7));
    }

    @Test
    public void anotherTests() {
        SimpleMap<Integer, Integer> bb = new SimpleMap<>(1, 0.75f);
        assertNull(bb.get(null));
    }
}