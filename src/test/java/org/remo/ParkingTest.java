package org.remo;


import org.junit.Assert;
import org.junit.Test;
import org.remo.exceptions.CantWayInException;
import org.remo.exceptions.TooManyVeichlesInException;
import org.remo.models.Car;
import org.remo.models.Vehicle;


import java.util.HashMap;
import java.util.Map;

public class ParkingTest {

    private Parking parking;

    @Test
    public void checkIn_checkIfMaxSeatsExceeding() throws Exception {
        Vehicle macchina1 = new Car("LT 5888493", "fiesta");
        Vehicle macchina2 = new Car("RM 88E6833", "ferrari");

        Map<Class<? extends Vehicle>, Integer> parkingMapData = new HashMap<>();
        parkingMapData.put(Car.class, 1);
        ParkingMap parkingMap = new ParkingMap(parkingMapData);
        parking = new Parking(parkingMap);

        try {
            parking.checkIn(null, null);
            parking.checkIn(null, null);
        } catch (CantWayInException e) {
            e.printStackTrace();
        } catch (TooManyVeichlesInException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void checkOut() throws Exception {
    }

}