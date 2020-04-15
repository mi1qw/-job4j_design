package ru.job4j.argzip;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.zip.ZipOutputStream;

/**
 * Создание zip архива
 *
 * @throws Exception выбрасывается при полном отсутствии ключей, проверка ключей делается отдельно.
 * Сначала проверяютсят все аргументы командной строки.
 * Только при успешной верификации создаётся архив.
 */
public final class Zip {
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


