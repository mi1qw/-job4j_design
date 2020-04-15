package ru.job4j.argzip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * реализация методов FileVisitor
 * для обхода дерева каталогов
 */
class PackFiles implements FileVisitor<Path> {
    private final List<String> list = new ArrayList<>();
    private List<String> typeFile;
    private final ArgZip a;
    private final ZipOutputStream zout;
    private final MatchextIn m;

    /**
     * конструктор {@code PackFiles}.
     *
     * @param a    класс аргументов для доступа к данным аргументов, на всякий случай ..можно удалить
     * @param zout поток вывода ZipOutputStream<p>             m - интерфейсная ссылка реализаций сравнения в зависимости от ключа             по маске, полное совпадение имени, регулярное выражение
     * @see MatchextIn#matchM MatchextIn#matchM MatchextIn m интерфейсная ссылка методы сравнения
     */
    PackFiles(ArgZip a, ZipOutputStream zout) {
        this.a = a;
        this.zout = zout;
        this.m = new Matchext();
    }

    /**
     * Gets files.
     *
     * @return the files
     */
    private List<String> getFiles() {
        return list;
    }

    /**
     * Sets type file.
     *
     * @param typeFile the type file
     */
    public void setTypeFile(final List<String> typeFile) {
        this.typeFile = typeFile;
    }

    @Override
    public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
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
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
        Path name = file.getFileName();
        for (String pattern : typeFile) {
            if (m.matchM(name, pattern)) {
                return CONTINUE;
            }
        }
        packSingleFile(file.toFile(), zout);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
        return CONTINUE;
    }

    //если файл не найден сохраняет структуру в виде пустых папок

    /**
     * снять комментрий если необходимо сохранить пустые папки.
     * Сохраняет полную структуру каталога, даже при исключении всех файлов.
     */
    @Override
    public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
        //packSingleFolder(dir.toFile(), zout);
        return CONTINUE;
    }

    /**
     * добавляет запись putNextEntry с файлом и выводит данные в архив.
     *
     * @param source the source
     * @param zout   the zout
     * @throws IOException the io exception
     */
    public void packSingleFile(final File source, final ZipOutputStream zout) throws IOException {
        try {
            zout.putNextEntry(new ZipEntry(source.getPath()));
            try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(source))) {
                zout.write(out.readAllBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * добавляет запись putNextEntry с пустой папкой и выводит данные в архив.
     *
     * @param source the source
     * @param zout   the zout
     * @throws IOException the io exception
     */
    public void packSingleFolder(final File source, final ZipOutputStream zout) throws IOException {
        try {
            zout.putNextEntry(new ZipEntry(source.getPath() + "\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
