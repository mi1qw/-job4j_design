package ru.job4j.tictactoe;

import java.util.Objects;
import java.util.function.Predicate;

//  public boolean isWinnerX() -проверяет есть ли в поле выигрышные комбинации для Крестика.
//  public boolean isWinnerO()-проверяет есть ли в поле выигрышные комбинации для Нолика.
//  public boolean hasGap()-проверяет,если ли пустые клетки для новых ходов.
//
//  Давайте сразу напишем тесты для проверки этой логики.

//class Logic {
//    int size;
//    Figure[] figures;                         // массив всего поля
//    int index = 0;                                  // порядковыйй номер ячейки
//

//}

//инит
//вывод
//логика

//ввод

//    |X|O|X|
//    |O|X|O|
//    |X|O|X|
//
//
//    |O|O|X|
//    |O|O|X|
//    |O|O|X|
//
//
//    |O|O|X|O|O|
//    |X|X|X|X|X|
//    |O|O|X|O|O|
//    |O|O|X|O|O|
//    |O|O|X|O|O|
//


interface Figure {
    default boolean movable() {
        return true;
    }

    Cell position();

    Cell[] way(Cell source, Cell dest);

    default String icon() {
        return String.format(
                "%s.png", this.getClass().getSimpleName()
        );
    }

    Figure copy(Cell dest);
}


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
    private final Figure3T[][] table;

    public Logic3T(Figure3T[][] table) {
        this.table = table;
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
        return this.fillBy(Figure3T::hasMarkX, 0, 0, 1, 0)
                || this.fillBy(Figure3T::hasMarkX, 0, 0, 0, 1)
                || this.fillBy(Figure3T::hasMarkX, 0, 0, 1, 1)
                || this.fillBy(Figure3T::hasMarkX, this.table.length - 1, 0, -1, 1);
    }

    public boolean isWinnerO() {
        return this.fillBy(Figure3T::hasMarkO, 0, 0, 1, 0)
                || this.fillBy(Figure3T::hasMarkO, 0, 0, 0, 1)
                || this.fillBy(Figure3T::hasMarkO, 0, 0, 1, 1)
                || this.fillBy(Figure3T::hasMarkO, this.table.length - 1, 0, -1, 1);
    }

    public boolean hasGap() {
        return true;
    }
}

//Класс TicTacToe - реализует визуальный компонент. Не волнуйтесь, если вы не понимаете большинство кода в этом классе.
//        Дальше в курсе будет разобраны все эти элементы. Здесь просто скопируйте этот код.


public class TicTacToe {
    private static final String JOB4J = "Крестики-нолики www.job4j.ru";
    private final int size = 3;
    private final Figure3T[][] cells = new Figure3T[size][size];
    private final Logic3T logic = new Logic3T(cells);

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
    public void start() {

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

    public static void main(String[] args) {
        new TicTacToe();
    }

    private void buildMarkO(double x, double y, int size) {
        System.out.print("X");
        //Group group = new Group();
        //int radius = size / 2;
        //Circle circle = new Circle(x + radius, y + radius, radius - 10);
        //circle.setStroke(Color.BLACK);
        //circle.setFill(Color.WHITE);
        //group.getChildren().add(circle);
        //return group;
    }

    private void buildMarkX(double x, double y, int size) {
        System.out.print("O");
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

    private void checkWinner() {
        if (this.logic.isWinnerX()) {
            //this.showAlert("Победили Крестики! Начните новую Игру!");
            System.out.println("Победили Крестики! Начните новую Игру!");
        } else if (this.logic.isWinnerO()) {
            //this.showAlert("Победили Нолики! Начните новую Игру!");
            System.out.println("Победили Нолики! Начните новую Игру!");
        }
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

    private void buildGrid() {
        //Group panel = new Group();
        for (int y = 0; y != this.size; y++) {
            for (int x = 0; x != this.size; x++) {
                System.out.println();
                //Figure3T rect = this.buildRectangle(x, y, 50);        построить поле
                //this.cells[y][x] = rect;
                //panel.getChildren().add(rect);
                //rect.setOnMouseClicked(this.buildMouseEvent(panel));
            }
        }
        //return panel;
    }
}

