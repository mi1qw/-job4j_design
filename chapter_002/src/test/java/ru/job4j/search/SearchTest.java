package ru.job4j.search;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SearchTest {
    List<String> expected = List.of(new String("Search.java"));

    @Test
    public void searchJavaInPath() throws IOException {
        Path start = Paths.get("./src/main/java/ru/job4j/search/Search.java");
        assertThat(expected, is(Search.search(start, "java")));
    }
}