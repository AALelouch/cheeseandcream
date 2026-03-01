package com.lelouch.cheeseandcream.service.model;

public record InvoiceProductRequest(
        Long productId,
        Double quantity
) {}
