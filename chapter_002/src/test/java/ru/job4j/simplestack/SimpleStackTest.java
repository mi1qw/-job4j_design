package ru.job4j.simplestack;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleStackTest {
    private SimpleStack<Integer> stack;

    @Before
    public void setUp() {
        stack = new SimpleStack<>();
    }

    @Test
    public void whenPushThenPoll() {

        stack.push(1);
        assertThat(stack.pop(), is(1));
    }

    @Test
    public void whenPushPollThenPushPoll() {
        stack.push(1);
        stack.pop();
        stack.push(2);
        assertThat(stack.pop(), is(2));
    }

    @Test
    public void whenPushPushThenPollPoll() {
        stack.push(1);
        stack.push(2);
        stack.pop();
        assertThat(stack.pop(), is(1));
    }

    @Test
    public void whenStackIsEmpty() {
        assertThat(stack.isEmpty(), is(true));
        stack.push(15);
        assertThat(stack.isEmpty(), is(false));
    }
}