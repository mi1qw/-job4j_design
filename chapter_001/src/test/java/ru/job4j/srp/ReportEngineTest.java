package ru.job4j.srp;

import org.junit.Test;

import java.util.Calendar;
import java.util.Comparator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReportEngineTest implements Nrn {
    private void display(final AllEmployers engine) {
        System.out.println(engine.generateFormat((em -> true), Comparator.comparing(Employer::getName), Format.JSON) + nRN());
        System.out.println(engine.generateFormat((em -> true), Comparator.comparing(Employer::getName), Format.CONSOLE) + N);
        System.out.println(engine.generateFormat((em -> true), Comparator.comparing(Employer::getName), Format.XML) + N);
        System.out.println(engine.generateFormat((em -> true), Comparator.comparing(Employer::getName), Format.HTML) + N);
    }

    @Test
    public void progr() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employer worker = new Employer("Ivan", now, now, 100);
        store.add(worker);
        Employer worker1 = new Employer("Anna", now, now, 120);
        store.add(worker1);
        Employer worker2 = new Employer("Zorg", now, now, 70);
        store.add(worker2);

        Prog engine = new Prog(store);
        display(engine);

        StringBuilder expect = new StringBuilder()
                .append("<!DOCTYPE HTML>"
                        + "<html>"
                        + " <head>"
                        + "  <meta charset=\"utf-8\">"
                        + "  <title>Ответы в виде html</title>"
                        + " </head>"
                        + " <body>"
                        + "  <p>")
                .append("Name; Hired; Fired; Salary;</p>")

                .append(worker1.getName()).append(";")
                .append(worker1.getHired()).append(";")
                .append(worker1.getFired()).append(";")
                .append(worker1.getSalary()).append(";")
                .append("<br/>")

                .append(worker.getName()).append(";")
                .append(worker.getHired()).append(";")
                .append(worker.getFired()).append(";")
                .append(worker.getSalary()).append(";")
                .append("<br/>")

                .append(worker2.getName()).append(";")
                .append(worker2.getHired()).append(";")
                .append(worker2.getFired()).append(";")
                .append(worker2.getSalary()).append(";")
                .append("<br/>")
                .append("  </p>"
                        + " </body>"
                        + "</html>");

        assertThat(engine.generate((em -> true), Comparator.comparing(Employer::getName)), is(expect.toString()));
    }

    @Test
    public void buhuchet() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employer worker = new Employer("Ivan", now, now, 100 + 50);
        store.add(worker);
        Employer worker1 = new Employer("Anna", now, now, 120 + 50);
        store.add(worker1);
        Employer worker2 = new Employer("Zorg", now, now, 12);
        store.add(worker2);

        Employer worker3 = new Employer("Mort", now, now, 80);
        store.add(worker3);
        Employer worker4 = new Employer("Bell", now, now, 50);
        store.add(worker4);

        Buh engine = new Buh(store);
        display(engine);

        StringBuilder expect = new StringBuilder()

                .append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator())

                .append(worker1.getName()).append(";")
                .append(worker1.getHired()).append(";")
                .append(worker1.getFired()).append(";")
                .append(worker1.getSalary()).append(";")
                .append(System.lineSeparator())

                .append(worker4.getName()).append(";")
                .append(worker4.getHired()).append(";")
                .append(worker4.getFired()).append(";")
                .append(worker4.getSalary()).append(";")
                .append(System.lineSeparator())

                .append(worker.getName()).append(";")
                .append(worker.getHired()).append(";")
                .append(worker.getFired()).append(";")
                .append(worker.getSalary()).append(";")
                .append(System.lineSeparator())

                .append(worker3.getName()).append(";")
                .append(worker3.getHired()).append(";")
                .append(worker3.getFired()).append(";")
                .append(worker3.getSalary()).append(";")
                .append(System.lineSeparator())

                .append(worker2.getName()).append(";")
                .append(worker2.getHired()).append(";")
                .append(worker2.getFired()).append(";")
                .append(worker2.getSalary()).append(";")
                .append(System.lineSeparator());

        assertThat(engine.generate((em -> true), Comparator.comparing(Employer::getName)), is(expect.toString()));
    }

    @Test
    public void hr() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employer worker = new Employer("Ivan", now, now, 100);
        store.add(worker);
        Employer worker1 = new Employer("Anna", now, now, 120);
        store.add(worker1);
        Employer worker2 = new Employer("Zorg", now, now, 70);
        store.add(worker2);

        Employer worker3 = new Employer("Mort", now, now, 80);
        store.add(worker3);
        Employer worker4 = new Employer("Bell", now, now, 50);
        store.add(worker4);

        HR engine = new HR(store);

        String str = engine.generateFormat((em -> true), Comparator.comparing(Employer::getSalary).reversed(), Format.JSON);
        System.out.println(str + N);
        str = engine.generateFormat((em -> true), Comparator.comparing(Employer::getSalary).reversed(), Format.CONSOLE);
        System.out.println(str + N);
        str = engine.generateFormat((em -> true), Comparator.comparing(Employer::getSalary).reversed(), Format.XML);
        System.out.println(str + N);
        str = engine.generateFormat((em -> true), Comparator.comparing(Employer::getSalary).reversed(), Format.HTML);
        System.out.println(str + N);

        StringBuilder expect = new StringBuilder()
                .append("Name; Salary;")
                .append(System.lineSeparator())

                .append(worker1.getName()).append(";")
                .append(worker1.getSalary()).append(";")
                .append(System.lineSeparator())

                .append(worker.getName()).append(";")
                .append(worker.getSalary()).append(";")
                .append(System.lineSeparator())

                .append(worker3.getName()).append(";")
                .append(worker3.getSalary()).append(";")
                .append(System.lineSeparator())

                .append(worker2.getName()).append(";")
                .append(worker2.getSalary()).append(";")
                .append(System.lineSeparator())

                .append(worker4.getName()).append(";")
                .append(worker4.getSalary()).append(";")
                .append(System.lineSeparator());

        assertThat(engine.generate((em -> true), Comparator.comparing(Employer::getSalary).reversed()), is(expect.toString()));
    }
}

