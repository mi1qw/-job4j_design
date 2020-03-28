package ru.job4j.tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//    |X|O|X|
//    |O|X|O|
//    |X|O|X|
//
//    |O|O|X|O|O|
//    |X|X|X|X|X|
//    |O|O|X|O|O|
//    |O|O|X|O|O|
//    |O|O|X|O|O|
//


public class TicTacToe {
    private static final String JOB4J = "Крестики-нолики www.job4j.ru";
    private final int size = 3;
    private final Figure3T[][] cells = new Figure3T[size][size];
    private final Logic3T logic = new Logic3T(cells);
    boolean mark;
    List<HumanBot> playerList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        new TicTacToe().start();
    }

    public void start() throws IOException {
        while (true) {
            for (int y = 0; y != this.size; y++) {
                for (int x = 0; x != this.size; x++) {
                    cells[x][y] = new Figure3T();
                }
            }
            mark = (int) (1 + Math.random() * 2) == 1 ? true : false;   // если true, то markX
            playerList.clear();

            switch (menu()) {
                case 1:
                    playerList.add(new Human("Jek", mark, logic));
                    playerList.add(new Bot("Bot", !mark, logic));
                    break;
                case 2:
                    playerList.add(new Human("Jek", mark, logic));
                    playerList.add(new Human("Ann", !mark, logic));
                    break;
                case 3:
                    playerList.add(new Bot("Bot_1", mark, logic));
                    playerList.add(new Bot("Bot_2", !mark, logic));
                default:
            }

            int n = -1;
            while (checkState()) {
                n = (++n) % playerList.size();
                playerList.get(n).inputXY();
                buildGrid();
                System.out.println();
                if (checkWinner(((Player) playerList.get(n)).name)) {
                    break;
                }
            }
        }
    }

    int menu() throws IOException {
        System.out.println();
        System.out.println("Варианты игры:");
        System.out.println("1 бот-человек");
        System.out.println("2 человек-человек");
        System.out.println("3 бот-бот");
        System.out.println();
        System.out.print("Cделайте выбор - ");

        boolean valid = true;
        int n = 0;
        do {
            try {
                String input = getString();
                n = Integer.parseInt(String.valueOf(input));
                if (n > 0 && n < 4) {
                    valid = false;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.print("Cделайте выбор - ");
            }
        } while (valid);
        return n;
    }

    String getString() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }

    private void buildGrid() {
        for (int y = 0; y != this.size; y++) {
            for (int x = 0; x != this.size; x++) {
                System.out.print("|");
                if (cells[x][y].hasMarkX()) {
                    System.out.print("X");
                    continue;
                }
                if (cells[x][y].hasMarkO()) {
                    System.out.print("O");
                    continue;
                }
                System.out.print(" ");
            }
            System.out.print("|");
            System.out.println();
        }
    }

    private boolean checkState() {
        boolean gap = this.logic.hasGap();
        if (!gap) {
            System.out.println("Все поля запонены! Начните новую Игру!");
        }
        return gap;
    }

    public boolean checkWinner(String name) {
        boolean res = false;
        if (this.logic.isWinnerX()) {
            System.out.println("Победитель Крестики - " + name + "!\n\nНачните новую Игру");
            res = true;
        } else if (this.logic.isWinnerO()) {
            System.out.println("Победитель Нолики - " + name + "!\n\nНачните новую Игру");
            res = true;
        }
        return res;
    }
}

