package ru.job4j.forwardlinklist;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ForwardLinkList<T> implements Iterable<T> {
    private Node<T> head;

    /**
     * Добавить.
     *
     * @param value значение
     */
    public void add(final T value) {
        Node<T> node = new Node<>(value, null);
        if (head == null) {
            head = node;
            return;
        }
        Node<T> tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = node;
    }

    /**
     * Revert.
     */
    public void revert() {
        Node<T> prev = null;
        Node<T> current = head;
        Node<T> tail;
        while (current != null) {
            tail = current.next;
            current.next = prev;

            prev = current;
            current = tail;
        }
        head = prev;
    }

    /**
     * Итератор.
     *
     * @return итератор
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> node = head;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T value = node.value;
                node = node.next;
                return value;
            }
        };
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;

        Node(final T value, final Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }
}
