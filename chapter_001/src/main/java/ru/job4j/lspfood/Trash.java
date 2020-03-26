package ru.job4j.lspfood;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

class Trash extends Store implements ExpaireDate {
    @Override
    public void checkFood(Map<N, ExpaireDate> store, Calendar virtualDate) {

        Iterator i = this.store.iterator();
        while (i.hasNext()) {
            Food food = (Food) i.next();

            //food.expaire = ExpaireDate.expaireDate(food.createDate, food.expaireDate);
            food.expaire = ExpaireDate.currentDate(food.createDate, food.expaireDate, virtualDate);

            if (food.expaire < 100) {
                System.out.println("Warning, there is fresh Food!!!");
                System.out.println(food.name + " " + food.expaire + "%, expiration date has not passed");
            }
        }
    }

    @Override
    public void add(Food food) {
        store.add(food);
    }
}
