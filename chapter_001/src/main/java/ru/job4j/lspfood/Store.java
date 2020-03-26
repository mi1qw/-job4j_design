package ru.job4j.lspfood;

import java.util.ArrayList;

abstract class Store {
    ArrayList<Food> store;

    public Store() {
        this.store = new ArrayList<>();
    }
}


