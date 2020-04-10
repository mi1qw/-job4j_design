package ru.job4j.argzip;

import java.nio.file.Path;
import java.nio.file.Paths;

interface ArgValidIn {
    boolean valid(ArgKey argKey);
}


/**
 * Верификация аргументов строки
 * Путь  Directory должен существовать и принадлежать только папке
 */
class ValidDirectory implements ArgValidIn {
    @Override
    public boolean valid(ArgKey argKey) {
        boolean res = false;
        Path path = Paths.get(argKey.data.get(0));
        if (path.toFile().isFile()) {
            throw new IllegalStateException("1st argument must be a folder. Usage java -jar dir.jar ROOT_FOLDER.");
        }
        if (!path.toFile().exists()) {
            throw new IllegalStateException("There is no such directory" + System.lineSeparator()
                    + "Usage java -jar dir.jar ROOT_FOLDER.");
        }
        res = true;
        return res;
    }
}


/**
 * Output - имя zip архива должен совпадать с регулярным выражением
 */
class ValidOutput implements ArgValidIn {
    @Override
    public boolean valid(ArgKey argKey) {
        boolean res = false;
        if (argKey.data.get(0).matches("[^\\s]+\\.zip")) {
            res = true;
        } else {
            throw new IllegalStateException("Wrong name for ZIP archive");
        }
        return res;
    }
}


/**
 * пустой метод, для аргументов не требующих проверки
 * проверка на пустой аргумент
 */
class ValidOFF implements ArgValidIn {
    @Override
    public boolean valid(ArgKey argKey) {
        boolean res = false;
        if (argKey.data.size() != 0) {
            res = true;
        } else {
            throw new IllegalStateException(System.lineSeparator() + "Wrong argument " + argKey.key);
        }
        return res;
    }
}