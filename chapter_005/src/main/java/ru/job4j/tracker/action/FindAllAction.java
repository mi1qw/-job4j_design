package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

import java.util.List;

public class FindAllAction implements UserAction {
    @Override
    public final String name() {
        return "=== Show all items ====";
    }

    @Override
    public final boolean execute(final Input input, final Store tracker) {
        List<Item> items = tracker.findAll();
        for (Item item : items) {
            System.out.println(item);
        }
        return true;
    }
}
