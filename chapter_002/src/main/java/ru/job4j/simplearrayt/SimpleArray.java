package ru.job4j.simplearrayt;

import java.util.Iterator;
import java.util.Objects;

public class SimpleArray<T> implements Iterable<T> {
    private T[] array;
    private int size;
    private int length;

    /**
     * Конструктор.
     * Создаём Object и приводим его к T[] array
     *
     * @param size the size
     */
    @SuppressWarnings("unchecked")
    public SimpleArray(final int size) {
        this.size = size;
        this.length = -1;
        this.array = (T[]) new Object[size];
    }

    /**
     * @return Generic Iterator
     */
    final @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int it = 0;

            @Override
            public boolean hasNext() {
                while (it < size && array[it] == null) {
                    ++it;
                }
                return it < size;
            }

            @Override
            public T next() {
                return array[it++];
            }
        };
    }

    final int length() {
        return this.length + 1;
    }

    final int add(final T model) throws ArrayIndexOutOfBoundsException {
        for (int n = 0; n < this.size; ++n) {
            if (array[n] == null) {
                array[n] = model;
                ++this.length;
                return n;
            }
        }
        throw new ArrayIndexOutOfBoundsException();
        //array[++this.index] = model;
    }

    final void set(final int index, final T model) {
        Objects.checkIndex(index, this.size);
        array[index] = model;
        if (model == null) {
            --this.length;
        }
    }

    final void remove(final int index) throws ArrayIndexOutOfBoundsException {
        Objects.checkIndex(index, this.size);
        for (int n = index; n < this.size - 1; ++n) {
            array[n] = array[n + 1];
        }
        array[size - 1] = null;
        this.length--;
    }

    final T get(final int index) {
        Objects.checkIndex(index, this.size);
        return array[index];
    }

    final void display() {
        for (int n = 0; n < this.size; ++n) {
            System.out.print(this.array[n] + "  ");
        }
        System.out.println();
    }
}
