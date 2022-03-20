package com.vivacon.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class OptionalIntegerValueValidator implements ConstraintValidator<OptionalIntegerValue, Optional<Integer>> {

    private int minValue;

    private int maxValue;

    @Override
    public void initialize(OptionalIntegerValue optionalValue) {
        minValue = optionalValue.min();
        maxValue = optionalValue.max();
    }

    @Override
    public boolean isValid(Optional<Integer> number, ConstraintValidatorContext constraintValidatorContext) {
        if (number.isEmpty()) {
            return true;
        } else {
            Integer actualValue = number.get();
            return actualValue >= minValue && actualValue <= maxValue;
        }
    }
}
