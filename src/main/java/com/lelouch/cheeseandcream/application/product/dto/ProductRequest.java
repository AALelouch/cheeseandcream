package com.lelouch.cheeseandcream.application.product.dto;

public record ProductRequest(
        String name,
        Double quantity,
        Double cost,
        String unitType,
        Long categoryId,
        Long agendId
) {}
