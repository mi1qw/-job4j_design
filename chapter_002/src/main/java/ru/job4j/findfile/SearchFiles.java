package ru.job4j.findfile;

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
 */
class SearchFiles implements FileVisitor<Path> {
    private final List<Path> list = new ArrayList<>();
    private List<String> typeFile;
    private ArgFuncIn matcher;

    /**
     * конструктор {@code Files}.
     *
     * @param matcher  ссылка реализаций сравнения в зависимости от ключа
     *                 по маске, полное совпадение имени, регулярное выражение
     * @param typeFile список имён/паттернов для поиска
     * @see ArgFuncIn фугкциональный интерфейс ArgFuncIn <T> boolean valid(T t)
     */
    SearchFiles(final ArgFuncIn matcher, final List<String> typeFile) {
        this.matcher = matcher;
        this.typeFile = typeFile;
    }

    /**
     * @return the files
     */
    List<Path> getFiles() {
        return list;
    }

    @Override
    public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
        return CONTINUE;
    }

    /**
     * Вызывается метод по ссылке для сравнения
     * <pre>{@code
     *  String[] s = {pattern, name.toString()};
     *  matcher.valid(s);
     *  }</pre>
     * <p>
     * Передаю два аргументе в массиве, чтобы использовать одно поле класса {@link ArgKey}
     * для нескольких типов функций проверки сравнения или валидности данных</p>
     * Сравниваются все паттерны typeFile в цикле.
     *
     * @param file путь, извлекается имя каждого файла для сравнения.
     * @return возвращает CONTINUE
     */
    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
        Path name = file.getFileName();
        for (String pattern : typeFile) {
            String[] s = {pattern, name.toString()};
            if (matcher.valid(s)) {
                list.add(file);
            }
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
        return CONTINUE;
    }
}
