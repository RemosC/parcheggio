package org.remo.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="cars")
@DiscriminatorValue(value = "C")
public class Car extends Vehicle {

    public Car() {
    }

    public Car(String licesePlate, String model) {
        super(licesePlate, model);
    }

    public String getType() {
        return "Automobile";
    }
}
