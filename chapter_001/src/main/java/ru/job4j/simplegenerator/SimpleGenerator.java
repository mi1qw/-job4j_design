package ru.job4j.simplegenerator;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleGenerator {
    public final String simpleGenerator(final String string, final Map<String, String> list) {
        final Pattern keys = Pattern.compile("(\\$\\{[^}]+})");     //Pattern.compile("(?<=\\$\\{)[^}]+(?=\\})")
        StringBuffer sb = new StringBuffer();

        Set<String> keyList = list.keySet();        // список ключей
        Set<String> replace1 = new TreeSet<>();     // найденные ключи в строке
        String keyplace = "";                       // текущий ключ замены
        String a = "";
        try {
            if (string.isBlank()) {
                blankString();
            }
            Matcher m = keys.matcher(string);
            while (m.find()) {
                keyplace = m.group().replaceAll("[${}]+", "");
                a = list.get(keyplace);
                if (a == null) {
                    noKeys();
                }
                m.appendReplacement(sb, a);
                replace1.add(keyplace);
            }
            m.appendTail(sb);

            keyList.removeAll(replace1);
            if (!keyList.isEmpty()) {
                unwantedKeys();
            }
        } catch (NoKeys e) {
            e.noKeys(keyplace);
        } catch (BlankString e) {
            e.blankString();
        } catch (UnwantedKeys e) {
            e.unwantedKeys(keyList);
        }
        return sb.toString();
    }

    /**
     * Mockito test
     */
    protected void blankString() {
        throw new BlankString();
    }

    /**
     * Mockito test
     */
    protected void noKeys() {
        throw new NoKeys();
    }

    /**
     * Mockito test
     */
    protected void unwantedKeys() {
        throw new UnwantedKeys();
    }
}
