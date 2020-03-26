package ru.job4j.lspparking;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parking implements Places {
    int carPlaces;                            // вместимость кол-во мест
    int truckPlaces;
    int truckTocar = 4;                       // 1 truck = 4 car
    int car = 0;
    int truck = 0;
    int freePlaces;

    public Parking(int carPlaces, int truckPlaces) {
        this.carPlaces = carPlaces;
        this.truckPlaces = truckPlaces;
        this.freePlaces = freePlaces;
    }

    List<Vehicle> vehicleList = new ArrayList<>();

    public static void main(String[] args) {
        Parking parking = new Parking(4, 2);
        parking.go();
    }

    void go() {
        this.freePlaces = truckPlaces * truckTocar + carPlaces - car - truck;
        System.out.println();
        System.out.println(0 + " hour, Car " + car + "  Truck " + truck + "  free Places " + freePlaces);
        System.out.println();

        int n = 0;
        while (n++ < 20) {
            if (vehicleList.size() != 0) {
                checkVehicle();
            }
            if (availablePlace()) {
                randomVehicle();
            }
            System.out.println(n + " hour, Car " + car + "  Truck " + truck + "  free Places " + freePlaces);
            System.out.println();
        }
    }

    @Override
    public boolean availablePlace() {
        boolean res = false;
        this.freePlaces = truckPlaces * truckTocar + carPlaces - car - truck * truckTocar;
        if (this.freePlaces > 0) {
            res = true;
        }
        return res;
    }

    @Override
    public void randomVehicle() {
        int vehicle = (int) (Math.random() * 4);
        if (vehicle > 1) {
            Car c = new Car();
            c.placeVehicle(this);
        } else if (freePlaces >= truckTocar) {
            Truck t = new Truck();
            t.placeVehicle(this);
        } else {
            System.out.println(" ===== No place for Truck ! ===== ");
        }
    }

    @Override
    public void checkVehicle() {
        Iterator<Vehicle> v = vehicleList.iterator();
        while (v.hasNext()) {
            Vehicle vehicle = v.next();
            vehicle.time -= 1;
            if (vehicle.time == 0) {
                ((VehicleI) vehicle).removeVehicle(this);
                v.remove();
            }
        }
    }
}

