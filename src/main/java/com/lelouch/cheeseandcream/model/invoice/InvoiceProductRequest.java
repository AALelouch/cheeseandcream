package com.lelouch.cheeseandcream.model.invoice;

public record InvoiceProductRequest(
        Long productId,
        Double quantity
) {}
