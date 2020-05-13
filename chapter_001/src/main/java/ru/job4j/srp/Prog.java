package ru.job4j.srp;

import java.util.Comparator;
import java.util.function.Predicate;

class Prog implements AllEmployers {
    private Store store;

    protected Prog(final Store store) {
        this.store = store;
    }

    @Override
    public String generateFormat(final Predicate<Employer> filter, final Comparator<Employer> comparator,
                                 final Format format) {
        return generateFormatBuhProg(filter, comparator, format, store);
    }

    public String generate(final Predicate<Employer> filter, final Comparator<Employer> comparator) {
        StringBuilder text = new StringBuilder();
        text.append("<!DOCTYPE HTML>"
                + "<html>"
                + " <head>"
                + "  <meta charset=\"utf-8\">"
                + "  <title>Ответы в виде html</title>"
                + " </head>"
                + " <body>"
                + "  <p>")
                .append("Name; Hired; Fired; Salary;</p>");
        for (Employer employer : store.findBy(filter, comparator)) {
            text.append(employer.getName()).append(";")
                    .append(employer.getHired()).append(";")
                    .append(employer.getFired()).append(";")
                    .append(employer.getSalary()).append(";")
                    .append("<br/>");
        }
        text.append("  </p>"
                + " </body>"
                + "</html>");
        return text.toString();
    }
}



