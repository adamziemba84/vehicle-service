package com.azet.vehicle.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = FuelTypeValuesValidator.class)
public @interface FuelTypeValues {
    String message() default "{com.azet.user.validator.fuel_type.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
