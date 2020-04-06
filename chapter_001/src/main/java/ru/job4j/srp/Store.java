package ru.job4j.srp;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface Store {
    List<Employer> findBy(Predicate<Employer> filter, Comparator<Employer> comparator);
}


interface N {
    public static final String N = System.lineSeparator();

    public default String n() {
        return N;
    }
}