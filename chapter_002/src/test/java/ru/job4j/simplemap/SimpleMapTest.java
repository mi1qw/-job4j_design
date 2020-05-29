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
        map.put(1, "AB");
        map.put(2, "ab");
        map.put(3, "abc");
        itMap = map.iterator();
    }

    @Test
    public void tableSizeFor() {
        assertThat(SimpleMap.tableSizeFor(3), is(4));
        assertThat(SimpleMap.tableSizeFor(5), is(8));
    }

    @Test
    public void whenDeleteShouldChangeSize() {
        assertTrue(map.delete(1));
        assertThat(map.size(), is(2));
    }

    @Test
    public void whenDeleteAnotherElement() {
        assertFalse(map.delete(4));
        assertThat(map.size(), is(3));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenCorruptedIt() {
        map.put(10, "10");
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

    @Test
    public void createMap() {
        SimpleMap<Integer, Integer> a = new SimpleMap<>(1, 0.75f);
        a.put(1, 1);
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
    public void whenPutInBusyNodeThenNull() {
        //assertNull(map.put(1, "q"));
        assertFalse(map.put(1, "q"));
    }

    @Test
    public void whenMapShouldGrow() {
        for (int n = 4; n != 8; ++n) {
            map.put(n, "a");
        }
        assertThat(map.size(), is(7));
    }

    @Test
    public void anotherTests() {
        map.put(null, "1");
        map.get(null);
        assertTrue(true);
    }

    //@Test
    //public void whenBlankString() {
    //    //Map<String, String> list = new HashMap<>();
    //    //list.put("name", "Petr");
    //    //String string = "";
    //    //SimpleMap<Integer, Integer> a = new SimpleMap<>(1, 0.75f);
    //    //SimpleMap a = Mockito.spy(SimpleMap.class);
    //    SimpleMap a = Mockito.spy(new SimpleMap(1, 0.75f));
    //    for (int n = 0; n != 20; ++n) {
    //        //noinspection unchecked
    //        a.put(n, "mock");
    //    }
    //
    //    //a..simpleGenerator(string, list);
    //    //
    //    //Mockito.verify(simple).blankString();
    //}
}