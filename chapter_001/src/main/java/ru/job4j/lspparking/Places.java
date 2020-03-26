package ru.job4j.lspparking;

interface Places {
    boolean availablePlace();

    void randomVehicle();                // случайная машина

    void checkVehicle();                 // проверяем имеющиеся машины на стоянке
}
