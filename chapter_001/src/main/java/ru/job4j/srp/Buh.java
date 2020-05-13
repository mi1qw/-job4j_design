package ru.job4j.srp;

import java.util.Comparator;
import java.util.function.Predicate;

class Buh implements AllEmployers {
    private Store store;

    protected Buh(final Store store) {
        this.store = store;
    }

    @Override
    public String generateFormat(final Predicate<Employer> filter, final Comparator<Employer> comparator,
                                 final Format format) {
        return generateFormatBuhProg(filter, comparator, format, store);
    }

    @Override
    public String generate(final Predicate<Employer> filter, final Comparator<Employer> comparator) {
        StringBuilder text = new StringBuilder();
        text.append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator());
        for (Employer employer : store.findBy(filter, comparator)) {
            text.append(employer.getName()).append(";")
                    .append(employer.getHired()).append(";")
                    .append(employer.getFired()).append(";")
                    .append(employer.getSalary()).append(";")
                    .append(System.lineSeparator());
        }
        return text.toString();
    }
}
