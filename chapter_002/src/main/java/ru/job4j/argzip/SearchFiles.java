package ru.job4j.argzip;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * реализация методов FileVisitor
 * для обхода дерева каталогов
 * <p>
 * m - интерфейсная ссылка реализации сравнения по маске
 *
 * @see MatchextIn#matchM MatchextIn#matchM MatchextIn m интерфейсная ссылка методы сравнения
 */
class SearchFiles implements FileVisitor<Path> {
    private final List<File> list = new ArrayList<>();
    private List<String> typeFile;
    private final MatchextIn m = new Matchext();

    /**
     * конструктор {@code SearchFiles}.
     *
     * @param typeFile типы названия файлов для исключения
     */
    SearchFiles(final List<String> typeFile) {
        this.typeFile = typeFile;
    }

    /**
     * @return найденный список файлов
     */
    List<File> getFiles() {
        return list;
    }

    @Override
    public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) {
        return CONTINUE;
    }

    /**
     * сравнивает в цикле с каждым типом файла из typeFile.
     *
     * @param file путь, извлекается именя файла для сравнения
     * @return возвращает CONTINUE в случае совпадения, исключая добавление файла в архив
     * для этого вызывается по интерфейсной ссылке методы  сравнения m.matchM
     */
    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) {
        Path name = file.getFileName();
        for (String pattern : typeFile) {
            if (m.matchM(name, pattern)) {
                return CONTINUE;
            }
        }
        list.add(file.toFile());
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(final Path file, final IOException exc) {
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) {

        return CONTINUE;
    }
}
