package com.lelouch.cheeseandcream.service.model.invoice;

import java.util.List;

public record InvoiceRequest(String invoiceType, Long agentId, List<InvoiceProductRequest> products, Boolean hasBalance, Float balanceAmount) {
}
