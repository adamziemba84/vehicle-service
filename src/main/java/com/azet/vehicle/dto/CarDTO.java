package com.azet.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private long id;
    private int auction;
    private int manufacturer;
    private int model;
    private int type;
    private int manufactureYear;
    private int mileage;
    private int fuelType;
    private String vin;
    private int basicPrice;
    private int engineSize;
    private String country;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
