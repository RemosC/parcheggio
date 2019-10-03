package org.remo.daos;


import org.remo.dtos.VehicleDTO;
import org.remo.models.Vehicle;

public interface VehicleDAO {
    Vehicle save(VehicleDTO vehicle);
    Vehicle getVehicleByDTO(VehicleDTO vehicleDTO);
    Vehicle getVehicleByLicense(VehicleDTO vehicleDTO);
}
