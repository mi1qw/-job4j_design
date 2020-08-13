package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.store.Store;

public class DeleteAction implements UserAction {
    @Override
    public final String name() {
        return "=== Delete item ====";
    }

    @Override
    public final boolean execute(final Input input, final Store tracker) {
        String id = input.askStr("Enter id: ");
        if (tracker.delete(id)) {
            System.out.println("Item is successfully deleted!");
        } else {
            System.out.println("Wrong id!");
        }
        return true;
    }
}
