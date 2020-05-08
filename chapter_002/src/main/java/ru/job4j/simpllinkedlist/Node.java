package ru.job4j.simpllinkedlist;

public class Node<T> {
    public T value;
    public Node<T> next;

    public Node(final T value, final Node<T> next) {
        this.value = value;
        this.next = next;
    }
}
