package ru.job4j.memstore;

class RoleStore<Role extends Base> implements Store<Role> {
    private final Store<Role> store = new MemStore<>();

    @Override
    public void add(final Role model) {
        store.add(model);
    }

    @Override
    public boolean replace(final String id, final Role model) {
        return store.replace(id, model);
    }

    @Override
    public boolean delete(final String id) {
        return store.delete(id);
    }

    @Override
    public Role findById(final String id) {
        return store.findById(id);
    }
}
