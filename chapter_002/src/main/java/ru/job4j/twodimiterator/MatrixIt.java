package ru.job4j.twodimiterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIt implements Iterator {
    private int row = 0;
    private int col = 0;
    private int size;
    private int[][] array;

    public MatrixIt(final int[][] array) {
        this.array = array;
        this.size = array.length;
    }

    public static void main(final String[] args) {

        int[][] array = {{1}, {2, 3, 4, 5}, {6, 7}, {8, 9, 10, 11, 12, 13, 14}};
        MatrixIt it = new MatrixIt(array);
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        System.out.println();

        it.reset().display();
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        while (row < size && array[row].length == 0) {
            ++row;
        }
        return row < size && col < array[row].length;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException("End of array");
        }
        Integer i = array[row][col];
        if (++col == array[row].length) {
            col = 0;
            ++row;
        }
        return i;
    }

    /**
     * Display. Вывод в консоль содержимого массива.
     */
    public void display() {
        while (hasNext()) {
            System.out.println(next());
        }
    }

    /**
     * Reset Сброс указателей итератора в начальное положение.
     *
     * @return the two dim iterator
     */
    public MatrixIt reset() {
        row = 0;
        col = 0;
        return this;
    }
}
