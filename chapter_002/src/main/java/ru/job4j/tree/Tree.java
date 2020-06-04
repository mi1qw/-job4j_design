package ru.job4j.tree;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Predicate;

class Tree<E> implements SimpleTree<E> {
    private final Node<E> root;

    Tree(final E root) {
        this.root = new Node<>(root);
    }

    @Override
    public boolean add(final E parent, final E child) {
        Optional<Node<E>> parentNode = findBy(parent);
        Optional<Node<E>> childNode = findBy(child);
        if (childNode.isEmpty() && parentNode.isPresent()) {
            Node<E> node = parentNode.get();
            node.getChildren().add(new Node<>(child));
            return true;
        }
        return false;
    }

    @Override
    public Optional<Node<E>> findBy(final E value) {
        return findNode(n -> n.getValue().equals(value));
    }

    @Override
    public boolean isBinary() {
        return findNode(n -> n.getChildren().size() > 2).isEmpty();
    }

    private Optional<Node<E>> findNode(final Predicate<Node<E>> p) {
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (p.test(el)) {
                return Optional.of(el);
            }
            data.addAll(el.getChildren());
        }
        return Optional.empty();
    }
}



