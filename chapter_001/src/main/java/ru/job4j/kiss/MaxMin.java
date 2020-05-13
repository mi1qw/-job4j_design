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
    public final <T> T max(final List<T> value, final Comparator<T> comparator) {
        return maxOrMin(value, comparator);
    }

    public final <T> T min(final List<T> value, final Comparator<T> comparator) {
        return maxOrMin(value, comparator);
    }

    private <T> T maxOrMin(final List<T> value, final Comparator<T> comparator) {
        List<T> minmax = new ArrayList<>();
        minmax.add(value.get(0));

        Consumer<T> minMax = n -> {
            if (comparator.compare(n, minmax.get(0)) > 0) {
                minmax.set(0, n);
            }
        };
        value.spliterator().forEachRemaining(minMax::accept);
        return minmax.get(0);
    }
}
