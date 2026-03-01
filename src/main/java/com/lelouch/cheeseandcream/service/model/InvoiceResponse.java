package com.lelouch.cheeseandcream.service.model;

import java.util.List;
import lombok.Builder;

@Builder
public record InvoiceResponse(
        Long id,
        String date,
        String invoiceType,
        Long agentId,
        String agentName,
        Double totalAmount,
        Double balance,
        List<InvoiceProductResponse> products
) {
}
