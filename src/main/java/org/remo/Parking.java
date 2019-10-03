package org.remo;


import org.remo.daos.*;
import org.remo.dtos.CustomerDTO;
import org.remo.dtos.RegisterOutDTO;
import org.remo.dtos.VehicleDTO;
import org.remo.exceptions.CantWayInException;
import org.remo.exceptions.CantWayOutException;
import org.remo.exceptions.TooManyVeichlesInException;
import org.remo.models.Customer;
import org.remo.models.Vehicle;

import java.time.LocalDateTime;

public class Parking implements ParkingAble{

    private IParkingMap parkingMap;


    public Parking(ParkingMap parkingMap) {
        this.parkingMap = parkingMap;
    }


    /**
     * gestisce l'entrata di un automezzo al parcheggio
     *
     * @param vehicleDTO
     * @throws CantWayInException
     */
    public void checkIn(VehicleDTO vehicleDTO, CustomerDTO customerDTO) throws CantWayInException, TooManyVeichlesInException {
        CustomerDAO customerDao = new CustomerDBDAO();
        VehicleDAO vehicleDAO = new VehicleDBDAO();
        RegisterDAO registerDAO = new RegisterDBDAO();

        Customer customer = customerDao.save(customerDTO);
        Vehicle vehicle = vehicleDAO.save(vehicleDTO);

        parkingMap.wayIn(vehicle);

        registerDAO.registerIn(customer, vehicle);

        System.out.println("Il veicolo di tipo " + vehicle.getType() + " con targa "+ vehicle.getLicensePlate() + " è entrato nel parcheggio");
    }


    /**
     * gestisce l'euscita di un automezzo al parcheggio
     *
     * @param vehicleDto
     * @throws CantWayOutException
     */
    public void checkOut(VehicleDTO vehicleDto, LocalDateTime whenGoOut) throws CantWayOutException {
        VehicleDAO vehicleDAO = new VehicleDBDAO();
        RegisterDAO registerDAO = new RegisterDBDAO();

        Vehicle vehicle = vehicleDAO.getVehicleByLicense(vehicleDto);
        RegisterOutDTO registerOut =  registerDAO.registerOut(vehicle, whenGoOut);

        parkingMap.wayOut(vehicle);
        System.out.println("Il veicolo di tipo " + vehicle.getType() + " con targa "+ vehicle.getLicensePlate() + " è uscito dal parcheggio pagando " + registerOut.getAmount());
    }
}
