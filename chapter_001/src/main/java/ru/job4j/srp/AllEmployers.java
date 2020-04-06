package ru.job4j.srp;

import java.util.Comparator;
import java.util.function.Predicate;

interface AllEmployers {
    String generate(Predicate<Employer> filter, Comparator<Employer> comparator);

    String generateFormat(Predicate<Employer> filter, Comparator<Employer> comparator, Format format);
}
