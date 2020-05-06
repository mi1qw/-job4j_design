package ru.job4j.memstore;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MemStoreTest {
    private RoleStore<Base> role;
    private UserStore<Base> user;
    private Base cat;
    private Base cats;
    private Base dog;
    private Base dogs;

    @Before
    public void setUp() {
        cat = new Base("myau");
        cats = new Base("murr");
        dog = new Base("Waw");
        dogs = new Base("friends");

        role = new RoleStore<>();
        user = new UserStore<>();
    }

    @Test
    public void findById() {
        role.add(cat);
        assertNotNull(role.findById("myau"));
        assertNull(role.findById("Waw"));
    }

    @Test
    public void add() {
        assertNull(role.findById("myau"));
        role.add(cat);
        assertNotNull(role.findById("myau"));

        assertNull(user.findById("Waw"));
        user.add(dog);
        assertNotNull(user.findById("Waw"));
    }

    @Test
    public void replace() {
        assertNull(role.findById("murr"));
        role.add(cat);
        role.replace("myau", cats);
        assertNotNull(role.findById("murr"));
        assertFalse(role.replace("000myau", cats));

        assertNull(user.findById("friends"));
        user.add(dog);
        user.replace("Waw", dogs);
        assertNotNull(user.findById("friends"));
    }

    @Test
    public void delete() {

        role.add(cat);
        assertNotNull(role.findById("myau"));
        assertFalse(role.delete("000myau"));
        role.delete("myau");
        assertNull(role.findById("myau"));

        user.add(dog);
        assertNotNull(user.findById("Waw"));
        assertFalse(user.delete("000myau"));
        user.delete("Waw");
        assertNull(user.findById("Waw"));
    }
}