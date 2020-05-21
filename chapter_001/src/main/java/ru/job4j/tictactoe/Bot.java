package ru.job4j.tictactoe;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Bot extends Player implements HumanBot {
    public Bot(final String name, final boolean mark, final Logic3T logic) {
        super(name, mark, logic);
    }

    @Override
    public final void inputXY() throws NoSuchAlgorithmException {
        int x;
        int y;
        Random rand = SecureRandom.getInstanceStrong();
        System.out.print(this.name + ", entered CELL xy: ");
        do {
            x = rand.nextInt(logic.table.length);
            y = rand.nextInt(logic.table.length);
        } while (logic.isNotFree(x, y));
        System.out.println((x + 1) + "" + (y + 1));

        logic.table[x][y].take(this.mark);
    }
}
