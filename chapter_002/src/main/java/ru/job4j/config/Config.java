package ru.job4j.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Config {
    private final String path;
    private final Map<String, String> values = new HashMap<String, String>();

    public Config(final String path) {
        this.path = path;
    }

    String search(String string) {
        if (string.startsWith("##")) {
            return "";
        }
        return string;
    }

    public void load() {
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().map(n -> search(n)).filter(n -> !n.isEmpty())
                    .forEach(n -> {
                        String[] str = n.split("=");
                        values.put(str[0], str[1]);
                    });
        } catch (Exception e) {
        }
    }

    public String value(String key) {
        return values.get(key);
        //throw new UnsupportedOperationException("Don't impl this method yet!");
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Config("chapter_002/data/pair_without_comment.properties"));
        System.out.println();

        Config c = new Config("chapter_002/data/app.properties");
        c.load();
        System.out.println(c.values);
    }
}
