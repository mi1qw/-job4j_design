package ru.job4j.tictactoe;

import java.io.IOException;

interface HumanBot {
    void inputXY() throws IOException;
}


class Player {
    String name;
    boolean mark;
    Logic3T logic;
    int[][] marks;

    public Player(String name, boolean mark, Logic3T logic) {
        this.name = name;
        this.mark = mark;
        this.logic = logic;
        marks = new int[logic.table.length][logic.table.length];
    }
}