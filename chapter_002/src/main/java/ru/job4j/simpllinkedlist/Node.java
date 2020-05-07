package ru.job4j.simpllinkedlist;

public class Node<T> {
    public T value;
    public Node<T> previos;

    public Node(final T value, final Node<T> prev) {
        this.value = value;
        this.previos = prev;
    }
}
