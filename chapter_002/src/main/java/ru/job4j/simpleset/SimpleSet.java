package ru.job4j.simpleset;

import ru.job4j.simplearrayt.ArrayInterf;
import ru.job4j.simplearrayt.SimpleArray;

import java.util.Iterator;

public class SimpleSet<E> implements SetInterf<E> {
    private final ArrayInterf<E> array;
    private int index;

    /**
     * Конструктор.
     * пустой, по умолчанию на 50 ячеек
     */
    public SimpleSet() {
        this.array = new SimpleArray<>();
    }

    /**
     * Конструктор.
     * с заданным колличеством элементов
     *
     * @param size фиксированно, нединамическая коллекция
     */
    public SimpleSet(final int size) {
        this.array = new SimpleArray<>(size);
    }

    /**
     * Конструктор.
     * Инициализация готовым массивом Е[] array
     *
     * @param sarray the array
     */
    public SimpleSet(final E[] sarray) {
        this.array = new SimpleArray<>(sarray);
    }

    /**
     * Remove. Удалить элемент со сдигом элементов справа на
     * единицу влево.
     *
     * @param o удаляемый элемент
     * @return вернуть true если удалось удалить
     */
    @Override
    public boolean remove(final E o) {
        if (contains(o)) {
            array.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Вариант add для SET.
     * исклють добавление дублирующихся элементов
     *
     * @param e the элемент
     * @return true при успешном добавлении элемента
     */
    @Override
    public boolean add(final E e) {
        if (size() == 0 || !contains(e)) {
            return array.add(e);
        }
        return false;
    }

    /**
     * @return Generic Iterator.
     */
    @Override
    public Iterator<E> iterator() {
        return array.iterator();
    }

    /**
     * @return возвращаем все элементы массива.
     */
    @Override
    public String toString() {
        return array.toString();
    }

    /**
     * @return the length
     */
    @Override
    public int size() {
        return array.length();
    }

    /**
     * Поиск элемента в коллекции.
     *
     * @param o the искомый элемент E
     * @return true если такой элемент есть
     */
    @Override
    public boolean contains(final E o) {
        int length = size();
        for (int n = 0; n != length; ++n) {
            if (o.equals(array.get(n))) {
                this.index = n;
                return true;
            }
        }
        return false;
    }
}
