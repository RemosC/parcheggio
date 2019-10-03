package org.remo;


import org.remo.exceptions.CantWayInException;
import org.remo.exceptions.CantWayOutException;
import org.remo.exceptions.NoVeichleFoundException;
import org.remo.exceptions.TooManyVeichlesInException;
import org.remo.models.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Incapsula il concetto di Area fisica del Parcheggio.
 *
 */
public class ParkingMap implements IParkingMap {

    private Map<Class<? extends Vehicle>, Integer> maxAvailableSeats;

    private Map<Class<? extends Vehicle>, List<? super Vehicle>> actualVehicles;


    public ParkingMap(Map<Class<? extends Vehicle>, Integer> maxAvailableSeats) {
        this.maxAvailableSeats = maxAvailableSeats;
        this.actualVehicles = new HashMap<>();
    }


    /**
     * Ottiene la capienza massima di posti disponibili per tipo automezzo
     *
     * @param veichleType
     * @return
     * @throws NoVeichleFoundException
     */
    public Integer getMaxAvailableSeats(Class<? extends Vehicle> veichleType) throws NoVeichleFoundException {
        if (!maxAvailableSeats.containsKey((veichleType))) {
            throw new NoVeichleFoundException("errore");
        }
        return maxAvailableSeats.get(veichleType);
    }


    /**
     * Esegue il processo di entrata del veicolo
     *
     * @param vehicle
     * @throws CantWayInException se il vehicle ha valore null, se il vehicle è già inserito in actualVehicles,
     * se raggiunto nr massimo di posti.
     */
    @Override
    public void wayIn(Vehicle vehicle) throws CantWayInException, TooManyVeichlesInException {
        if (vehicle == null)
            throw new CantWayInException("Indicare un veicolo");

        List<? super Vehicle> veichlesIn = actualVehicles.get(vehicle.getClass());
        if (veichlesIn == null) {
            veichlesIn = new ArrayList<>();
            actualVehicles.put(vehicle.getClass(), veichlesIn);
        }

        if (veichlesIn.contains(vehicle))
            throw new CantWayInException("Il veicolo con targa " + vehicle.getLicensePlate() + " è già nel percheggio");

        try {
            if (getMaxAvailableSeats(vehicle.getClass())<=veichlesIn.size())
                throw new TooManyVeichlesInException("Raggiunto il nr massimo di posti disponibili per il tipo di automezzo: "+ vehicle.getType());
        } catch (NoVeichleFoundException e) {
            throw new CantWayInException("Indicare un veicolo");
        }

        veichlesIn.add(vehicle);
    }


    /**
     * Esegue il processo di uscita del veicolo
     *
     *
     * @param vehicle
     * @throws CantWayOutException se il vehicle ha valore null, se il vehicle non esiste in actualVehicles.
     */
    @Override
    public void wayOut(Vehicle vehicle) throws CantWayOutException {
        if (vehicle == null)
            throw new CantWayOutException("Indicare un veicolo");

        List<? super Vehicle> veichlesIn = actualVehicles.get(vehicle.getClass());
        if (veichlesIn == null)
            throw new CantWayOutException("nessun automezzo di tipo "+ vehicle.getType() + " nel parcheggio");

        if (!veichlesIn.contains(vehicle))
            throw new CantWayOutException("Il veicolo con targa " + vehicle.getLicensePlate() + " è non è nel percheggio");

        veichlesIn.remove(vehicle);
    }

}
