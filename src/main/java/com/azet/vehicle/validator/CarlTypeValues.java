package com.azet.vehicle.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = CarTypeValuesValidator.class)
public @interface CarlTypeValues {
    String message() default "{com.azet.user.validator.car_type.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
