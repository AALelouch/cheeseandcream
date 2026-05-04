package com.lelouch.cheeseandcream.model.invoice;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record InvoiceResponse(
        Long id,
        String invoiceType,
        Long agentId,
        String agentName,
        Double totalAmount,
        Double totalProfit,
        Double balance,
        LocalDateTime creationDate,
        LocalDateTime modifiedDate,
        List<InvoiceProductResponse> products
) {
}
