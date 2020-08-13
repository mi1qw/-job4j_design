//package ru.job4j.tracker.hibernate;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "item")
//public class Item {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    private String name;
//
//    public Item() {
//    }
//
//    public Item(String name) {
//        this.name = name;
//    }
//
//    public static Item idStub(int id) {
//        Item item = new Item();
//        item.setId(id);
//        return item;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%d %s", id, name);
//    }
//}
