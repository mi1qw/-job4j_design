/**
 * These classes contain the ...... чё-то там
 *
 * @author somebody
 * @version 1.0
 * @since 1.0
 */
package ru.job4j.kiss;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class MaxMin {
    public <T> T max(List<T> value, Comparator<T> comparator) {
        return maxOrMin(value, comparator);
    }

    public <T> T min(List<T> value, Comparator<T> comparator) {
        return maxOrMin(value, comparator);
    }

    public <T> T maxOrMin(List<T> value, Comparator<T> comparator) {
        List<T> minmax = new ArrayList<>();
        minmax.add(value.get(0));

        Consumer<T> minMax = (n) -> {
            if (comparator.compare(n, minmax.get(0)) > 0) {
                minmax.set(0, n);
            }
        };
        value.spliterator().forEachRemaining((n) -> minMax.accept(n));
        return minmax.get(0);
    }

    public void go() {
        // Перебор коллекций с элементами разного типа
        // и соттветствущий компаратор для поиска MAX или MIN

        Comparator compMin = Comparator.naturalOrder().reversed();
        Comparator compMax = Comparator.naturalOrder();

        ArrayList<Double> ll = new ArrayList<>();
        ll.add(7.0);
        ll.add(2.5);
        ll.add(10.1);
        ll.add(5.2);
        ll.add(8.3);
        System.out.println(ll);
        System.out.println("MAX = " + max(ll, compMax));
        System.out.println("MIN = " + min(ll, compMin));
        System.out.println();

        ArrayList<Integer> kk = new ArrayList<>();
        kk.add(7);
        kk.add(2);
        kk.add(10);
        kk.add(5);
        kk.add(8);
        System.out.println(kk);
        System.out.println("MAX = " + max(kk, compMax));
        System.out.println("MIN = " + min(kk, compMin));
        System.out.println();

        ArrayList<String> jj = new ArrayList<>();
        jj.add("bbb");
        jj.add("aaa");
        jj.add("bb");
        jj.add("c");
        jj.add("aa");
        System.out.println(jj);
        System.out.println("MAX = " + max(jj, compMax));
        System.out.println("MIN = " + min(jj, compMin));
    }

    public static void main(String[] args) {
        new MaxMin().go();
    }
}
