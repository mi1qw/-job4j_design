package ru.job4j.lspparking;

class Vehicle {
    int time;
    final static int MAXTIME = 4;                          // максимальное время стоянки

    public Vehicle() {
        this.time = (int) ((Math.random() * MAXTIME) + 1);
    }
}


class Car extends Vehicle implements VehicleI {
    @Override
    public void placeVehicle(Parking parking) {
        parking.car += 1;
        parking.freePlaces -= 1;
        parking.vehicleList.add(this);
        System.out.println("place Car " + time + " hour");
    }

    @Override
    public void removeVehicle(Parking parking) {
        parking.car -= 1;
        parking.freePlaces += 1;
        System.out.println("remove Car");
    }
}


class Truck extends Vehicle implements VehicleI {
    @Override
    public void placeVehicle(Parking parking) {
        parking.truck += 1;
        parking.freePlaces -= parking.truckTocar;
        parking.vehicleList.add(this);
        System.out.println("place Truck " + time + " hour");
    }

    @Override
    public void removeVehicle(Parking parking) {
        parking.truck -= 1;
        parking.freePlaces += parking.truckTocar;
        System.out.println("remove Truck");
    }
}