package ru.job4j.argzip;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Создание zip архива
 *
 * @throws Exception выбрасывается при полном отсутствии ключей, проверка ключей делается отдельно.
 * Сначала проверяютсят все аргументы командной строки.
 * Только при успешной верификации создаётся архив.
 */
public final class Zip {
    public static void main(final String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalStateException("Usage java -jar dir.jar ROOT_FOLDER...");
        }
        new Zip().argZip(args);
    }

    void argZip(final String[] args) throws Wrongkey, UseKeyDEO, IOException {
        ArgZip arg = new ArgZip(args);
        if (arg.valid()) {
            SearchFiles visitor = new SearchFiles(arg.exclude());
            Files.walkFileTree(arg.directory(), visitor);

            packFiles(visitor.getFiles(), arg.output().toFile());
        }
    }

    /**
     * Создание архива
     *
     * @param sources готовый список файлов для архивации
     * @param target  имя конечного архива
     */
    public void packFiles(final List<File> sources, final File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (int n = 0; n != sources.size(); ++n) {
                zip.putNextEntry(new ZipEntry(sources.get(n).getPath()));
                try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(sources.get(n)))) {
                    zip.write(out.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
