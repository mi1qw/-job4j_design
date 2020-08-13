package ru.job4j.tracker.input;

import java.util.Scanner;

public class ConsoleInput implements Input {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public final String askStr(final String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    @Override
    public final int askInt(final String question) {
        return Integer.parseInt(askStr(question));
    }

    @Override
    public final int askInt(final String question, final int max) {
        int select = askInt(question);
        if (select < 0 || select >= max) {
            throw new IllegalStateException(String.format("Out of about %s > [0, %s]", select, max));
        }
        return select;
    }
}