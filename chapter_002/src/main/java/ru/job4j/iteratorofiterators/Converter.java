package ru.job4j.iteratorofiterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

class Converter {
    Iterator<Integer> convert(final Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            private Iterator<Integer> iterator = it.next();

            @Override
            public boolean hasNext() {
                while (!iterator.hasNext() && it.hasNext()) {
                    iterator = it.next();
                }
                return iterator.hasNext();
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    return iterator.next();
                }
            }
        };
    }
}
