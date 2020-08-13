package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

public class CreateAction implements UserAction {
    @Override
    public final String name() {
        return "=== Create a new Item ====";
    }

    @Override
    public final boolean execute(final Input input, final Store tracker) {
        String name = input.askStr("Enter name: ");
        Item item = new Item(name);
        tracker.add(item);
        System.out.println("Item successfully added!");
        return true;
    }
}
