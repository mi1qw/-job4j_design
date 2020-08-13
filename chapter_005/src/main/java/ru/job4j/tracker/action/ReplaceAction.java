package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

public class ReplaceAction implements UserAction {
    @Override
    public final String name() {
        return "=== Edit item ====";
    }

    @Override
    public final boolean execute(final Input input, final Store tracker) {
        String id = input.askStr("Enter id: ");
        String name = input.askStr("Enter name: ");
        if (tracker.replace(id, new Item(name))) {
            System.out.println("Item is successfully replaced!");
        } else {
            System.out.println("Wrong id!");
        }
        return true;
    }
}
