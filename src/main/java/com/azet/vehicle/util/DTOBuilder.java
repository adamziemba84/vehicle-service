package com.azet.vehicle.util;

import com.azet.vehicle.dto.CarDTO;
import com.azet.vehicle.dto.CarPatchDTO;
import com.azet.vehicle.model.Car;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DTOBuilder {
    public static CarDTO toCarDTO(Car car) {
        CarDTO dto = new CarDTO();
        BeanUtils.copyProperties(car, dto);

        dto.setFuelType(car.getFuelType().getValue());
        dto.setType(car.getType().getValue());
        dto.setManufacturer(car.getManufacturer().getId().intValue());
        dto.setAuction(car.getAuction().getId().intValue());
        dto.setModel(car.getModel().getId().intValue());
        return dto;
    }

    public static List<CarDTO> toCarDTO(List<Car> car) {
        return car.stream()
                .map(DTOBuilder::toCarDTO)
                .collect(Collectors.toList());
    }

    public static CarPatchDTO toCarCreateDTO(Car car) {
        CarPatchDTO dto = new CarPatchDTO();
        BeanUtils.copyProperties(car, dto);

        return dto;
    }
}
