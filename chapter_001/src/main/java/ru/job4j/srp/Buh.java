package ru.job4j.srp;

import ru.job4j.simplegenerator.SimpleGenerator;

import java.util.Calendar;
import java.util.Comparator;
import java.util.function.Predicate;

class Buh implements AllEmployers {
    private Store store;

    public Buh(Store store) {
        this.store = store;
    }

    @Override
    public String generateFormat(Predicate<Employer> filter, Comparator<Employer> comparator, Format format) {
        StringBuilder text = new StringBuilder();
        text.append("${head}")
                .append("${p}Name; Hired; Fired; Salary;${/p}")
                .append("${Employees}");
        for (Employer employer : store.findBy(filter, comparator)) {
            text.append("${name}").append(employer.getName()).append(";").append("${/name}")
                    .append("${hired}").append(employer.getHired().get(Calendar.DATE) + "/" + (1 + employer.getHired().get(Calendar.MONTH))
                    + "/" + employer.getHired().get(Calendar.YEAR)).append(";").append("${/hired}")
                    .append("${fired}").append(employer.getHired().get(Calendar.DATE) + "/" + (1 + employer.getHired().get(Calendar.MONTH))
                    + "/" + employer.getHired().get(Calendar.YEAR)).append(";").append("${/fired}")
                    .append("${salary}").append(employer.getSalary()).append(";").append("${/salary}");
        }
        text.append("${/Employees}")
                .append("${/head}");

        return SimpleGenerator.simpleGenerator(text.toString(), new Report().getformat(format));
    }

    @Override
    public String generate(Predicate<Employer> filter, Comparator<Employer> comparator) {
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
