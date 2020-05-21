package ru.job4j.simpleset;

import ru.job4j.simplearrayt.ArrayInterf;
import ru.job4j.simplearrayt.SimpleArray;

import java.util.Iterator;

public class ForwardingSet<E> implements ArrayInterf<E> {
    private final ArrayInterf<E> array;

    public ForwardingSet(final SimpleArray<E> array) {
        //noinspection unchecked
        this.array = array;
    }

    /**
     * Вариант add для SET.
     * исклють добавление дублирующихся элементов
     *
     * @param e элемент
     * @return true при успешном добавлении элемента
     */
    public boolean add(final E e) {
        return array.add(e);
    }

    /**
     * Remove. Удалить элемент со сдигом элементов справа на
     * единицу влево.
     *
     * @param index the index
     */
    public void remove(final int index) {
        array.remove(index);
    }

    /**
     * @return Generic Iterator.
     */
    @Override
    public Iterator<E> iterator() {
        return array.iterator();
    }

    /**
     * @return the length
     */
    @Override
    public int length() {
        return array.length();
    }

    /**
     * Get Получить элемент T по ииндексу.
     *
     * @param index the index
     * @return the t
     */
    @Override
    public E get(final int index) {
        return array.get(index);
    }

    /**
     * @return возвращаем все элементы массива, вместе с Null
     */
    @Override
    public String toString() {
        return array.toString();
    }
}
