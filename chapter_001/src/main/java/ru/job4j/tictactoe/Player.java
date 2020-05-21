package ru.job4j.tictactoe;

public class Player {
    protected String name;
    protected boolean mark;
    protected Logic3T logic;
    protected int[][] marks;

    protected Player(final String name, final boolean mark, final Logic3T logic) {
        this.name = name;
        this.mark = mark;
        this.logic = logic;
        marks = new int[logic.table.length][logic.table.length];
    }
}
