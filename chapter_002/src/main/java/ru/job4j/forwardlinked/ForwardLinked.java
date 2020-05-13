package ru.job4j.forwardlinked;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ForwardLinked<T> implements Iterable<T> {
    private Node<T> head;
    private int size;

    /**
     * колличестов элементов Т
     *
     * @return size
     */
    public int size() {
        return this.size;
    }

    /**
     * Add.
     * {@code while (tail.next != null)} переставляет указатель в конец коллекции
     *
     * @param value the value
     */
    public void add(final T value) {
        Node<T> node = new Node<>(value, null);
        if (head == null) {
            head = node;
            ++size;
            return;
        }
        Node<T> tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = node;
        ++size;
    }

    /**
     * Delete first.
     */
    public void deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        head = head.next;
        --size;
    }

    /**
     * Delete last.
     *
     * @return the deleted node.value T
     */
    public T deleteLast() {
        T val;
        if (head == null) {
            throw new NoSuchElementException();
        }
        if (this.size != 1) {
            Node<T> tail = head;
            Node<T> prev = head;
            while (tail.next != null) {
                prev = tail;
                tail = tail.next;
            }
            prev.next = null;
            --size;
            return tail.value;
        } else {
            val = head.value;
            deleteFirst();
        }
        return val;
    }

    /**
     * @return Итератор.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
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
