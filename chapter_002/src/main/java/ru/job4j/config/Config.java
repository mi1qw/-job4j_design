package ru.job4j.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

//@FunctionalInterface
//interface Search {
//    String search(String string);
//}


public class Config {
    private final String path;
    private final Map<String, String> values = new HashMap<String, String>();

    public Config(final String path) {
        this.path = path;
    }

    String search(String string) {
        if (string.startsWith("##")) {
            //if (string.startsWith("##") || string.isEmpty()) {
            //System.out.println("1111111111111111");
            return "";
        }
        return string;
    }

    public void load() {
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            //read.lines().map(n -> search(n)).filter(n -> !n.equals(""))
            read.lines().map(n -> search(n)).filter(n -> !n.isEmpty())
                    .forEach(n -> {
                        String[] str = n.split("=");
                        values.put(str[0], str[1]);
                        //System.out.println(n);
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

//String path = "./data/pair_without_comment.properties";
//data/pair_without_comment.properties
//##
//-- Это стандартный комментарий SQL
/* многострочный комментарий
 * с вложенностью: /* вложенный блок комментария */
//*/

//String path = "chapter_002/data/pair_without_comment.properties";
//pair_without_comment.properties