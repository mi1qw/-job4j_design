package ru.job4j.argzip;

import org.junit.Assert;
import org.junit.Test;

public class ArgZipTest {
    @Test
    public void correctKeysAndAfguments() throws Wrongkey, UseKeyDEO {
        String[] args = "-d src/test/java/ru/job4j/argzip -e *.java -o project.zip".split("\\s+");
        Assert.assertTrue(new ArgZip(args).valid());
    }

    @Test(expected = IllegalStateException.class)
    public void incorrectdirectory() throws IllegalStateException, Wrongkey, UseKeyDEO {
        String[] args = "-d incorrec -e *.java  -o project.zip".split("\\s+");
        new ArgZip(args).valid();
    }

    @Test(expected = IllegalStateException.class)
    public void directoryIsFile() throws IllegalStateException, Wrongkey, UseKeyDEO {
        String[] args = "-d src/test/java/ru/job4j/argzip/ArgZipTest.java -e *.java  -o project.zip".split("\\s+");
        new ArgZip(args).valid();
    }

    @Test(expected = IllegalStateException.class)
    public void wrongNameZip() throws IllegalStateException, Wrongkey, UseKeyDEO {
        String[] args = "-d src/test/java/ru/job4j/argzip -e *.java -o project.z0".split("\\s+");
        new ArgZip(args).valid();
    }

    @Test(expected = UseKeyDEO.class)
    public void withoutOutput() throws Wrongkey, UseKeyDEO {
        String[] args = "-d src/test/java/ru/job4j/argzip -e *.java -o ".split("\\s+");
        new ArgZip(args).valid();
    }

    @Test(expected = UseKeyDEO.class)
    public void withoutDirectory() throws Wrongkey, UseKeyDEO {
        String[] args = "-d  -e *.java -o project.zip".split("\\s+");
        new ArgZip(args).valid();
    }

    @Test(expected = Wrongkey.class)
    public void wrongKey() {
        String[] args = "-z src/test/java/ru/job4j/argzip -e *.java -o project.zip".split("\\s+");
        new ArgZip(args).valid();
    }
}