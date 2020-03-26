package ru.job4j.lspfood;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

class Go {
    public static void main(String[] args) {
        new Go().go();
    }

    public void go() {
        Calendar createDate;
        Calendar expaireDate;
        int expaire;
        Calendar currentDate = Calendar.getInstance();

        //******************* подготовка продуктов *******************
        createDate = new GregorianCalendar(2020, Calendar.MARCH, 20);
        expaireDate = new GregorianCalendar(2020, Calendar.APRIL, 19);
        expaire = ExpaireDate.expaireDate(createDate, expaireDate);
        Food cheese = new Food("cheese", createDate, expaireDate, expaire, 100, 10);

        createDate = new GregorianCalendar(2020, Calendar.MARCH, 15);
        expaireDate = new GregorianCalendar(2020, Calendar.APRIL, 5);
        expaire = ExpaireDate.expaireDate(createDate, expaireDate);
        Food eggs = new Food("eggs", createDate, expaireDate, expaire, 20, 10);

        createDate = new GregorianCalendar(2020, Calendar.MARCH, 9);
        expaireDate = new GregorianCalendar(2020, Calendar.APRIL, 1);
        expaire = ExpaireDate.expaireDate(createDate, expaireDate);
        Food sausage = new Food("sausage", createDate, expaireDate, expaire, 100, 10);

        //******************* помещаем продукты на склад*******************
        Map<N, ExpaireDate> store = new HashMap<>();
        Store warehouse = new Warehouse();
        ((ExpaireDate) warehouse).add(sausage);
        ((ExpaireDate) warehouse).add(cheese);

        Store shop = new Shop();
        ((ExpaireDate) shop).add(eggs);

        Store trash = new Trash();
        //((ExpaireDate) trash).add(sausage);
        //******************* конец ****************************************

        store.put(N.warehouse, (ExpaireDate) warehouse);
        store.put(N.shop, (ExpaireDate) shop);
        store.put(N.trash, (ExpaireDate) trash);
        ControllQuality controllQuality = new ControllQuality(store);

        //******************* вывод в консль, имитация времени  *************
        System.out.println(currentDate.get(Calendar.DAY_OF_MONTH)
                + " " + ((int) 1 + currentDate.get(Calendar.MONTH))
                + " " + currentDate.get(Calendar.YEAR) + "  Date ______________________");

        System.out.println("   Warehouse");
        warehouse.store.forEach(Food -> {
            System.out.println(Food.name + " " + Food.expaire + "%  price "
                    + Food.price + "    expaire "
                    + Food.expaireDate.get(Calendar.DAY_OF_MONTH)
                    + " " + ((int) 1 + Food.expaireDate.get(Calendar.MONTH))
                    + " " + Food.expaireDate.get(Calendar.YEAR));
        });

        System.out.println();
        System.out.println("   Shop");
        shop.store.forEach(Food -> {
            System.out.println(Food.name + " " + Food.expaire + "%  price "
                    + Food.price + "    expaire "
                    + Food.expaireDate.get(Calendar.DAY_OF_MONTH)
                    + " " + ((int) 1 + Food.expaireDate.get(Calendar.MONTH))
                    + " " + Food.expaireDate.get(Calendar.YEAR));
        });

        System.out.println();
        System.out.println("   Trash");
        trash.store.forEach(Food -> {
            System.out.println(Food.name + " " + Food.expaire + "%  price "
                    + Food.price + "    expaire "
                    + Food.expaireDate.get(Calendar.DAY_OF_MONTH)
                    + " " + ((int) 1 + Food.expaireDate.get(Calendar.MONTH))
                    + " " + Food.expaireDate.get(Calendar.YEAR));
        });
        System.out.println("\n");

        int x = 8;
        do {
            controllQuality.resort(1);

            System.out.println("   Warehouse");
            warehouse.store.forEach(Food -> {
                System.out.println(Food.name + " " + Food.expaire + "%    price "
                        + Food.price + "    expaire "
                        + Food.expaireDate.get(Calendar.DAY_OF_MONTH)
                        + " " + ((int) 1 + Food.expaireDate.get(Calendar.MONTH))
                        + " " + Food.expaireDate.get(Calendar.YEAR));
            });

            System.out.println();
            System.out.println("   Shop");
            shop.store.forEach(Food -> {
                System.out.println(Food.name + " " + Food.expaire + "%    price "
                        + Food.price + "    expaire "
                        + Food.expaireDate.get(Calendar.DAY_OF_MONTH)
                        + " " + ((int) 1 + Food.expaireDate.get(Calendar.MONTH))
                        + " " + Food.expaireDate.get(Calendar.YEAR));
            });

            System.out.println();
            System.out.println("   Trash");
            trash.store.forEach(Food -> {
                System.out.println(Food.name + " " + Food.expaire + "%    price "
                        + Food.price + "    expaire "
                        + Food.expaireDate.get(Calendar.DAY_OF_MONTH)
                        + " " + ((int) 1 + Food.expaireDate.get(Calendar.MONTH))
                        + " " + Food.expaireDate.get(Calendar.YEAR));
            });

            System.out.println("\n");
        } while (--x > 0);
    }
}
