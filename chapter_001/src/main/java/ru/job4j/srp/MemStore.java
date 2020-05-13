package ru.job4j.srp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MemStore implements Store {
    private final List<Employer> employers = new ArrayList<>();

    protected final void add(final Employer em) {
        employers.add(em);
    }

    /**
     * @param filter
     * @param comparator
     * @return
     */
    @Override
    public List<Employer> findBy(final Predicate<Employer> filter, final Comparator<Employer> comparator) {
        return employers.stream().filter(filter).sorted(comparator).collect(Collectors.toList());
    }
}

