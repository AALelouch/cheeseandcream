package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.model.invoice.InvoiceRequest;

public interface InvoiceCreationService {

    void createInvoice(InvoiceRequest invoiceData);

}
