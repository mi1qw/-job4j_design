package ru.job4j.simpllinkedlist;

import java.util.*;

public class SimplLinkedList<T> implements Iterable<T> {
    private Node<T> last;
    private int size;
    private int modCount = 0;

    public SimplLinkedList() {
        this.last = null;
    }

    /**
     * Add.
     *
     * @param value the value
     */
    public void add(final T value) {
        ++modCount;
        ++size;
        this.last = new Node<>(value, last);
    }

    /**
     * Get t.
     *
     * @param index the index
     * @return the t
     */
    public T get(final int index) {
        Objects.checkIndex(index, size);
        int idex = this.size - index - 1;

        Node<T> current = this.last;
        while (idex-- != 0) {
            current = current.previos;
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
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Object[] array = new Object[size];
        Node<T> current = last;
        int n = size;
        while (current.previos != null) {
            array[--n] = current;
            current = current.previos;
        }
        array[--n] = current;
        return new Iterator<T>() {
            private int expectedModCount = modCount;
            private int n = 0;

            /**
             * Returns {@code true} if the iteration has more elements.
             * (In other words, returns {@code true} if {@link #next} would
             * return an element rather than throwing an exception.)
             *
             * @return {@code true} if the iteration has more elements
             */
            @Override
            public boolean hasNext() {
                return n < size;
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
                Node<T> m = (Node<T>) array[n++];
                return m.value;
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
        ArrayDeque<Node<T>> stack = new ArrayDeque<>();
        Node<T> current = this.last;
        while (current.previos != null) {
            stack.push(current);
            current = current.previos;
        }
        stack.push(current);

        stack.stream().forEach(n -> sb.append(n.value).append(" "));
        return sb.toString();
    }
}
