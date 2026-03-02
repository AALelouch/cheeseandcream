package com.lelouch.cheeseandcream.service.model.invoice;

public record InvoiceProductRequest(
        Long productId,
        Double quantity
) {}
