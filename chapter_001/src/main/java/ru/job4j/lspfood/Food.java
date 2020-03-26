package ru.job4j.lspfood;

import java.util.*;

public class Food {
    String name;
    Calendar expaireDate;
    Calendar createDate;
    int expaire;
    double price;
    double disscount;
    boolean disscounted;
    final double expirationDate = 25;
    final double expirationDateDisscount = 75;

    public Food(String name, Calendar createDate, Calendar expaireDate, int expaire, double price, double disscount) {
        this.name = name;
        this.expaireDate = expaireDate;
        this.createDate = createDate;
        this.price = price;
        this.disscount = disscount;
        this.disscounted = false;
        this.expaire = expaire;
    }
}
