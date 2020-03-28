package ru.job4j.tictactoe;

import java.util.function.Predicate;

class Logic3T {
    final Figure3T[][] table;

    public Logic3T(Figure3T[][] table) {
        this.table = table;
    }

    boolean isNotFree(int x, int y) {
        return table[x][y].hasMarkX() || table[x][y].hasMarkO();
    }

    public boolean fillBy(Predicate<Figure3T> predicate, int startX, int startY, int deltaX, int deltaY) {
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

    public boolean isWinnerX() {
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

    public boolean isWinnerO() {
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

    public boolean hasGap() {
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
