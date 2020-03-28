package ru.job4j.tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Human extends Player implements HumanBot {
    public Human(String name, boolean mark, Logic3T logic) {
        super(name, mark, logic);
    }

    @Override
    public void inputXY() throws IOException {
        int x = 0;
        int y = 0;
        boolean valid = true;
        String input;
        do {
            try {
                System.out.print(this.name + ", Enter CELL xy: ");
                System.out.flush();
                input = getString();                // Чтение строки с клавиатуры
                x = Integer.parseInt(String.valueOf(input.charAt(0))) - 1;
                y = Integer.parseInt(String.valueOf(input.charAt(1))) - 1;
                if (x >= 0 && x < logic.table.length && y >= 0 && y < logic.table.length) {
                    valid = false;
                }
            } catch (NumberFormatException e) {
            } catch (StringIndexOutOfBoundsException e) {
            }
        } while (valid || logic.isNotFree(x, y));

        logic.table[x][y].take(this.mark);
    }

    String getString() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }
}
