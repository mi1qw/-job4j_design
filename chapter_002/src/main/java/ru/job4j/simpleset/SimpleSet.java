package ru.job4j.simpleset;

import ru.job4j.simplearrayt.SimpleArray;

import java.util.Iterator;

public class SimpleSet<E> extends ForwardingSet<E> {
    /**
     * Конструктор декоратор.
     *
     * @param array new array
     */
    public SimpleSet(final SimpleArray<E> array) {
        super(array);
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
        if (length() == 0 || !contains(e)) {
            return super.add(e);
        }
        return false;
    }

    /**
     * Remove. Удалить элемент со сдигом элементов справа на
     * единицу влево.
     *
     * @param index the index
     */
    @Override
    public void remove(final int index) {
        super.remove(index);
    }

    /**
     * @return Generic Iterator.
     */
    @Override
    public Iterator<E> iterator() {
        return super.iterator();
    }

    /**
     * @return возвращаем все элементы массива, вместе с Null
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * @return the length
     */
    @Override
    public int length() {
        return super.length();
    }

    /**
     * Get Получить элемент T по ииндексу.
     *
     * @param index the index
     * @return the t
     */
    @Override
    public E get(final int index) {
        return super.get(index);
    }

    /**
     * Поиск элемента в коллекции.
     *
     * @param o the искомый элемент E
     * @return true если такой элемент есть
     */
    public boolean contains(final E o) {
        int length = length();
        for (int n = 0; n != length; ++n) {
            if (o.equals(get(n))) {
                return true;
            }
        }
        return false;
    }
}
