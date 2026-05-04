package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.model.invoice.InvoiceResponse;
import java.util.List;

public interface InvoiceQueryService {

    InvoiceResponse getInvoiceById(Long invoiceId);
    List<InvoiceResponse> getAllInvoices();
    List<InvoiceResponse> getInvoicesByAgentId(Long agentId);

}
