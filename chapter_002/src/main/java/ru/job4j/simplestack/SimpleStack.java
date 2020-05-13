package ru.job4j.simplestack;

import ru.job4j.forwardlinked.ForwardLinked;

public class SimpleStack<T> {
    private ForwardLinked<T> linked;

    /**
     * Instantiates a пустой Simple stack.
     */
    public SimpleStack() {
        this.linked = new ForwardLinked<>();
    }

    /**
     * добавить элемент Т
     *
     * @param value
     */
    public void push(final T value) {
        linked.add(value);
    }

    /**
     * вернуть самый верхний элемент Т
     * удалив элемент из стека
     *
     * @return знаечение Т
     */
    public T pop() {
        return linked.deleteLast();
    }

    /**
     * Is empty стек.
     *
     * @return the boolean
     */
    public boolean isEmpty() {
        return linked.size() == 0;
    }
}
