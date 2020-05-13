package ru.job4j.srp;

import java.util.Calendar;

public class Employer {
    private String name;
    private Calendar hired;
    private Calendar fired;
    private double salary;

    public Employer(final String name, final Calendar hired, final Calendar fired, final double salary) {
        this.name = name;
        this.hired = hired;
        this.fired = fired;
        this.salary = salary;
    }

    public final String getName() {
        return name;
    }

    public final Calendar getHired() {
        return hired;
    }

    public final Calendar getFired() {
        return fired;
    }

    public final double getSalary() {
        return salary;
    }
}