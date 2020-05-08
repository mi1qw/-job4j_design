package ru.job4j.simpllinkedlist;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimplLinkedList<T> implements Iterable<T> {
    private Node<T> last;
    private Node<T> first;
    private int size;
    private int modCount = 0;

    public SimplLinkedList() {
        this.last = null;
        this.first = null;
    }

    /**
     * Add.
     *
     * @param value the value
     */
    public void add(final T value) {
        Node<T> node = new Node<>(value, null);
        if (first == null) {
            this.first = node;
            this.last = node;
        } else {
            this.last.next = node;
            this.last = node;
        }
        ++size;
        ++modCount;
    }

    /**
     * Get t.
     *
     * @param index the index
     * @return the t
     */
    public T get(final int index) {
        Objects.checkIndex(index, size);
        int idex = index;

        Node<T> current = this.first;
        while (idex-- != 0) {
            current = current.next;
        }
        return current.value;
    }

    /**
     * @return the number of elements in this collection.
     */
    public int size() {
        return size;
    }

    private void checkForModification(final int expectedModCount) {
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int expectedModCount = modCount;
            private Node<T> current = first;

            /**
             * Returns {@code true} if the iteration has more elements.
             * (In other words, returns {@code true} if {@link #next} would
             * return an element rather than throwing an exception.)
             *
             * @return {@code true} if the iteration has more elements
             */
            @Override
            public boolean hasNext() {
                return current != null;
            }

            /**
             * Returns the next element in the iteration.
             *
             * @return the next element in the iteration
             * @throws NoSuchElementException if the iteration has no more elements
             */
            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                checkForModification(expectedModCount);
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }

    /**
     * Вариант с сохранением адрессов элементов должен отработать
     * быстрее вызовов get(index) для каждого элемента коллекции
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> current = this.first;
        while (current != null) {
            sb.append(current.value).append(" ");
            current = current.next;
        }
        return sb.toString();
    }
}
