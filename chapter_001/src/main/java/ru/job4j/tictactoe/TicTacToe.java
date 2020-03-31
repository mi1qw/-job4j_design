package ru.job4j.tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

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


class Cell {
    public final int x;
    public final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

//Класс Figure3T - отвечает за клетку на поле.
//        Он содержит методы - имеет ли клетка крестик или нолик. или пустая.


class Figure3T {
    private boolean markX = false;
    private boolean markO = false;

    public Figure3T() {
    }

    public Figure3T(boolean markX, boolean markO) {
        this.markX = markX;
        this.markO = markO;
    }

    public void take(boolean markX) {
        this.markX = markX;
        this.markO = !markX;
    }

    public boolean hasMarkX() {
        return this.markX;
    }

    public boolean hasMarkO() {
        return this.markO;
    }
}

//отвечает за проверку логики. В этом задании тебе нужно до реализовать эти методы.


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
        //return this.fillBy(Figure3T::hasMarkX, 0, 0, 1, 0)
        //        || this.fillBy(Figure3T::hasMarkX, 0, 0, 0, 1)
        //        || this.fillBy(Figure3T::hasMarkX, 0, 0, 1, 1)
        //        || this.fillBy(Figure3T::hasMarkX, this.table.length - 1, 0, -1, 1);
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
        //return this.fillBy(Figure3T::hasMarkO, 0, 0, 1, 0)
        //        || this.fillBy(Figure3T::hasMarkO, 0, 0, 0, 1)
        //        || this.fillBy(Figure3T::hasMarkO, 0, 0, 1, 1)
        //        || this.fillBy(Figure3T::hasMarkO, this.table.length - 1, 0, -1, 1);
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
//Класс TicTacToe - реализует визуальный компонент. Не волнуйтесь, если вы не понимаете большинство кода в этом классе.
//        Дальше в курсе будет разобраны все эти элементы. Здесь просто скопируйте этот код.


public class TicTacToe {
    private static final String JOB4J = "Крестики-нолики www.job4j.ru";
    private final int size = 3;
    private final Figure3T[][] cells = new Figure3T[size][size];
    private final Logic3T logic = new Logic3T(cells);
    boolean mark;
    List<HumanBot> playerList = new ArrayList<>();

    //private Figure3T buildRectangle(int x, int y, int size) {
    //    Figure3T rect = new Figure3T();
    //    rect.setX(x * size);
    //    rect.setY(y * size);
    //    rect.setHeight(size);
    //    rect.setWidth(size);
    //    rect.setFill(Color.WHITE);
    //    rect.setStroke(Color.BLACK);
    //    return rect;
    //}
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
        //Group panel = new Group();
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

                //Figure3T rect = this.buildRectangle(x, y, 50);        построить поле
                //this.cells[y][x] = rect;
                //panel.getChildren().add(rect);
                //rect.setOnMouseClicked(this.buildMouseEvent(panel));
            }
            System.out.print("|");
            System.out.println();
        }
        //return panel;
    }

    //@Override
    //public void start(Stage stage) {
    //BorderPane border = new BorderPane();
    //HBox control = new HBox();
    //control.setPrefHeight(40);
    //control.setSpacing(10.0);-
    //control.setAlignment(Pos.BASELINE_CENTER);
    //Button start = new Button("Начать");
    //start.setOnMouseClicked(
    //        event -> border.setCenter(this.buildGrid())
    //);
    //control.getChildren().addAll(start);
    //border.setBottom(control);
    //border.setCenter(this.buildGrid());
    //stage.setScene(new Scene(border, 300, 300));
    //stage.setTitle(JOB4J);
    //stage.setResizable(false);
    //stage.show();
    //}

    public static void main(String[] args) throws IOException {
        new TicTacToe().start();
    }

    private void buildMarkO(double x, double y, int size) {
        System.out.print("O");
        //Group group = new Group();
        //int radius = size / 2;
        //Circle circle = new Circle(x + radius, y + radius, radius - 10);
        //circle.setStroke(Color.BLACK);
        //circle.setFill(Color.WHITE);
        //group.getChildren().add(circle);
        //return group;
    }

    private void buildMarkX(double x, double y, int size) {
        System.out.print("X");
        //Group group = new Group();
        //group.getChildren().addAll(
        //        new Line(
        //                x + 10, y + 10,
        //                x + size - 10, y + size - 10
        //        ),
        //        new Line(
        //                x + size - 10, y + 10,
        //                x + 10, y + size - 10
        //        )
        //);
        //return group;
    }

    //private void showAlert(String message) {
    //    Alert alert = new Alert(Alert.AlertType.WARNING);
    //    alert.setTitle(JOB4J);
    //    alert.setHeaderText(null);
    //    alert.setContentText(message);
    //    alert.showAndWait();
    //}

    private boolean checkState() {
        boolean gap = this.logic.hasGap();
        if (!gap) {
            //this.showAlert("Все поля запонены! Начните новую Игру!");
            System.out.println("Все поля запонены! Начните новую Игру!");
        }
        return gap;
    }

    public boolean checkWinner(String name) {
        boolean res = false;
        if (this.logic.isWinnerX()) {
            //this.showAlert("Победили Крестики! Начните новую Игру!");
            System.out.println("Победитель Крестики - " + name + "!\n\nНачните новую Игру");
            res = true;
        } else if (this.logic.isWinnerO()) {
            //this.showAlert("Победили Нолики! Начните новую Игру!");
            System.out.println("Победитель Нолики - " + name + "!\n\nНачните новую Игру");
            res = true;
        }
        return res;
    }

    //private EventHandler<MouseEvent> buildMouseEvent(Group panel) {
    //    return event -> {
    //        Figure3T rect = (Figure3T) event.getTarget();
    //        if (this.checkState()) {
    //            if (event.getButton() == MouseButton.PRIMARY) {
    //                rect.take(true);
    //                panel.getChildren().add(
    //                        this.buildMarkX(rect.getX(), rect.getY(), 50)
    //                );
    //            } else {
    //                rect.take(false);
    //                panel.getChildren().add(
    //                        this.buildMarkO(rect.getX(), rect.getY(), 50)
    //                );
    //            }
    //            this.checkWinner();
    //            this.checkState();
    //        }
    //    };
    //}
}

