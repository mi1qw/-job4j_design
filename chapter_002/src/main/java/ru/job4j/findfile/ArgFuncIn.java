package ru.job4j.findfile;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ссылка на интерфейс, так же выступает в роли фунционального для ссылок на метод.
 */
interface ArgFuncIn {
    <T> boolean valid(T t);
}


/**
 * Верификация аргументов строки.
 * Путь  Directory должен существовать и принадлежать только папке
 */
class ValidDirectory implements ArgFuncIn {
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
 * Output - имя log отчёта должен совпадать с регулярным выражением или чем-то необходимым.
 */
class ValidOutput implements ArgFuncIn {
    @Override
    public <T> boolean valid(final T t) {
        boolean res = false;
        String argKey = (String) t;
        if (argKey.matches("[^\\s]+\\.txt")) {
            res = true;
        } else {
            throw new IllegalStateException("Wrong name for *.txt file");
        }
        return res;
    }
}


/**
 * пустой метод, для аргументов не требующих проверки.
 * проверка на пустой аргумент
 */
class ValidOFF implements ArgFuncIn {
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
