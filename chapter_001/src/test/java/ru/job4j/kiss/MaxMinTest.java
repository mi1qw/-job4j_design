package ru.job4j.kiss;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MaxMinTest {
    private MaxMin maxMin;

    @Before
    public void setUp() {
        maxMin = new MaxMin();
    }

    @Test
    public void whenInteger() {
        Comparator<Integer> intCompMin = Comparator.reverseOrder();
        Comparator<Integer> intCompMax = Comparator.naturalOrder();
        ArrayList<Integer> kk = new ArrayList<>();
        kk.add(7);
        kk.add(2);
        kk.add(10);
        kk.add(5);
        kk.add(8);
        assertThat(maxMin.max(kk, intCompMax), is(10));
        assertThat(maxMin.min(kk, intCompMin), is(2));
    }

    @Test
    public void whenDouble() {
        Comparator<Double> compMin = Comparator.reverseOrder();
        Comparator<Double> compMax = Comparator.naturalOrder();
        ArrayList<Double> ll = new ArrayList<>();
        ll.add(7.0);
        ll.add(2.5);
        ll.add(10.1);
        ll.add(5.2);
        ll.add(8.3);
        assertThat(maxMin.max(ll, compMax), is(10.1));
        assertThat(maxMin.min(ll, compMin), is(2.5));
    }

    @Test
    public void whenString() {
        Comparator<String> strCompMin = Comparator.reverseOrder();
        Comparator<String> strCompMax = Comparator.naturalOrder();
        ArrayList<String> jj = new ArrayList<>();
        jj.add("bbb");
        jj.add("aaa");
        jj.add("bb");
        jj.add("c");
        jj.add("aa");
        assertThat(maxMin.max(jj, strCompMax), is("c"));
        assertThat(maxMin.min(jj, strCompMin), is("aa"));
    }
}
