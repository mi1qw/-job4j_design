package ru.job4j.simplearrayt;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleArray<T> implements Iterable<T>, ArrayInterf<T> {
    private final T[] array;
    private final int size;
    private int cell;
    private static final int DEFAULT_CAPACITY = 50;

    /**
     * Конструктор.
     * пустой, по умолчанию на 50 ячеек
     */
    public SimpleArray() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Конструктор.
     * Создаём Object и приводим его к T[] array
     *
     * @param size фиксированно, нединамическая коллекция
     */
    @SuppressWarnings("unchecked")
    public SimpleArray(final int size) {
        this.size = size;
        this.cell = -1;
        this.array = (T[]) new Object[size];
    }

    /**
     * Конструктор.
     * Инициализация готовым массивом T[] array
     *
     * @param array the array
     */
    public SimpleArray(final T[] array) {
        this.array = array.clone();
        this.size = array.length;
        this.cell = this.size - 1;
    }

    /**
     * @return the length
     */
    public int length() {
        return this.cell + 1;
    }

    /**
     * @return Generic Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int it = -1;

            @Override
            public boolean hasNext() {
                return it < cell;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return array[++it];
            }
        };
    }

    /**
     * Add Добавить элемент model в последнюю ячейку.
     *
     * @param model the model
     * @return the boolean успешного добавления
     */
    public boolean add(final Object model) {
        if (++this.cell == size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        //noinspection unchecked
        array[cell] = (T) model;
        return true;
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
    }

    /**
     * Remove. Удалить элемент со сдигом элементов справа на
     * единицу влево.
     *
     * @param index the index
     */
    public void remove(final int index) {
        Objects.checkIndex(index, this.size);
        System.arraycopy(array, index + 1, array, index, array.length - index - 1);
        set(cell--, null);
    }

    /**
     * Get Получить элемент T по ииндексу.
     *
     * @param index the index
     * @return the t
     */
    public T get(final int index) {
        Objects.checkIndex(index, this.size);
        return array[index];
    }

    /**
     * @return возвращаем все элементы массива, вместе с Null
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString()).append(" ");
        }
        return sb.toString();
    }
}
