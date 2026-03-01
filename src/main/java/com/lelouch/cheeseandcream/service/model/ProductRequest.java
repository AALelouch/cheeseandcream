package com.lelouch.cheeseandcream.service.model;

public record ProductRequest(
        String name,
        Double quantity,
        Double price,
        Double cost
) {}
