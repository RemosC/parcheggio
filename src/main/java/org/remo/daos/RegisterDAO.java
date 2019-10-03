package org.remo.daos;

import org.remo.dtos.RegisterOutDTO;
import org.remo.models.Customer;
import org.remo.models.Register;
import org.remo.models.Vehicle;

import java.time.LocalDateTime;

public interface RegisterDAO {
    void registerIn(Customer customer, Vehicle vehicle);
    RegisterOutDTO registerOut(Vehicle vehicle, LocalDateTime whenGoOut);

    Register getRegisterByVehicle(Vehicle vehicle);
}
