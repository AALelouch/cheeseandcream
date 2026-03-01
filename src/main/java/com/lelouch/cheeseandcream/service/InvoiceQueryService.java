package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.service.model.InvoiceResponse;
import java.util.List;

public interface InvoiceQueryService {

    InvoiceResponse getInvoiceById(Long invoiceId);
    List<InvoiceResponse> getAllInvoices();
    List<InvoiceResponse> getInvoicesByAgentId(Long agentId);

}
