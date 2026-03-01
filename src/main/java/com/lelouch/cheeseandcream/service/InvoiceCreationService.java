package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.service.model.InvoiceRequest;

public interface InvoiceCreationService {

    void createInvoice(InvoiceRequest invoiceData);

}
