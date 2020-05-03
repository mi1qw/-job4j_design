package ru.job4j.memstore;

class UserStore<User extends Base> implements Store<User> {
    private final Store<User> store = new MemStore<>();

    @Override
    public void add(final User model) {
        store.add(model);
    }

    @Override
    public boolean replace(final String id, final User model) {
        return store.replace(id, model);
    }

    @Override
    public boolean delete(final String id) {
        return store.delete(id);
    }

    @Override
    public User findById(final String id) {
        return store.findById(id);
    }
}
