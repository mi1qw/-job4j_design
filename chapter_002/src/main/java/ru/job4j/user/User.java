package ru.job4j.user;

import java.util.Calendar;

public class User {
    private String name;
    private int children;
    private Calendar birthday;

    public User(final String name, final int children, final Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    @Override
    public final String toString() {
        return "User{"
                + System.lineSeparator()
                + "name='" + name + '\''
                + ", children=" + children
                + ", birthday=" + birthday.getTime() + '}'
                + " hashCode=" + this.hashCode();
    }
}
