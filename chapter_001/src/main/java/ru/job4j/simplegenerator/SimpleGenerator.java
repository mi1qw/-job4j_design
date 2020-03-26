package ru.job4j.simplegenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleGenerator {
    //@SuppressWarnings("checkstyle:innerassignment")
    @SuppressWarnings("all")

    public static String simpleGenerator(final String string, final Map<String, String> list) throws BadException {
        //final Pattern keys = Pattern.compile("(?<=\\$\\{)[^}]+(?=\\})");
        final Pattern keys = Pattern.compile("(\\$\\{[^}]+})");
        StringBuffer sb = new StringBuffer();

        Set<String> keyList = list.keySet();        // список ключей
        Set<String> replace1 = new TreeSet<>();     // найденные ключи в строке
        String qq = "";                             // текущий ключ замены
        String a = "";
        try {
            if (string.isBlank()) {
                throw new BlankString();
            }
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
                throw new UnwantedKeys();
            }
        } catch (NoKeys e) {
            e.noKeys(qq);
        } catch (BlankString e) {
            e.blankString();
        } catch (UnwantedKeys e) {
            e.unwantedKeys(keyList);
        }
        return sb.toString();
    }

    public static void main(final String[] args) {
        Map<String, String> list = new HashMap<>();
        list.put("name", "Petr");
        list.put("name1", "Serj");
        list.put("subject", "you");
        //list.put("subject11", "you");

        String string = "I am a ${name}, Who are ${subject}? My name is ${name1}.";
        String result = simpleGenerator(string, list);
        System.out.println(result + "\n");

        Map<String, String> list1 = new HashMap<>();
        list1.put("sos", "Aaaa");
        string = " Help, ${sos}, ${sos}, ${sos}";
        result = simpleGenerator(string, list1);
        System.out.println(result);
    }
}
