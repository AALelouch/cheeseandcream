package com.lelouch.cheeseandcream.exception.advice;

import jakarta.persistence.OptimisticLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class OptimisticLockingExceptionAdvice {

    @ExceptionHandler({
            OptimisticLockException.class,
            OptimisticLockingFailureException.class,
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleOptimisticLockingException(Exception ex) {
        return Map.of("error", ex.getMessage());
    }
}
