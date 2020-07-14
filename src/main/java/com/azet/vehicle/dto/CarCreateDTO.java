package com.azet.vehicle.dto;

import com.azet.vehicle.validator.FuelTypeValues;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarCreateDTO {
    @NotNull private long auction;
    @NotNull private long manufacturer;
    @NotNull private long model;
    @NotNull private int type;
    @NotNull private int manufactureYear; // 2014
    @NotNull private int mileage; // 15000

    @NotNull @FuelTypeValues
    private int fuelType;
    @NotNull @NotEmpty private String vin;
    @NotNull private int basicPrice;
    @NotNull private int engineSize;
    @NotNull @NotEmpty private String country;
}
