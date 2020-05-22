package ru.job4j.simpleset;

import java.util.Iterator;

public interface SetInterf<E> {
    /**
     * Remove. Удалить элемент со сдигом элементов справа на
     * единицу влево.
     *
     * @param o удаляемый элемент
     * @return вернуть true если удалось удалить
     */
    boolean remove(E o);

    /**
     * Вариант add для SET.
     * исклють добавление дублирующихся элементов
     *
     * @param e the элемент
     * @return true при успешном добавлении элемента
     */
    boolean add(E e);

    /**
     * @return Generic Iterator.
     */
    Iterator<E> iterator();

    /**
     * @return возвращаем все элементы массива.
     */
    String toString();

    /**
     * @return the length
     */
    int size();

    /**
     * Поиск элемента в коллекции.
     *
     * @param o the искомый элемент E
     * @return true если такой элемент есть
     */
    boolean contains(E o);
}