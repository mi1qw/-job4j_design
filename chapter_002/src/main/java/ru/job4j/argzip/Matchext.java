package ru.job4j.argzip;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

/**
 * Класс {@code Matchext} для реализаций сравнения по маске.
 */
class Matchext implements MatchextIn {
    // сравнение по маске
    @Override
    public boolean matchM(final Path file, final String pattern) {
        boolean res = false;
        FileSystem fs = FileSystems.getDefault();
        PathMatcher matcher = fs.getPathMatcher("glob:" + pattern);
        if (matcher.matches(file.getFileName())) {
            res = true;
        }
        return res;
    }
}
