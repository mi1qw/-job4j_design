package ru.job4j.iteratorofiterators;

import java.util.Arrays;
import java.util.Iterator;

public class IteratorOfIterators {
    public static void main(final String[] args) {
        new IteratorOfIterators().go();
    }

    final void go() {
        Iterator<Integer> it1 = Arrays.asList(1, 2, 3).iterator();
        Iterator<Integer> it2 = Arrays.asList(4, 5, 6).iterator();
        Iterator<Integer> it3 = Arrays.asList(7, 8, 9).iterator();
        Iterator<Iterator<Integer>> its = Arrays.asList(it1, it2, it3).iterator();

        Iterator<Integer> it = new Converter().convert(its);

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}

