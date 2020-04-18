package ru.job4j.findfile;

import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Создание списка найденных файлов.
 *
 * @throws Exception выбрасывается при полном отсутствии ключей, проверка ключей делается отдельно.
 * Сначала проверяютсят все аргументы командной строки.
 * Только при успешной верификации начинается поиск и создание списка найденных файлов.
 */
public final class Findfile {
    public static void main(final String[] args) {
        if (args.length == 0) {
            throw new IllegalStateException("Usage java -jar dir.jar ROOT_FOLDER...");
        }
        new Findfile().go(args);
    }

    void go(final String[] args) throws Wrongkey, UseKeyDEO {
        ArgFindfile arg = new ArgFindfile(args);
        if (arg.valid()) {
            try (PrintWriter filelist =
                         new PrintWriter(new FileOutputStream(arg.getOutput().toFile()))) {
                Files visitor = new Files(arg.getMatcher(), filelist);
                visitor.setTypeFile(arg.getName());

                java.nio.file.Files.walkFileTree(arg.getDirectory(), visitor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
