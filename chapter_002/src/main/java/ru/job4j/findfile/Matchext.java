package ru.job4j.findfile;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

/**
 * Класс {@code Matchext} для реализаций сравнения в зависимости от ключа
 * по маске, полное совпадение имени, регулярное выражение.
 * Используется тот же интерфейс {@link ArgFuncIn}
 */
class Matchext {
    private String pattern;

    // сравнение по маске
    public <T> boolean matchM(final T t) {
        String[] s = (String[]) t;
        pattern = s[0];
        String file = s[1];

        boolean res = false;
        FileSystem fs = FileSystems.getDefault();
        PathMatcher matcher = fs.getPathMatcher("glob:" + pattern);
        if (matcher.matches(Paths.get(file))) {
            res = true;
        }
        return res;
    }

    //полное совпадение имени
    public <T> boolean matchF(final T t) {
        String[] s = (String[]) t;
        pattern = s[0];
        //String file = ;
        String file = Paths.get(s[1]).getFileName().toString();

        boolean res = false;
        if (pattern.equals(file)) {
            res = true;
        }
        return res;
    }

    //регулярное выражение
    public <T> boolean matchR(final T t) {
        String[] s = (String[]) t;
        pattern = s[0];
        String file = s[1];

        boolean res = false;
        if (file.matches(pattern)) {
            res = true;
        }
        return res;
    }
}
