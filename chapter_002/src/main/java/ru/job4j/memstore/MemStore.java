package ru.job4j.memstore;

import java.util.ArrayList;
import java.util.List;

final class MemStore<T extends Base> implements Store<T> {
    private final List<T> mem = new ArrayList<>();

    @Override
    public void add(final T model) {
        mem.add(model);
    }

    @Override
    public boolean replace(final String id, final T model) {
        T rId = findById(id);
        if (rId != null) {
            mem.set(mem.indexOf(rId), model);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(final String id) {
        T del = findById(id);
        if (del != null) {
            return mem.remove(del);
        }
        return false;
    }

    @Override
    public T findById(final String id) {
        for (T m : mem) {
            String mId = m.getId();
            if (mId.equals(id)) {
                return m;
            }
        }
        return null;
    }
}
