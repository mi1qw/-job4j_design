package ru.job4j.findfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Создание списка найденных файлов.
 *
 * @throws Exception выбрасывается при полном отсутствии ключей, проверка ключей делается отдельно.
 * Сначала проверяютсят все аргументы командной строки.
 * Только при успешной верификации начинается поиск и создание списка найденных файлов.
 */
public final class Findfile {
    public static void main(final String[] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalStateException("Usage java -jar dir.jar ROOT_FOLDER...");
        }
        new Findfile().findFile(args);
    }

    protected void findFile(final String[] args) throws IOException {
        ArgFindfile arg = new ArgFindfile(args);
        if (arg.valid()) {
            SearchFiles visitor = new SearchFiles(arg.getMatcher(), arg.getName());
            Files.walkFileTree(arg.getDirectory(), visitor);

            packFiles(visitor.getFiles(), arg.getOutput().toFile());
        }
    }

    /**
     * Создание файла с результами поиска
     *
     * @param sources готовый список файлов для архивации
     * @param target  имя конечного архива
     */
    public void packFiles(final List<Path> sources, final File target) {
        try (PrintWriter filelist = new PrintWriter(new FileOutputStream(target))) {
            sources.forEach(filelist::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

