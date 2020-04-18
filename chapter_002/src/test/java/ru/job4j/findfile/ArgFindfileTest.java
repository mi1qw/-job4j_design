package ru.job4j.findfile;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ArgFindfileTest {
    @Test
    public void findFilesMask() throws Wrongkey, UseKeyDEO, UnsupportedEncodingException {
        String[] args = "-d src/main/java/ru/job4j/findfile -n *.java -m -o log.txt".split("\\s+");
        ArgFindfile arg = new ArgFindfile(args);
        ByteArrayOutputStream actualStream = new ByteArrayOutputStream();
        if (arg.valid()) {
            try (PrintWriter filelist =
                         new PrintWriter(actualStream)) {
                Files visitor = new Files(arg.getMatcher(), filelist);
                visitor.setTypeFile(arg.getName());
                java.nio.file.Files.walkFileTree(arg.getDirectory(), visitor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Path path = Paths.get("src", "main", "java", "ru", "job4j", "findfile");
        String[] expected = {
                path.resolve("ArgFindfile.java").toString(),
                path.resolve("ArgFuncIn.java").toString(),
                path.resolve("ArgKey.java").toString(),
                path.resolve("Files.java").toString(),
                path.resolve("Findfile.java").toString(),
                path.resolve("Matchext.java").toString(),
                path.resolve("Wrongkey.java").toString()
        };
        String[] actual = actualStream.toString().split(System.lineSeparator());
        Arrays.sort(actual);

        assertThat(actual, is(expected));
    }

    @Test
    public void findFilesFullEqual() throws Wrongkey, UseKeyDEO {
        String[] args = "-d src/main/java/ru/job4j/findfile -n Findfile.java -f -o log.txt".split("\\s+");
        ArgFindfile arg = new ArgFindfile(args);
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        if (arg.valid()) {
            try (PrintWriter filelist =
                         new PrintWriter(actual)) {
                Files visitor = new Files(arg.getMatcher(), filelist);
                visitor.setTypeFile(arg.getName());
                java.nio.file.Files.walkFileTree(arg.getDirectory(), visitor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String expected = Paths.get("src", "main", "java", "ru", "job4j", "findfile", "Findfile.java").toString()
                + System.lineSeparator();

        assertThat(actual.toString(), is(expected));
    }

    @Test
    public void findTwoFilesRegularExpression() throws Wrongkey, UseKeyDEO {
        String[] args = ("-d src/main/java/ru/job4j/findfile -n ^Fil\\w+\\.java ^W\\w+\\.java -r -o "
                + "log.txt").split("\\s+");
        ArgFindfile arg = new ArgFindfile(args);
        ByteArrayOutputStream actualStream = new ByteArrayOutputStream();
        if (arg.valid()) {
            try (PrintWriter filelist =
                         new PrintWriter(actualStream)) {
                Files visitor = new Files(arg.getMatcher(), filelist);
                visitor.setTypeFile(arg.getName());
                java.nio.file.Files.walkFileTree(arg.getDirectory(), visitor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String[] actual = actualStream.toString().split(System.lineSeparator());
        Arrays.sort(actual);
        Path path = Paths.get("src", "main", "java", "ru", "job4j", "findfile");
        String[] expected = {
                path.resolve("Files.java").toString(),
                path.resolve("Wrongkey.java").toString()
        };

        assertThat(actual, is(expected));
    }

    @Test
    public void correctKeysAndAfguments() throws Wrongkey, UseKeyDEO {
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
