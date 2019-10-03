package org.remo.dtos;

public class VehicleDTO {

    private Long id;

    private String licesePlate;

    private String model;

    private String vehicleType;

    public VehicleDTO() {  }

    public VehicleDTO(Long id, String licesePlate, String model, String vehicleType) {
        this.id = id;
        this.licesePlate = licesePlate;
        this.model = model;
        this.vehicleType = vehicleType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licesePlate;
    }

    public void setLicesePlate(String licesePlate) {
        this.licesePlate = licesePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
