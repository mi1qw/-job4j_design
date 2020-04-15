package ru.job4j.argzip;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

/**
 * Класс {@code Matchext} для реализаций сравнения в зависимости от ключа
 * по маске, полное совпадение имени, регулярное выражение
 */
class Matchext implements MatchextIn {
    // сравнение по маске
    @Override
    public boolean matchM(final Path file, String pattern) {
        boolean res = false;
        FileSystem fs = FileSystems.getDefault();
        PathMatcher matcher = fs.getPathMatcher("glob:" + pattern);
        if (matcher.matches(file.getFileName())) {
            //System.out.print(String.format("Found matched file: '%s'.%n", file));
            res = true;
        }
        return res;
    }

    // сравнение полное совпадение имени
    @Override
    public boolean matchF() {
        return false;
    }

    // сравнение регулярное выражение
    @Override
    public boolean matchR() {
        return false;
    }
}
