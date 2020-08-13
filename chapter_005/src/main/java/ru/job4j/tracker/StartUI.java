package ru.job4j.tracker;

import ru.job4j.tracker.action.*;
import ru.job4j.tracker.input.ConsoleInput;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.ValidateInput;
import ru.job4j.tracker.store.MemTracker;
import ru.job4j.tracker.store.Store;

public class StartUI {
    /**
     * Init.
     *
     * @param input   the input
     * @param tracker the tracker
     * @param actions the actions
     */
    public void init(final Input input, final Store tracker, final UserAction[] actions) {
        boolean run = true;
        while (run) {
            showMenu(actions);
            int select = input.askInt("Enter select: ");
            UserAction action = actions[select];
            run = action.execute(input, tracker);
        }
    }

    private void showMenu(final UserAction[] actions) {
        System.out.println("Menu.");
        for (int i = 0; i < actions.length; i++) {
            System.out.printf("%d. %s%n", i, actions[i].name());
        }
    }

    public static void main(final String[] args) {
        Input validate = new ValidateInput(
                new ConsoleInput()
        );
        //try (Store tracker = new SqlTracker()) {
        try (MemTracker tracker = new MemTracker()) {
            tracker.init();
            UserAction[] actions = {
                    new CreateAction(),
                    new ReplaceAction(),
                    new DeleteAction(),
                    new FindAllAction(),
                    new FindByIdAction(),
                    new FindByNameAction(),
                    new ExitAction()
            };
            new StartUI().init(validate, tracker, actions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}