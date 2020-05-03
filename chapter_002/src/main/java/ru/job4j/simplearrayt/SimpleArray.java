package ru.job4j.simplearrayt;

import java.util.Iterator;
import java.util.Objects;

public class SimpleArray<T> implements Iterable<T> {
    private final T[] array;
    private final int size;
    @SuppressWarnings("CheckStyle")
    public int length;

    /**
     * Конструктор.
     * Создаём Object и приводим его к T[] array
     *
     * @param size фиксированно, нединамическая коллекция
     */
    @SuppressWarnings("unchecked")
    public SimpleArray(final int size) {
        this.size = size;
        this.length = 0;
        this.array = (T[]) new Object[size];
    }

    /**
     * Конструктор.
     * Инициализация готовым массивом T[] array
     *
     * @param array the array
     */
    public SimpleArray(final T[] array) {
        this.array = array;
        this.size = array.length;
        this.length = getLength(array);
    }

    /**
     * Gets length- кол-во элементов в масиве без null.
     *
     * @param array the array
     * @return the length
     */
    private int getLength(final T[] array) {
        int length = 0;
        for (T n : array) {
            if (n != null) {
                ++length;
            }
        }
        return length;
    }

    /**
     * @return Generic Iterator.
     */
    final @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int it = 0;

            @Override
            public boolean hasNext() {
                while (it < size && array[it] == null) {
                    ++it;
                }
                return it < size;
            }

            @Override
            public T next() {
                return array[it++];
            }
        };
    }

    /**
     * Add Добавить элемент model в первую пусту null ячейку.
     *
     * @param model the model
     * @return the int позиция куда был вставлен элемент
     * @throws ArrayIndexOutOfBoundsException the array index out of bounds exception
     */
    public int add(final T model) throws ArrayIndexOutOfBoundsException {
        int m = -1;
        for (int n = 0; n < this.size; ++n) {
            if (array[n] == null) {
                array[n] = model;
                ++this.length;
                m = n;
                break;
            }
        }
        if (m == -1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return m;
    }

    /**
     * Назначить элементу массива значение.
     *
     * @param index the index
     * @param model the model
     */
    public void set(final int index, final T model) {
        Objects.checkIndex(index, this.size);
        array[index] = model;
        if (model == null) {
            --this.length;
        }
    }

    /**
     * Remove. Удалить элемент со сдигом элементов справа на
     * единицу влево.
     *
     * @param index the index
     * @throws ArrayIndexOutOfBoundsException the array index out of bounds exception
     */
    public void remove(final int index) throws ArrayIndexOutOfBoundsException {
        Objects.checkIndex(index, this.size);
        for (int n = index; n < this.size - 1; ++n) {
            array[n] = array[n + 1];
        }
        array[size - 1] = null;
        this.length--;
    }

    /**
     * Get Получить элемент T по ииндексу
     *
     * @param index the index
     * @return the t
     */
    public T get(final int index) {
        Objects.checkIndex(index, this.size);
        return array[index];
    }

    /**
     * Вывести в консоль полностью массив со всеми пустыми ячейками.
     */
    public void dispSizeArray() {
        for (int n = 0; n < this.size; ++n) {
            System.out.print(this.array[n] + " ");
        }
        System.out.println();
    }

    /**
     * Вывести в консоль полностью массив без пустых null ячеек.
     */
    public void dispIterator() {
        Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();
    }
}
