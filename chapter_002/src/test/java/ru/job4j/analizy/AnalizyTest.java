package ru.job4j.analizy;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AnalizyTest {
    private List<String> expected = List.of(
            "10:58:01;11:02:02",
            "11:04:01;11:06:01",
            "11:07:01;11:08:01");
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testingUnavailable() throws IOException {
        Analizy a = new Analizy();
        File target = folder.newFile("unavailable.csv");
        a.unavailable("./data/server.csv", target.getAbsolutePath());
        assertThat(expected, is(a.listout));
    }

    @Test
    public void compareWithSavedFile() throws IOException {
        Analizy a = new Analizy();
        File target = folder.newFile("unavailable.csv");
        a.unavailable("./data/server.csv", target.getAbsolutePath());
        a.listin.clear();
        a.readfile(target.getAbsolutePath());
        assertThat(expected, is(a.listin));
    }
}