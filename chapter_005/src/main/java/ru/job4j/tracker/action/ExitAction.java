package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.store.Store;

public class ExitAction implements UserAction {
    @Override
    public final String name() {
        return "=== Exit ====";
    }

    @Override
    public final boolean execute(final Input input, final Store tracker) {
        return false;
    }
}
