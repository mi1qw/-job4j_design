package ru.job4j.tictactoe;

class Bot extends Player implements HumanBot {
    public Bot(String name, boolean mark, Logic3T logic) {
        super(name, mark, logic);
    }

    @Override
    public void inputXY() {
        int x, y;
        System.out.print(this.name + ", entered CELL xy: ");
        do {
            x = (int) (Math.random() * logic.table.length);
            y = (int) (Math.random() * logic.table.length);
        } while (logic.isNotFree(x, y));
        System.out.println((int) (x + 1) + "" + (int) (y + 1));

        logic.table[x][y].take(this.mark);
    }
}
