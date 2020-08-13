package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

import java.util.List;

public class FindByNameAction implements UserAction {
    @Override
    public final String name() {
        return "=== Find items by name ====";
    }

    @Override
    public final boolean execute(final Input input, final Store tracker) {
        String name = input.askStr("Enter name: ");
        List<Item> items = tracker.findByName(name);
        for (Item item : items) {
            System.out.println(item);
        }
        return true;
    }
}
