package com.azet.vehicle.validator;

import com.azet.vehicle.model.Car;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FuelTypeValuesValidator implements ConstraintValidator<FuelTypeValues, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return Car.FuelType.of(value) != null;
    }
}
