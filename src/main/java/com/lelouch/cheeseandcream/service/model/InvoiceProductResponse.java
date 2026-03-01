package com.lelouch.cheeseandcream.service.model;

import lombok.Builder;

@Builder
public record InvoiceProductResponse(
        Long id,
        String name,
        Double quantity,
        Double price,
        Double unitPrice
) {}
