package ru.job4j.tictactoe;

public class Figure3T {
    private boolean markX = false;
    private boolean markO = false;

    public Figure3T() {
    }

    public Figure3T(final boolean markX, final boolean markO) {
        this.markX = markX;
        this.markO = markO;
    }

    public final void take(final boolean markX) {
        this.markX = markX;
        this.markO = !markX;
    }

    public final boolean hasMarkX() {
        return this.markX;
    }

    public final boolean hasMarkO() {
        return this.markO;
    }
}
