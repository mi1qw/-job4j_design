package ru.job4j.argzip;

import java.nio.file.Path;
import java.nio.file.Paths;

interface ArgValidIn {
    <T> boolean valid(T t);
}


/**
 * Верификация аргументов строки.
 * Путь  Directory должен существовать и принадлежать только папке
 */
class ValidDirectory implements ArgValidIn {
    @Override
    public <T> boolean valid(final T t) {
        Path path = Paths.get((String) t);
        if (path.toFile().isFile()) {
            throw new IllegalStateException("1st argument must be a folder. Usage java -jar dir.jar ROOT_FOLDER.");
        } else if (!path.toFile().exists()) {
            throw new IllegalStateException("There is no such directory" + System.lineSeparator()
                    + "Usage java -jar dir.jar ROOT_FOLDER.");
        }
        return true;
    }
}


/**
 * Output - имя zip архива должен совпадать с регулярным выражением.
 */
class ValidOutput implements ArgValidIn {
    @Override
    public <T> boolean valid(final T t) {
        boolean res = false;
        String argKey = (String) t;
        if (argKey.matches("[^\\s]+\\.zip")) {
            res = true;
        } else {
            throw new IllegalStateException("Wrong name for ZIP archive");
        }
        return res;
    }
}


/**
 * пустой метод, для аргументов не требующих проверки.
 * проверка на пустой аргумент
 */
class ValidOFF implements ArgValidIn {
    @Override
    public <T> boolean valid(final T t) {
        boolean res = false;
        String argKey = (String) t;
        if (!argKey.isEmpty()) {
            res = true;
        } else {
            throw new IllegalStateException(System.lineSeparator() + "Wrong argument " + argKey);
        }
        return res;
    }
}
