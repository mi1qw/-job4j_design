package ru.job4j.findfile;

import java.util.List;

/**
 * Класс {@code ArgKey} для аргумента командной строки.
 */
class ArgKey {
    /**
     * @param key   ключ вида -d, -o и т.д
     * @param text  описание ключа, используется при выводе ошибок/запросов
     * @param data  поле {@code data} данные следующие после конкретного ключа, путь или ещё что
     * @param cons  логическое True указывает на обязательное присутсиве ключа в командной строке
     * @param func ссылка на интерфейсный метод проверяющий конкретный ключ или ссылка на метод сравнения
     * @param num   показывает вводился ли вообще аргумент.
     * Для ключа "расширение" их может быть несколько (*.jpg, *.zip)
     * Участвует при проверке наличия обязательных аргументов в строке, таких как
     * - путь исследуемой паки
     * - имя конечного файла для найденных результатов
     */
    protected final String key;
    protected final String text;
    protected final List<String> data;
    protected final boolean cons;
    protected final ArgFuncIn func;
    protected int num = 0;

    ArgKey(final String key, final String text, final List<String> data, final boolean cons, final ArgFuncIn func) {
        this.key = key;
        this.text = text;
        this.data = data;
        this.cons = cons;
        this.func = func;
    }
}
