package com.lelouch.cheeseandcream.service.model.invoice;

import lombok.Builder;

@Builder
public record InvoiceProductResponse(
        Long id,
        String name,
        Double quantity,
        Double price,
        Double unitPrice
) {}
