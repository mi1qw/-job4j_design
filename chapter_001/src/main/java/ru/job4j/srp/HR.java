package ru.job4j.srp;

import ru.job4j.simplegenerator.SimpleGenerator;

import java.util.Comparator;
import java.util.function.Predicate;

class HR implements AllEmployers {
    private Store store;

    protected HR(final Store store) {
        this.store = store;
    }

    @Override
    public String generateFormat(final Predicate<Employer> filter, final Comparator<Employer> comparator,
                                 final Format format) {
        StringBuilder text = new StringBuilder();
        text.append("${head}")
                .append("${p}Name; Salary;${/p}")
                .append("${Employees}");
        for (Employer employer : store.findBy(filter, comparator)) {
            text.append("${name}").append(employer.getName()).append(";").append("${/name}")
                    .append("${salary}").append(employer.getSalary()).append(";").append("${/salary}");
        }
        text.append("${/Employees}")
                .append("${/head}");
        SimpleGenerator s = new SimpleGenerator();
        return s.simpleGenerator(text.toString(), new Report().getformat(format));
    }

    @Override
    public String generate(final Predicate<Employer> filter, final Comparator<Employer> comparator) {
        StringBuilder text = new StringBuilder();
        text.append("Name; Salary;")
                .append(System.lineSeparator());
        for (Employer employer : store.findBy(filter, comparator)) {
            text.append(employer.getName()).append(";")
                    .append(employer.getSalary()).append(";")
                    .append(System.lineSeparator());
        }
        return text.toString();
    }
}
