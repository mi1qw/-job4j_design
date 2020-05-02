package ru.job4j.simplearrayt;

import java.util.Iterator;

public class WrappArray {
    public static void main(final String[] args) {
        new WrappArray().go();
    }

    final void go() {
        SimpleArray<Integer> a = new SimpleArray<>(4);
        a.display();
        a.add(3);
        a.display();
        a.add(4);
        a.display();
        a.add(5);
        a.display();

        a.set(3, 555);
        a.display();

        System.out.println(a.get(1));
        System.out.println();

        a.remove(1);
        a.display();

        Iterator<Integer> it = a.iterator();
        System.out.println("Iterator");
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
