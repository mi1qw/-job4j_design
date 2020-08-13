package ru.job4j.tracker.input;

public class ValidateInput implements Input {
    private Input input;

    public ValidateInput(final Input input) {
        this.input = input;
    }

    @Override
    public final String askStr(final String question) {
        return input.askStr(question);
    }

    @Override
    public final int askInt(final String question) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = input.askInt(question);
                invalid = false;
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter validate data again.");
            }
        } while (invalid);
        return value;
    }

    @Override
    public final int askInt(final String question, final int max) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = input.askInt(question, max);
                invalid = false;
            } catch (IllegalStateException moe) {
                System.out.println("Please select key from menu.");
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter validate data again.");
            }
        } while (invalid);
        return value;
    }
}
