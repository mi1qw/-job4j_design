package ru.job4j.iteratorofiterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

class Converter {
    //Iterator<Iterator<Integer>> it;

    Iterator<Integer> convert(final Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            private Iterator<Integer> iterator;

            {
                this.iterator = it.next();
            }

            @Override
            public boolean hasNext() {
                boolean res = false;
                if (iterator.hasNext()) {
                    res = true;
                } else {
                    while (it.hasNext() && !res) {
                        iterator = it.next();
                        res = iterator.hasNext();
                    }
                }
                return res;
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
