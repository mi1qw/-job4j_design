package ru.job4j.srp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MemStore implements Store {
    private final List<Employer> employers = new ArrayList<>();

    public void add(Employer em) {
        employers.add(em);
    }

    @Override
    public List<Employer> findBy(Predicate<Employer> filter, Comparator<Employer> comparator) {
        return employers.stream().filter(filter).sorted(comparator).collect(Collectors.toList());
    }
}

