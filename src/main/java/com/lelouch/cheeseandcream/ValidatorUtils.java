package com.lelouch.cheeseandcream;

import com.lelouch.cheeseandcream.exception.BadRequestException;
import java.util.function.BooleanSupplier;

public class ValidatorUtils {

    public static void validateData(BooleanSupplier validationFunction, String errorMessage) {
        if (validationFunction.getAsBoolean()) {
            throw new BadRequestException(errorMessage);
        }

    }

}
