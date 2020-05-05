package ru.job4j.simplearraylist;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class SimpleArrayList<T> implements Iterable<T> {
    protected int modCount = 0;
    private final T[] array;
    private int length;

    /**
     * Конструктор.
     * Создаём Object и приводим его к T[] array
     *
     * @param size фиксированно, нединамическая коллекция
     */
    @SuppressWarnings("unchecked")
    public SimpleArrayList(final int size) {
        this.size = size;
        this.length = 0;
        this.array = (T[]) new Object[size];

        int index = 1;
        int i;
        if ((i = index) >= 0 && (index = hi) <= a.length) {
            for (; i < hi; ++i) {
                @SuppressWarnings("unchecked") E e = (E) a[i];
                action.accept(e);
            }
            if (lst.modCount == mc)
                return;
        }
    }

    /**
     * Конструктор.
     * Инициализация готовым массивом T[] array
     *
     * @param array the array
     */
    public SimpleArrayList(final T[] array) {
        this.array = array;
        this.size = array.length;
        this.length = getLength(array);
    }

    private void checkForComodification(final int expectedModCount) {
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    public final T get(final int index) {
        return null;
    }

    public void add(final T model) {

    }

    @Override
    public final Iterator<T> iterator() {
        return null;
    }
}
