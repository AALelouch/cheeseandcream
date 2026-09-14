package com.lelouch.cheeseandcream.domain;

import com.lelouch.cheeseandcream.domain.exception.BadRequestException;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public class ValidatorUtils {

    public static void validateData(BooleanSupplier validationFunction, String errorMessage) {
        if (validationFunction.getAsBoolean()) {
            throw new BadRequestException(errorMessage);
        }

    }

    public static void validateData(Predicate<Double> predicate, Double value,String errorMessage) {
        if (predicate.test(value)) {
            throw new BadRequestException(errorMessage);
        }

    }

}
