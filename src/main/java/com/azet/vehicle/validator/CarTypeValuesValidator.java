package com.azet.vehicle.validator;

import com.azet.vehicle.model.Car;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CarTypeValuesValidator implements ConstraintValidator<CarlTypeValues, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return Car.CarType.of(value) != null;
    }
}
