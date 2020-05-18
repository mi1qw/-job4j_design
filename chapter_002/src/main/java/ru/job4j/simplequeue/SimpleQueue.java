package ru.job4j.simplequeue;

import ru.job4j.simplestack.SimpleStack;

public class SimpleQueue<T> {
    private final SimpleStack<T> in;
    private final SimpleStack<T> out;

    /**
     * Instantiates a пустой Simple stack.
     */
    public SimpleQueue() {
        this.in = new SimpleStack<>();
        this.out = new SimpleStack<>();
    }

    /**
     * помещает значение в конец.
     *
     * @param value значение
     */
    public void push(final T value) {
        in.push(value);
    }

    /**
     * должен возвращать первое значение и удалять его из коллекции.
     *
     * @return значение элемента Т
     */
    public T poll() {
        while (!in.isEmpty()) {
            out.push(in.pop());
        }
        T val = out.pop();
        while (!out.isEmpty()) {
            in.push(out.pop());
        }
        return val;
    }
}