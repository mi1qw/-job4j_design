package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.store.Store;

public class StubAction implements UserAction {
    private boolean call = false;

    @Override
    public final String name() {
        return "Stub action";
    }

    @Override
    public final boolean execute(final Input input, final Store tracker) {
        call = true;
        return false;
    }

    public final boolean isCall() {
        return call;
    }
}