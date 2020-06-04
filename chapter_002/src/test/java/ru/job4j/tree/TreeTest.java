package ru.job4j.tree;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TreeTest {
    @Test
    public void when6ElFindLastThen6() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(
                tree.findBy(6).isPresent(),
                is(true)
        );
    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
    }

    @Test
    public void whenAddExistingElementThenFalse() {
        Tree<Integer> tree1 = new Tree<>(1);
        assertThat(
                tree1.add(1, 2),
                is(true)
        );
        assertThat(
                tree1.add(1, 2),
                is(false)
        );
    }

    @Test
    public void whenChildMore2ThenNotBinaryTree() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 20);
        tree.add(1, 30);
        tree.add(1, 40);
        tree.add(40, 50);
        assertThat(
                tree.isBinary(),
                is(false)
        );
    }

    @Test
    public void whenChild2ThenItIsBinaryTree() {
        Tree<Integer> tree = new Tree<>(11);
        tree.add(11, 21);
        tree.add(11, 41);
        tree.add(41, 51);
        tree.add(41, 61);
        assertThat(
                tree.isBinary(),
                is(true)
        );
    }
}