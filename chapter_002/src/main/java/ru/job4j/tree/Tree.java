package ru.job4j.tree;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

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
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.getValue().equals(value)) {
                rsl = Optional.of(el);
                break;
            }
            data.addAll(el.getChildren());
        }
        return rsl;
    }

    @Override
    public boolean isBinary() {
        Queue<Node<E>> tree = new LinkedList<>();
        tree.offer(this.root);
        while (!tree.isEmpty()) {
            Node<E> pElem = tree.poll();
            if (pElem.getChildren().size() > 2) {
                return false;
            }
            tree.addAll(pElem.getChildren());
        }
        return true;
    }
}



