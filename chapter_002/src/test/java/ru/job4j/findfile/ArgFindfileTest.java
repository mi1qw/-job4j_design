package ru.job4j.findfile;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ArgFindfileTest {
    @Test
    public void findFilesMask() throws Wrongkey, UseKeyDEO, IOException {
        final String[] args = "-d src/main/java/ru/job4j/findfile -n *.java -m -o log.txt".split("\\s+");
        SearchFiles visitor = null;
        Path[] actual = new Path[0];
        final ArgFindfile arg = new ArgFindfile(args);
        if (arg.valid()) {
            visitor = new SearchFiles(arg.getMatcher(), arg.getName());
            Files.walkFileTree(arg.getDirectory(), visitor);
        }
        final Path path = Paths.get("src", "main", "java", "ru", "job4j", "findfile");
        Path[] expected = {
                path.resolve("ArgFindfile.java"),
                path.resolve("ArgFuncIn.java"),
                path.resolve("ArgKey.java"),
                path.resolve("Findfile.java"),
                path.resolve("Matchext.java"),
                path.resolve("SearchFiles.java"),
                path.resolve("Wrongkey.java")
        };
        actual = visitor.getFiles().toArray(actual);
        Arrays.sort(actual);

        assertThat(actual, is(expected));
    }

    @Test
    public void findFilesFullEqual() throws Wrongkey, UseKeyDEO, IOException {
        final String[] args = "-d src/main/java/ru/job4j/findfile -n Findfile.java -f -o log.txt".split("\\s+");
        SearchFiles visitor = null;
        Path[] actual = new Path[0];
        final ArgFindfile arg = new ArgFindfile(args);
        if (arg.valid()) {
            visitor = new SearchFiles(arg.getMatcher(), arg.getName());
            Files.walkFileTree(arg.getDirectory(), visitor);
        }
        final Path[] expected = {Paths.get("src", "main", "java", "ru", "job4j", "findfile", "Findfile.java")};
        actual = visitor.getFiles().toArray(actual);

        assertThat(actual, is(expected));
    }

    @Test
    public void findTwoFilesRegularExpression() throws Wrongkey, UseKeyDEO, IOException {
        final String[] args = ("-d src/main/java/ru/job4j/findfile -n ^Fin\\w+\\.java ^W\\w+\\.java -r -o "
                + "log.txt").split("\\s+");
        SearchFiles visitor = null;
        Path[] actual = new Path[0];
        final ArgFindfile arg = new ArgFindfile(args);
        if (arg.valid()) {
            visitor = new SearchFiles(arg.getMatcher(), arg.getName());
            Files.walkFileTree(arg.getDirectory(), visitor);
        }
        final Path[] expected = {
                Paths.get("src", "main", "java", "ru", "job4j", "findfile", "Findfile.java"),
                Paths.get("src", "main", "java", "ru", "job4j", "findfile", "Wrongkey.java")
        };
        actual = visitor.getFiles().toArray(actual);

        assertThat(actual, is(expected));
    }

    @Test
    public void correctKeysAndArguments() throws Wrongkey, UseKeyDEO {
        String[] args = "-d src -n *.java -m -o log.txt".split("\\s+");
        Assert.assertTrue(new ArgFindfile(args).valid());
    }

    @Test
    public void useTwoExtFiles() throws Wrongkey, UseKeyDEO {
        String[] args = "-d src -n *.java *.txt -f -o log.txt".split("\\s+");
        Assert.assertTrue(new ArgFindfile(args).valid());
    }

    @Test(expected = IllegalStateException.class)
    public void incorrectdirectory() throws IllegalStateException, Wrongkey, UseKeyDEO {
        String[] args = "-d incorrec -n *.java -m -o log.txt".split("\\s+");
        new ArgFindfile(args).valid();
    }

    @Test(expected = IllegalStateException.class)
    public void directoryIsFile() throws IllegalStateException, Wrongkey, UseKeyDEO {
        String[] args = "-d src/test/java/ru/job4j/argzip/ArgZipTest.java  -n *.java -m -o log.txt".split("\\s+");
        new ArgFindfile(args).valid();
    }

    @Test(expected = IllegalStateException.class)
    public void wrongNameFile() throws IllegalStateException, Wrongkey, UseKeyDEO {
        String[] args = "-d src -n *.java *.txt -f -o project.z0".split("\\s+");
        new ArgFindfile(args).valid();
    }

    @Test(expected = UseKeyDEO.class)
    public void withoutOutput() throws Wrongkey, UseKeyDEO {
        String[] args = "-d src -n *.java *.txt -f -o ".split("\\s+");
        new ArgFindfile(args).valid();
    }

    @Test(expected = UseKeyDEO.class)
    public void withoutDirectory() throws Wrongkey, UseKeyDEO {
        String[] args = "-d -n *.java -m -o log.txt".split("\\s+");
        new ArgFindfile(args).valid();
    }

    @Test(expected = Wrongkey.class)
    public void wrongKey() {
        String[] args = "-z src -n *.java -m -o log.txt".split("\\s+");
        new ArgFindfile(args).valid();
    }

    @Test(expected = UseKeyDEO.class)
    public void withoutExtFiles() {
        String[] args = "-d src -n -m -o log.txt".split("\\s+");
        new ArgFindfile(args).valid();
    }
}
