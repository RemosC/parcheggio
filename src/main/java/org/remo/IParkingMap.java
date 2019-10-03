package org.remo;


import org.remo.exceptions.CantWayInException;
import org.remo.exceptions.CantWayOutException;
import org.remo.exceptions.NoVeichleFoundException;
import org.remo.exceptions.TooManyVeichlesInException;
import org.remo.models.Vehicle;

public interface IParkingMap {

    Integer getMaxAvailableSeats(Class<? extends Vehicle> veichleType) throws NoVeichleFoundException;

    void wayIn(Vehicle vehicle) throws CantWayInException, TooManyVeichlesInException;

    void wayOut(Vehicle vehicle) throws CantWayOutException;
}
