package org.remo.dtos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class RegisterOutDTO {

    private VehicleDTO vehicle;

    private CustomerDTO customer;

    private LocalDateTime checkInDateTime;

    private LocalDateTime checkOutDateTime;

    private BigDecimal amount = new BigDecimal(BigInteger.ZERO);

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public LocalDateTime getCheckInDateTime() {
        return checkInDateTime;
    }

    public void setCheckInDateTime(LocalDateTime checkInDateTime) {
        this.checkInDateTime = checkInDateTime;
    }

    public LocalDateTime getCheckOutDateTime() {
        return checkOutDateTime;
    }

    public void setCheckOutDateTime(LocalDateTime checkOutDateTime) {
        this.checkOutDateTime = checkOutDateTime;
    }
}
