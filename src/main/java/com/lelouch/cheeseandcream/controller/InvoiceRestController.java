package com.lelouch.cheeseandcream.controller;

import com.lelouch.cheeseandcream.service.InvoiceCreationService;
import com.lelouch.cheeseandcream.service.InvoiceQueryService;
import com.lelouch.cheeseandcream.service.impl.InvoiceQueryServiceImpl;
import com.lelouch.cheeseandcream.model.invoice.InvoiceRequest;
import com.lelouch.cheeseandcream.model.invoice.InvoiceResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceRestController {

    private final InvoiceCreationService invoiceCreationService;
    private final InvoiceQueryService invoiceQueryService;
    private final InvoiceQueryServiceImpl invoiceQueryServiceImpl;

    public InvoiceRestController(InvoiceCreationService invoiceCreationService, InvoiceQueryService invoiceQueryService,
            InvoiceQueryServiceImpl invoiceQueryServiceImpl) {
        this.invoiceCreationService = invoiceCreationService;
        this.invoiceQueryService = invoiceQueryService;
        this.invoiceQueryServiceImpl = invoiceQueryServiceImpl;
    }

    @PostMapping
    public ResponseEntity<Void> createInvoice(@RequestBody InvoiceRequest request) {
        invoiceCreationService.createInvoice(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getInvoices() {
        return new ResponseEntity<>(invoiceQueryServiceImpl.getAllInvoices(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getInvoiceById(@PathVariable Long id) {
        return new ResponseEntity<>(invoiceQueryService.getInvoiceById(id), HttpStatus.OK);
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<InvoiceResponse>> getInvoicesByAgentId(@PathVariable Long agentId) {
        return new ResponseEntity<>(invoiceQueryServiceImpl.getInvoicesByAgentId(agentId), HttpStatus.OK);
    }


}
