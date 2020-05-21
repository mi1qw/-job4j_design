package ru.job4j.simplearrayt;

import java.util.Iterator;

public interface ArrayInterf<E> {
    /**
     * Вариант add для SET.
     * исклють добавление дублирующихся элементов
     *
     * @param model the элемент
     * @return true при успешном добавлении элемента
     */
    boolean add(E model);

    /**
     * @return Generic Iterator.
     */
    Iterator<E> iterator();

    /**
     * @return the length
     */
    int length();

    /**
     * Get Получить элемент T по ииндексу.
     *
     * @param index the index
     * @return the t
     */
    E get(int index);

    /**
     * Remove. Удалить элемент со сдигом элементов справа на
     * единицу влево.
     *
     * @param index the index
     */
    void remove(int index);

    /**
     * @return возвращаем все элементы массива, вместе с Null
     */
    String toString();
}
