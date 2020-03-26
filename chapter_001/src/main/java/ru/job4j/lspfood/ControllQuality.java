package ru.job4j.lspfood;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum N {
    warehouse, shop, trash
}


class ControllQuality {
    Calendar virtualDate;
    Map<N, ExpaireDate> store;

    public ControllQuality(Map<N, ExpaireDate> store) {
        this.store = store;
        this.virtualDate = Calendar.getInstance();
    }

    void checkFood(int dayPlus) {
        virtualDate.add(Calendar.DAY_OF_MONTH, dayPlus);
        System.out.println(virtualDate.get(Calendar.DAY_OF_MONTH)
                + " " + ((int) 1 + virtualDate.get(Calendar.MONTH))
                + " " + virtualDate.get(Calendar.YEAR) + "  Date ______________________");

        for (Map.Entry<N, ExpaireDate> n : store.entrySet()) {

            n.getValue().checkFood(store, virtualDate);
        }
    }
}
