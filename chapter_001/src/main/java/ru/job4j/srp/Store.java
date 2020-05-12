package ru.job4j.srp;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface Store {
    List<Employer> findBy(Predicate<Employer> filter, Comparator<Employer> comparator);
}


interface Nrn {
    String N = System.lineSeparator();

    default String nRN() {
        return N;
    }
}