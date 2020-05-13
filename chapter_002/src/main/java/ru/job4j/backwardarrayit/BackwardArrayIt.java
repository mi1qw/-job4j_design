package ru.job4j.backwardarrayit;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BackwardArrayIt implements Iterator<Integer> {
    private final int[] data;
    private int point = 0;

    public BackwardArrayIt(final int[] data) {
        this.data = data;
    }

    /**
     * hasNext
     */
    @Override
    public boolean hasNext() {
        return point < data.length;
    }

    /**
     * next обратный, выводит с конца
     */
    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return data[data.length - 1 - point++];
    }
}

