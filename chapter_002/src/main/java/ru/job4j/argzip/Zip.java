package ru.job4j.argzip;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Создание zip архива
 *
 * @throws Exception выбрасывается при полном отсутствии ключей проверка ключей делается отдельно.
 * Сначала проверяет все аргументы командной строки.
 * Только при успешной верификации создаётся архив.
 */
public class Zip {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalStateException("Usage java -jar dir.jar ROOT_FOLDER...");
        }
        new Zip().go(args);
    }

    void go(String[] args) throws Wrongkey, UseKeyDEO {
        ArgZip a = new ArgZip(args);
        if (a.valid()) {
            try (ZipOutputStream zip =
                         new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(a.output().toFile())))) {
                PackFiles visitor = new PackFiles(a, zip);
                visitor.setTypeFile(a.exclude());

                Files.walkFileTree(a.directory(), visitor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


