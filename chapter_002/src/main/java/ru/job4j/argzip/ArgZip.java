package ru.job4j.argzip;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code ArgZip} класс для проверки командной строки.
 */
class ArgZip {
    /**
     * @param numcons - минимальное кол-во обязательных аргументов
     * @param valid - флаг верификации командной строки
     * @param Map<String, ArgKey> param коллекция всех допустимых ключей, тип ArgKey
     */
    private boolean valid = false;
    private static final int NUMCONS = 2;
    private final Map<String, ArgKey> param = new HashMap<>();
    private final String[] args;
    private final List<String> directory = new ArrayList<>();
    private final List<String> exclude = new ArrayList<>();
    private final List<String> output = new ArrayList<>();

    protected ArgZip(final String[] args) {
        this.args = args;
        param.put("-d", new ArgKey("-d", " Specify a path to a directory wich must be ziped",
                directory, true, new ValidDirectory()));
        param.put("-e", new ArgKey("-e", " exclude files",
                exclude, false, new ValidOFF()));
        param.put("-o", new ArgKey("-o", " to choose the desired location (drive/folder) to save the ZIP",
                output, true, new ValidOutput()));
    }

    protected boolean valid() {
        if (!this.valid) {
            try {
                ArgKey data = null;
                for (int n = 0; n != this.args.length; ++n) {
                    if (args[n].startsWith("-")) {
                        if (param.containsKey(args[n])) {
                            data = param.get(args[n]);
                            data.num += 1;
                            continue;
                        } else {
                            throw new Wrongkey(args[n]);        // неправильный ключ в командной строке
                        }
                    }
                    if (data != null && data.valid.valid(args[n])) {
                        data.data.add(args[n]);
                    } else {
                        data.num = 0;
                    }
                }
                if (complexValid()) {
                    throw new UseKeyDEO(param);               // не все обязательные ключи использованы
                } else {
                    this.valid = true;
                }
            } catch (UseKeyDEO e) {
                System.out.print(" Error, use this Key:");
                System.out.println();
                e.param.forEach((n, n1) -> {                  // выводит все невирифицированные обязательные ключи
                    if (n1.cons && n1.data.size() == 0) {                   // + описание каждого ключа
                        System.out.print(n + n1.text);
                    }
                });
                System.out.println();
                e.printStackTrace();
                throw e;
            } catch (Wrongkey e) {
                System.out.println(e.arg + " Wrong key, No such key");
                e.printStackTrace();
                throw e;
            }
        }
        return this.valid;
    }

    /**
     * проверяет наличие всех обязательных аргументов
     * у каждого ключа есть поле "cons" обязательный ключ или нет.
     * num - означает фактически прошедший верификацию аргумент, возможно проще использовать boolean
     * пока что int кол-во.
     *
     * @return Колличество верифицированных аргументов должно быть не меньше кол-ва обязательных аргументов
     */
    private boolean complexValid() {
        int num = 0;
        for (Map.Entry<String, ArgKey> n : param.entrySet()) {
            if (n.getValue().cons) {
                num += n.getValue().data.size();
            }
        }
        return num < NUMCONS;
    }

    /**
     * Предполагается что в поле {@code data - directory} только одно значение поэтому
     * получаем его  при помощи {@code get(0)}
     *
     * @return the path
     */
    protected Path directory() {
        return Paths.get(this.directory.get(0));
    }

    /**
     * Возвращаем целиком list, так как могут быть нескольлко значений/типов файловExclude.
     *
     * @return the list
     */
    protected List<String> exclude() {
        return this.exclude;
    }

    /**
     * Предполагается что в поле {@code data - output} только одно значение поэтому
     * получаем его  при помощи {@code get(0)}
     *
     * @return the path
     */
    protected Path output() {
        return Paths.get(this.output.get(0));
    }
}


