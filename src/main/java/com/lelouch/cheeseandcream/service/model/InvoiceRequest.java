package com.lelouch.cheeseandcream.service.model;

import java.util.List;

public record InvoiceRequest(String invoiceType, Long agentId, List<InvoiceProductRequest> products, boolean hasBalance, double balanceAmount) {
}
