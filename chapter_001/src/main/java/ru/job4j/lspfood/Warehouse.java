package ru.job4j.lspfood;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

class Warehouse extends Store implements ExpaireDate {
    @Override
    public void checkFood(Map<N, ExpaireDate> store, Calendar virtualDate) {

        Iterator i = this.store.iterator();
        while (i.hasNext()) {
            Food food = (Food) i.next();

            //food.expaire = ExpaireDate.expaireDate(food.createDate, food.expaireDate);
            food.expaire = ExpaireDate.currentDate(food.createDate, food.expaireDate, virtualDate);

            if (food.expaire > 25 && food.expaire < 75) {
                store.get(N.shop).add(food);
                i.remove();
            }
            if (food.expaire > 75 && !food.disscounted) {
                food.price *= (100 - food.disscount) / 100;           // скидка 10%
                food.disscounted = true;
                store.get(N.shop).add(food);
                i.remove();
            }
            if (food.expaire > 100) {
                store.get(N.trash).add(food);
                i.remove();
            }
        }
    }

    @Override
    public void add(Food food) {
        store.add(food);
    }
}
