package ru.job4j.simplearrayt;

import java.util.Iterator;

public class SimpleArray<T> implements Iterable<T> {
    private T[] array;
    private int size;
    private int index;

    public SimpleArray(final T[] array) {
        this.array = array;
        this.size = array.length;
        this.index = this.size - 1;
    }

    final @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int it = 0;

            @Override
            public boolean hasNext() {
                return it < size;
            }

            @Override
            public T next() {
                return array[it++];
            }
        };
    }

    final void add(final T model) {
        if (this.index + 1 == this.size) {
            Object[] t = new Object[this.size * 2];
            int n;
            for (n = 0; n < this.size; ++n) {
                t[n] = this.array[n];
            }
            this.array = (T[]) t;
            this.index = --n;
            this.size = this.array.length;
        }
        array[++this.index] = model;
    }

    final void set(final int index, final T model) {
        //index = Objects.checkIndex(0, this.size);
        array[index] = model;
    }

    final void remove(final int index) {
        for (int n = index; n < this.size - 1; ++n) {
            array[n] = array[n + 1];
        }
    }

    final T get(final int index) {
        return array[index];
    }

    final void display() {
        for (int n = 0; n < this.size; ++n) {
            System.out.print(this.array[n] + "  ");
        }
        System.out.println();
    }
}
