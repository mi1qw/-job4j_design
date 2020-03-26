package ru.job4j.lspparking;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParkingTest {
    @Test
    public void getRandomVehicle() {
        Parking parking = new Parking(15, 2);
        parking.freePlaces = 20;
        int countVehicle = parking.vehicleList.size();
        parking.randomVehicle();
        assertTrue(parking.vehicleList.get(parking.vehicleList.size() - 1).time > 0 && parking.vehicleList.size() > countVehicle);
    }

    @Test
    public void timeVehicleMinusHour() {
        Parking parking = new Parking(15, 2);
        parking.vehicleList.add(new Car());
        parking.vehicleList.add(new Truck());
        parking.vehicleList.get(0).time = 2;
        parking.vehicleList.get(1).time = 2;
        int expected = parking.vehicleList.get(0).time + parking.vehicleList.get(1).time - 2;

        parking.checkVehicle();
        int actual = parking.vehicleList.get(0).time + parking.vehicleList.get(1).time;
        assertEquals(actual, expected);
    }

    @Test
    public void minimumTimeShouldRemoveVehicleFromList() {
        Parking parking = new Parking(15, 2);
        parking.vehicleList.add(new Car());
        parking.vehicleList.add(new Truck());
        parking.vehicleList.get(0).time = 1;
        parking.vehicleList.get(1).time = 1;
        int expected = parking.vehicleList.size() - 2;

        parking.checkVehicle();
        int actual = parking.vehicleList.size();
        assertEquals(actual, expected);
    }
}

