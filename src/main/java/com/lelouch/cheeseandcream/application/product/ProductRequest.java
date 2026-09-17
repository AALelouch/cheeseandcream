package com.lelouch.cheeseandcream.application.product;

public record ProductRequest(
        String name,
        Double quantity,
        Double price,
        Double cost,
        String unitType,
        Long categoryId,
        Long agendId
) {}
