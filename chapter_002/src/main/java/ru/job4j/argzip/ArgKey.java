package ru.job4j.argzip;

import java.util.List;

/**
 * Класс {@code ArgKey} для аргумента командной строки
 */
class ArgKey {
    /**
     * @param key   ключ вида -d, -o и т.д
     * @param text  описание ключа, используется при выводе ошибок/запросов
     * @param data  поле {@code data} данные следующие после конкретного ключа, путь или ещё что
     * @param cons  логическое True указывает на обязательное присутсиве ключа в командной строке
     * @param valid ссылка на интерфейсный метод проверяющий конкретный ключ
     */
    public ArgKey(String key, String text, List<String> data, boolean cons, ArgValidIn valid) {
        this.key = key;
        this.text = text;
        this.data = data;
        this.cons = cons;
        this.valid = valid;
    }

    /**
     * @param num  показывает вводился ли вообще аргумент и сколько их. Для ключа "расширение"
     * их может быть несколько (*.jpg, *.zip)
     * Участвует при проверке наличия обязательных аргументов в строке, таких как
     * - путь архивируемой паки
     * - имя конечного архива
     */
    String key;
    String text;
    List<String> data;
    final boolean cons;
    ArgValidIn valid;
    int num = 0;
}

