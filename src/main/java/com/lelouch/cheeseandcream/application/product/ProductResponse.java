package com.lelouch.cheeseandcream.application.product;

public record ProductResponse(
        Long id,
        String name,
        Double quantity,
        Double price,
        Double cost,
        String unitType,
        String categoryName,
        String agentName
) {}
