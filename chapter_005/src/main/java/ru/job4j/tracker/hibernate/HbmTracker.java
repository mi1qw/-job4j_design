//package ru.job4j.tracker.hibernate;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.query.Query;
//
//import java.util.List;
//
//public class HbmTracker implements AutoCloseable {
//
//    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//            .configure().build();
//
//    private final SessionFactory sf = new MetadataSources(registry)
//            .buildMetadata().buildSessionFactory();
//
//    public Item add(Item item) {
//        Session session = sf.openSession();
//        session.beginTransaction();
//        session.save(item);
//        session.getTransaction().commit();
//        session.close();
//        return item;
//    }
//
//    public boolean replace(int id, Item item) {
//        boolean result = false;
//        Session session = sf.openSession();
//        session.beginTransaction();
//        Item dbItem = session.get(Item.class, id);
//        if (dbItem != null) {
//            dbItem.setName(item.getName());
//            result = true;
//        }
//        session.getTransaction().commit();
//        session.close();
//        return result;
//    }
//
//    public boolean delete(int id) {
//        Session session = sf.openSession();
//        session.beginTransaction();
//        session.delete(Item.idStub(id));
//        session.getTransaction().commit();
//        session.close();
//        return findById(id) == null;
//    }
//
//    public List<Item> findAll() {
//        Session session = sf.openSession();
//        session.beginTransaction();
//        List<Item> result = session.createQuery("from Item").list();
//        session.getTransaction().commit();
//        session.close();
//        return result;
//    }
//
//    public List<Item> findByName(String key) {
//        Session session = sf.openSession();
//        session.beginTransaction();
//        Query query = session.createQuery("from Item i where i.name = :name");
//        query.setParameter("name", key);
//        List<Item> result = query.list();
//        session.getTransaction().commit();
//        session.close();
//        return result;
//    }
//
//    public Item findById(int id) {
//        Session session = sf.openSession();
//        session.beginTransaction();
//        Item dbItem = session.get(Item.class, id);
//        session.getTransaction().commit();
//        session.close();
//        return dbItem;
//    }
//
//    @Override
//    public void close() throws Exception {
//        StandardServiceRegistryBuilder.destroy(registry);
//    }
//
//    public static void main(String[] args) {
//        HbmTracker tracker = new HbmTracker();
//        save(List.of(
//                new Item("item1"),
//                new Item("item2"),
//                new Item("item3"),
//                new Item("item4"),
//                new Item("item5"),
//                new Item("item1")
//        ), tracker);
//        //findAll(tracker);
//        //findByName("item1", tracker);
//        findById(3, tracker);
//        update(3, new Item("item3 new"), tracker);
//        findById(3, tracker);
//        delete(3, tracker);
//        findById(3, tracker);
//    }
//
//    private static void save(List<Item> items, HbmTracker tracker) {
//        System.out.println(System.lineSeparator() + "=================== Saving begin ===================");
//        items.forEach(tracker::add);
//        System.out.println(System.lineSeparator() + "=================== Save end     ===================");
//    }
//
//    private static void findAll(HbmTracker tracker) {
//        System.out.println(System.lineSeparator() + "=================== Find all begin ===================");
//        tracker.findAll().forEach(System.out::println);
//        System.out.println(System.lineSeparator() + "=================== Find all end   ===================");
//    }
//
//    private static void findByName(String name, HbmTracker tracker) {
//        System.out.println(System.lineSeparator() + "=================== Find by name begin ===================");
//        tracker.findByName(name).forEach(System.out::println);
//        System.out.println(System.lineSeparator() + "=================== Find by name end   ===================");
//    }
//
//    private static void findById(int id, HbmTracker tracker) {
//        System.out.println(System.lineSeparator() + "=================== Find by id begin ===================");
//        System.out.println(tracker.findById(id));
//        System.out.println(System.lineSeparator() + "=================== Find by id end   ===================");
//    }
//
//    private static void update(int id, Item newItem, HbmTracker tracker) {
//        System.out.println(System.lineSeparator() + "=================== Find by id begin ===================");
//        System.out.println(tracker.replace(id, newItem));
//        System.out.println(System.lineSeparator() + "=================== Find by id end   ===================");
//    }
//
//    private static void delete(int id, HbmTracker tracker) {
//        System.out.println(System.lineSeparator() + "=================== Find by id begin ===================");
//        System.out.println(tracker.delete(id));
//        System.out.println(System.lineSeparator() + "=================== Find by id end   ===================");
//    }
//
//
//}
