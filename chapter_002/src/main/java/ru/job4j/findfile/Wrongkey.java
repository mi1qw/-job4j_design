package ru.job4j.findfile;

import java.util.Map;

/**
 * Класс исключения
 * {@code Wrongkey}  указывает на неправильный вид ключа.
 * Ошибка воззникает при отсутсвии в наборе ключей {@code Map<String, ArgKey> param}
 */
class Wrongkey extends RuntimeException {
    String arg;

    /**
     * @param arg ошибочный ключ
     */
    Wrongkey(final String arg) {
        this.arg = arg;
    }
}


/**
 * Класс исключения
 * {@code UseKeyDEO}  указывает на отсутствие обязательных ключей в командной строке.
 * Ообязательные ключи - те с которыми код так или иначе отработает.
 */
class UseKeyDEO extends RuntimeException {
    Map<String, ArgKey> param;

    /**
     * @param param Набор всех ключей командной строки
     *              для вывода текста- подсказки обязательных ключей в консоль
     */
    UseKeyDEO(final Map<String, ArgKey> param) {
        this.param = param;
    }
}
