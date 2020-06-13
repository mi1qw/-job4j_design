package ru.job4j.analize;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AnalizeTest {
    private List<Analize.User> previous;
    private List<Analize.User> current;

    @Before
    public void setUp() {
        previous = new ArrayList<>();
        previous.add(new Analize.User(1, "Ann"));
        previous.add(new Analize.User(2, "Boris"));
        previous.add(new Analize.User(3, "Carol"));
        current = new ArrayList<>(List.copyOf(previous));
    }

    @Test
    public void whenAddShouldIncreaseInfoAdded() {
        current.add(new Analize.User(10, "Bill"));
        System.out.println(previous);
        System.out.println(current);
        Analize.Info info = new Analize().diff(previous, current);
        assertThat(info.getAdded(), is(1));
        System.out.println(info);
        System.out.println();
    }

    @Test
    public void whenDeletedShouldIncreaseInfoDeleted() {
        current.remove(0);
        System.out.println(previous);
        System.out.println(current);
        Analize.Info info = new Analize().diff(previous, current);
        System.out.println(info);
        assertThat(info.getDeleted(), is(1));
        System.out.println();
    }

    @Test
    public void whenChangeExistingUser() {
        System.out.println(previous);
        current.set(2, new Analize.User(3, "Adam"));
        System.out.println(current);
        Analize.Info info = new Analize().diff(previous, current);
        System.out.println(info);
        assertThat(info.getChanged(), is(1));
        System.out.println();
    }

    @Test
    public void whenDeletedShouldIncreaseInfoDeleted1() {

        current.set(2, new Analize.User(3, "Adam"));
        current.remove(0);
        current.add(new Analize.User(10, "Bill"));
        System.out.println(previous);
        System.out.println(current);
        Analize.Info info = new Analize().diff(previous, current);
        System.out.println(info);

        assertThat(info.getDeleted()
                        + info.getAdded()
                        + info.getChanged(),
                is(3));
        System.out.println();
    }

    @Test
    public void anotherTest() {
        current.get(0).equals(null);
        assertThat(current.get(0).equals(current.get(0)), is(true));
    }
}