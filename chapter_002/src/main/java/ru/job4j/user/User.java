package ru.job4j.user;

import java.util.Calendar;
import java.util.Objects;

public class User {
    private String name;
    private int children;
    private Calendar birthday;

    public User(final String name, final int children, final Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    /**
     * Переопределить hashCode.
     *
     * @return hash только полей
     */
    @SuppressWarnings("CheckStyle")
    @Override
    public int hashCode() {
        return Objects.hash(name, children, birthday);
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
