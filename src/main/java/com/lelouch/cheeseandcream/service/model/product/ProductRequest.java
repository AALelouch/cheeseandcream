package com.lelouch.cheeseandcream.service.model.product;

public record ProductRequest(
        String name,
        Double quantity,
        Double price,
        Double cost
) {}
