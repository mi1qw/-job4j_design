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
        int rId = indexOf(id);
        if (indexOf(id) != -1) {
            mem.set(rId, model);
            return true;
        }
        return false;
    }

    private int indexOf(final String id) {
        for (int n = 0; n < mem.size(); ++n) {
            if (mem.get(n).getId().equals(id)) {
                return n;
            }
        }
        return -1;
    }

    @Override
    public boolean delete(final String id) {
        int rId = indexOf(id);
        if (indexOf(id) != -1) {
            mem.remove(rId);
            return true;
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
