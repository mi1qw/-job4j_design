package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

public class FindByIdAction implements UserAction {
    @Override
    public final String name() {
        return "=== Find item by Id ====";
    }

    @Override
    public final boolean execute(final Input input, final Store tracker) {
        String id = input.askStr("Enter id: ");
        Item item = tracker.findById(id);
        if (item != null) {
            System.out.println(item);
        } else {
            System.out.println("Wrong id! Not found");
        }
        return true;
    }
}
