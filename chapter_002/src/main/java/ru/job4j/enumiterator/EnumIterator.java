package ru.job4j.enumiterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EnumIterator<T extends Number> implements Iterator<T> {
    private int index = -1;
    private int indexEnum;
    private final int size;
    private final T[] array;

    public EnumIterator(final T[] array) {
        this.array = array;
        this.size = array.length;
    }

    @Override
    public boolean hasNext() {
        boolean hasEnum = false;
        indexEnum = index;
        while (++indexEnum < size) {
            if (isEvenNumber(array[indexEnum])) {
                hasEnum = true;
                break;
            }
        }
        return hasEnum;
    }

    @Override
    public T next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in this array!");
        }
        index = indexEnum;
        return array[index];
    }

    private boolean isEvenNumber(final T number) {
        boolean res = false;
        if (number.longValue() % 2 == 0) {
            res = true;
        }
        return res;
    }

    public EnumIterator reset() {
        index = -1;
        indexEnum = -1;
        return this;
    }

    public void display() {
        while (hasNext()) {
            System.out.println(next());
        }
    }

    public static void main(final String[] args) {
        final Integer[] array = {1, 1, 2, 3, 4, 5, 6, 7, 3571, 8};
        EnumIterator<Integer> it = new EnumIterator<>(array);

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}


