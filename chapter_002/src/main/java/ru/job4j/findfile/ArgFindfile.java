package ru.job4j.findfile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code ArgFindfile} класс проверки командной строки.
 */
class ArgFindfile {
    /**
     * @param valid - флаг верификации командной строки
     * @param numcons - минимальное кол-во обязательных аргументов
     * @param Map<String ArgKey> param коллекция всех допустимых ключей, тип ArgKey
     * @param args список аргументов командной строки
     * @param directory диретория поиска
     * @param name имя файла или паттерн поиска
     * @param output выходной файл для результатов поиска
     * @param matchext ссылка на класс {@code Matchext} реализаций сравнений файлов/паттернов
     * @param matcher ссылка на фунциональный методов сравнения
     */
    private boolean valid = false;
    private static final int NUMCONS = 3;
    private final Map<String, ArgKey> param = new HashMap<>();
    private final String[] args;
    private final List<String> directory = new ArrayList<>();
    private final List<String> name = new ArrayList<>();
    private final List<String> output = new ArrayList<>();
    private final Matchext matchext = new Matchext();
    private ArgFuncIn matcher = matchext::matchM;

    protected ArgFindfile(final String[] args) {
        this.args = args;
        param.put("-d", new ArgKey("-d", " Specify the directory in which to start the search",
                directory, true, new ValidDirectory()));
        param.put("-n", new ArgKey("-n", " file name, mask, or regular expression",
                name, true, new ValidOFF()));
        param.put("-m", new ArgKey("-m", " search by mask",
                List.of("-1"), false, matchext::matchM));
        param.put("-f", new ArgKey("-f", " full name match",
                List.of("-1"), false, matchext::matchF));
        param.put("-r", new ArgKey("-r", " regular expression",
                List.of("-1"), false, matchext::matchR));
        param.put("-o", new ArgKey("-o", " save found files to file",
                output, true, new ValidOutput()));
    }

    protected boolean valid() throws Wrongkey, UseKeyDEO {
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
                    if (data != null && data.func.valid(args[n])) {
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
                setMatcher();
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
                System.out.println(e.arg + " Wrong key, No such key. Use this keys");
                param.forEach((n, m) -> {
                    System.out.print(String.format("%s %s.%n", m.key, m.text));
                });
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
            if (n.getValue().cons && !n.getValue().data.isEmpty()) {
                ++num;
            }
        }
        return num < NUMCONS;
    }

    /**
     * Определить по ключу выбранный способ сравнения имени файла/паттерна.
     */
    private void setMatcher() {
        for (Map.Entry<String, ArgKey> n : param.entrySet()) {
            if (n.getValue().data.get(0).equals("-1") && n.getValue().num > 0) {
                this.matcher = n.getValue().func;
                break;
            }
        }
    }

    /**
     * @return возвращает ссылку на метод нужного способа сравнения.
     */
    protected ArgFuncIn getMatcher() {
        return this.matcher;
    }

    /**
     * Предполагается что в поле {@code data - directory} только одно значение поэтому
     * получаем его при помощи {@code get(0)}
     *
     * @return the path
     */
    protected Path getDirectory() {
        return Paths.get(this.directory.get(0));
    }

    /**
     * Возвращаем целиком list, так как могут быть нескольлко значений/типов файлов/паттернов.
     *
     * @return the list
     */
    protected List<String> getName() {
        return this.name;
    }

    /**
     * Предполагается что в поле {@code data - output} только одно значение поэтому
     * получаем его  при помощи {@code get(0)}
     *
     * @return the path
     */
    protected Path getOutput() {
        return Paths.get(this.output.get(0));
    }
}


