package ru.job4j.simplegenerator;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimplegeneratorTest {
    @Test
    public void getstring() {
        SimpleGenerator s = new SimpleGenerator();
        Map<String, String> list = new HashMap<>();
        list.put("name", "Petr");
        list.put("name1", "Serj");
        list.put("subject", "you");

        String string = "I am a ${name}, Who are ${subject}? My name is ${name1}.";
        String expected = s.simpleGenerator(string, list);
        System.out.println("Test-1 ОК!\n" + string + "\n" + expected + "\n");
        assertThat(expected, is("I am a Petr, Who are you? My name is Serj."));
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("DLS_DEAD_LOCAL_STORE")
    @Test
    public void whenBlankString() {
        Map<String, String> list = new HashMap<>();
        list.put("name", "Petr");
        String string = "";
        SimpleGenerator simple = Mockito.spy(SimpleGenerator.class);
        simple.simpleGenerator(string, list);

        Mockito.verify(simple).blankString();
    }

    @Test
    public void thereIsNoSuchKeysInStringUnwantedKeys() {
        Map<String, String> list = new HashMap<>();
        list.put("name", "Petr");
        list.put("name1", "Serj");
        list.put("subject", "you");
        list.put("dude", "Вася");
        String string = "I am a ${name}, Who are ${subject}? My name is ${name1}.";
        SimpleGenerator simple = Mockito.spy(SimpleGenerator.class);
        simple.simpleGenerator(string, list);

        Mockito.verify(simple).unwantedKeys();
    }

    @Test
    public void noKeysFoundInListNoKeys() {
        Map<String, String> list = new HashMap<>();
        list.put("name", "Petr");
        list.put("name1", "Serj");
        String string = "I am a ${name}, Who are ${subject}? My name is ${name1}.";
        SimpleGenerator simple = Mockito.spy(SimpleGenerator.class);
        simple.simpleGenerator(string, list);

        Mockito.verify(simple).noKeys();
    }
}

