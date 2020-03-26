package ru.job4j.lspfood;

import java.util.Calendar;
import java.util.Map;

interface ExpaireDate {
    static int expaireDate(Calendar createDate, Calendar expaireDate) {
        Calendar currentDate = Calendar.getInstance();
        long deltaTime = expaireDate.getTime().getTime() - createDate.getTime().getTime();
        long current = currentDate.getTime().getTime() - createDate.getTime().getTime();
        return (int) Math.ceil((float) current / deltaTime * 100);
    }

    static int currentDate(Calendar createDate, Calendar expaireDate, Calendar virtualDate) {
        long deltaTime = expaireDate.getTime().getTime() - createDate.getTime().getTime();
        long current = virtualDate.getTime().getTime() - createDate.getTime().getTime();
        return (int) Math.ceil((float) current / deltaTime * 100);
    }

    void checkFood(Map<N, ExpaireDate> store, Calendar virtualDate);       // на каждом складе

    void add(Food food);
}
