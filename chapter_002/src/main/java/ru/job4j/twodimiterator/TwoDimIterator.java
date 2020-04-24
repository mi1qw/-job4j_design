package ru.job4j.twodimiterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TwoDimIterator implements Iterator {
    private int indexRow = -1;
    private int indexCol = -1;
    private int size;
    private int[][] array;

    public TwoDimIterator(final int[][] array) {
        this.array = array;
        this.size = array.length;
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
        boolean res = false;
        if ((indexCol + 1) < (array[indexRow + 1].length) && indexRow < size - 1) {
            res = true;
        } else if (indexRow + 1 < size - 1) {
            res = true;
        }
        return res;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Integer next() {
        if (++indexCol == array[indexRow + 1].length) {
            indexCol = 0;
            if (++indexRow == size - 1) {
                throw new NoSuchElementException("End of array");
            }
        }
        return array[indexRow + 1][indexCol];
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
    public TwoDimIterator reset() {
        indexRow = -1;
        indexCol = -1;
        return this;
    }

    public static void main(final String[] args) {

        int[][] array = {{1}, {2, 3, 4, 5}, {6, 7}, {8, 9, 10, 11, 12, 13, 14}};
        TwoDimIterator it = new TwoDimIterator(array);
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        System.out.println();

        it.reset().display();
    }
}
