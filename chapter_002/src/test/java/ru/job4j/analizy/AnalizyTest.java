package ru.job4j.analizy;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AnalizyTest {
    List<String> expected = List.of(
            new String("10:58:01;11:02:02"),
            new String("11:04:01;11:06:01"),
            new String("11:07:01;11:08:01"));

    @Test
    public void testingUnavailable() {
        Analizy a = new Analizy();
        a.unavailable("./data/server.csv", "./data/unavailable.csv");
        assertThat(expected, is(a.listout));
    }

    @Test
    public void compareWithSavedFile() {
        Analizy a = new Analizy();
        a.unavailable("./data/server.csv", "./data/unavailable.csv");
        a.listin.clear();
        a.readfile("./data/unavailable.csv");
        assertThat(expected, is(a.listin));
    }
}