package com.lelouch.cheeseandcream.model.product;

public record ProductResponse(
        Long id,
        String name,
        Double quantity,
        Double price,
        Double cost,
        String unitType,
        String categoryName
) {}
