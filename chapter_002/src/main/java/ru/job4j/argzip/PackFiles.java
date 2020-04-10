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
    /**
     * конструктор {@code PackFiles}
     *
     * @param a    класс аргументов для доступа к данным аргументов, на всякий случай ..можно удалить
     * @param zout поток вывода ZipOutputStream
     *             m интерфейсная ссылка реализаций сравнения в зависимости от ключа
     *             по маске, полное совпадение имени, регулярное выражение
     *             MatchextIn m интерфейсная ссылка методы сравнения
     */
    public PackFiles(ArgZip a, ZipOutputStream zout) {
        this.a = a;
        this.zout = zout;
        this.m = new Matchext();
    }

    List<String> list = new ArrayList<>();
    List<String> typeFile;
    ArgZip a;
    ZipOutputStream zout;
    MatchextIn m;

    public List<String> getFiles() {
        return list;
    }

    public void setTypeFile(List<String> typeFile) {
        this.typeFile = typeFile;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return CONTINUE;
    }

    /**
     * сравнивает в цикле с каждым типом файла из typeFile
     *
     * @param file путь, извлекается именя файла для сравнения
     * @return возвращает CONTINUE в случае совпадения, исключая добавление файла в архив
     * для этого вызывается по интерфейсной ссылке методы  сравнения m.matchM
     */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
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
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return CONTINUE;
    }

    //если файл не найден сохраняет структуру в виде пустых папок

    /**
     * снять комментрий если необходимо сохранить пустые папки.
     * Сохраняет полную структуру каталога, даже при исключении всех файлов.
     */
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        //packSingleFolder(dir.toFile(), zout);
        return CONTINUE;
    }

    /**
     * добавляет запись putNextEntry с файлом и выводит данные в архив
     */
    public void packSingleFile(File source, ZipOutputStream zout) throws IOException {
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
     * добавляет запись putNextEntry с пустой папкой и выводит данные в архив
     */
    public void packSingleFolder(File source, ZipOutputStream zout) throws IOException {
        try {
            zout.putNextEntry(new ZipEntry(source.getPath() + "\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
