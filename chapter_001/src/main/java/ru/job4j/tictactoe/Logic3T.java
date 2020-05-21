package ru.job4j.tictactoe;

import java.util.function.Predicate;

public class Logic3T {
    protected final Figure3T[][] table;

    public Logic3T(final Figure3T[][] table) {
        this.table = table;
    }

    final boolean isNotFree(final int x, final int y) {
        return table[x][y].hasMarkX() || table[x][y].hasMarkO();
    }

    public final boolean fillBy(final Predicate<Figure3T> predicate, int startX, int startY, final int deltaX,
                                final int deltaY) {
        boolean result = true;
        for (int index = 0; index != this.table.length; index++) {
            Figure3T cell = this.table[startX][startY];
            startX += deltaX;
            startY += deltaY;
            if (!predicate.test(cell)) {
                result = false;
                break;
            }
        }
        return result;
    }

    public final boolean isWinnerX() {
        boolean res;
        res = this.fillBy(Figure3T::hasMarkX, this.table.length - 1, 0, -1, 1);
        res |= this.fillBy(Figure3T::hasMarkX, 0, 0, 1, 1);
        for (int x = 0; x != table.length; ++x) {
            res |= this.fillBy(Figure3T::hasMarkX, x, 0, 0, 1);
        }
        for (int y = 0; y != table.length; ++y) {
            res |= this.fillBy(Figure3T::hasMarkX, 0, y, 1, 0);
        }
        return res;
    }

    public final boolean isWinnerO() {
        boolean res;
        res = this.fillBy(Figure3T::hasMarkO, this.table.length - 1, 0, -1, 1);
        res |= this.fillBy(Figure3T::hasMarkO, 0, 0, 1, 1);
        for (int x = 0; x != table.length; ++x) {
            res |= this.fillBy(Figure3T::hasMarkO, x, 0, 0, 1);
        }
        for (int y = 0; y != table.length; ++y) {
            res |= this.fillBy(Figure3T::hasMarkO, 0, y, 1, 0);
        }
        return res;
    }

    public final boolean hasGap() {
        for (int y = 0; y != this.table.length; y++) {
            for (int x = 0; x != this.table.length; x++) {
                if (!isNotFree(x, y)) {
                    return true;
                }
            }
        }
        return false;
    }
}
