package com.lelouch.cheeseandcream.service.model;

public record ProductResponse(
        Long id,
        String name,
        Double quantity,
        Double price,
        Double cost
) {}
