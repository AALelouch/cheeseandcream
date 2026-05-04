package com.lelouch.cheeseandcream.service.impl;

import com.lelouch.cheeseandcream.entity.invoice.Invoice;
import com.lelouch.cheeseandcream.exception.NotFoundException;
import com.lelouch.cheeseandcream.repository.InvoiceRepository;
import com.lelouch.cheeseandcream.service.InvoiceQueryService;
import com.lelouch.cheeseandcream.model.invoice.InvoiceProductResponse;
import com.lelouch.cheeseandcream.model.invoice.InvoiceResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InvoiceQueryServiceImpl implements InvoiceQueryService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceQueryServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public InvoiceResponse getInvoiceById(Long invoiceId) {
        return invoiceRepository.findByIdWithDetails(invoiceId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new NotFoundException("Invoice not found with id: " + invoiceId));
    }

    @Override
    public List<InvoiceResponse> getAllInvoices() {
        return invoiceRepository.findAllWithDetails().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<InvoiceResponse> getInvoicesByAgentId(Long agentId) {
        return invoiceRepository.findAllByAgentIdWithDetails(agentId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    private InvoiceResponse mapToResponse(Invoice invoice) {
        List<InvoiceProductResponse> products = invoice.getProducts().stream()
                .map(ip -> new InvoiceProductResponse(ip.getId(), ip.getProduct().getName(), ip.getQuantity(), ip.getTotalPrice(),
                        ip.getProduct().getPrice(), ip.getProduct().getUnitType())).toList();

        return new InvoiceResponse(
                invoice.getId(),
                invoice.getInvoiceType(),
                invoice.getAgent().getId(),
                invoice.getAgent().getName(),
                invoice.getTotalAmount(),
                invoice.getTotalProfit(),
                invoice.getBalance(),
                invoice.getCreationDate(),
                invoice.getModifiedDate(),
                products
        );
    }
}
