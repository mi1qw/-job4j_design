package ru.job4j.srp;

import ru.job4j.simplegenerator.SimpleGenerator;

import java.util.Calendar;
import java.util.Comparator;
import java.util.function.Predicate;

interface AllEmployers {
    String generate(Predicate<Employer> filter, Comparator<Employer> comparator);

    String generateFormat(Predicate<Employer> filter, Comparator<Employer> comparator, Format format);

    default String generateFormatBuhProg(final Predicate<Employer> filter, final Comparator<Employer> comparator,
                                         final Format format, Store store) {
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
        SimpleGenerator s = new SimpleGenerator();
        return s.simpleGenerator(text.toString(), new Report().getformat(format));
    }
}
