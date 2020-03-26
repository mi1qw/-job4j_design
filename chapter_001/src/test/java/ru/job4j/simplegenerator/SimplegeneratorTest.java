package ru.job4j.simplegenerator;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Ignore
public class SimplegeneratorTest {
    Set<String> keyList;
    String qq;
    String a = "";

    @Test
    public void getstring() {
        Map<String, String> list = new HashMap<>();
        list.put("name", "Petr");
        list.put("name1", "Serj");
        list.put("subject", "you");

        String string = "I am a ${name}, Who are ${subject}? My name is ${name1}.";
        String expected = SimpleGenerator.simpleGenerator(string, list);
        System.out.println("Test-1 ОК!\n" + string + "\n" + expected + "\n");
        assertThat(expected, is("I am a Petr, Who are you? My name is Serj."));
    }

    @SuppressWarnings("checkstyle:InnerAssignment")
    @Test(expected = UnwantedKeys.class)
    public void thereisnosuchkeysinstring() throws UnwantedKeys {
        Map<String, String> list = new HashMap<>();
        list.put("name", "Petr");
        list.put("name1", "Serj");
        list.put("subject", "you");
        list.put("dude", "Вася");
        //list.put("subject2", "Lena");
        String string = "I am a ${name}, Who are ${subject}? My name is ${name1}.";

        //try {
        final Pattern keys = Pattern.compile("(\\$\\{[^}]+})");
        StringBuffer sb = new StringBuffer();
        keyList = list.keySet();
        Set<String> replace1 = new TreeSet<>();
        Matcher m = keys.matcher(string);
        while (m.find()) {
            qq = m.group().replaceAll("[${}]+", "");
            a = list.get(qq);
            if (a == null) {
                throw new NoKeys();
            }
            m.appendReplacement(sb, a);
            replace1.add(qq);
        }
        m.appendTail(sb);
        keyList.removeAll(replace1);
        if (!keyList.isEmpty()) {
            System.out.print("Error - There is no such keys in string ");
            System.out.println(keyList);
            System.out.println();
            throw new UnwantedKeys();
        }
    }

    @SuppressWarnings("checkstyle:InnerAssignment")
    @Test(expected = NoKeys.class)
    public void nokeysfoundinList() throws NoKeys {
        Map<String, String> list = new HashMap<>();
        list.put("name", "Petr");
        list.put("name1", "Serj");
        //list.put("subject", "you");

        String string = "I am a ${name}, Who are ${subject}? My name is ${name1}.";

        final Pattern keys = Pattern.compile("(\\$\\{[^}]+})");
        StringBuffer sb = new StringBuffer();
        keyList = list.keySet();
        Set<String> replace1 = new TreeSet<>();
        Matcher m = keys.matcher(string);
        while (m.find()) {
            qq = m.group().replaceAll("[${}]+", "");
            a = list.get(qq);
            if (a == null) {
                System.out.print("Erorr - No keys found in List -> ");
                System.out.println(qq);
                System.out.println();
                throw new NoKeys();
            }
            m.appendReplacement(sb, a);
            replace1.add(qq);
        }
        m.appendTail(sb);
    }
}

