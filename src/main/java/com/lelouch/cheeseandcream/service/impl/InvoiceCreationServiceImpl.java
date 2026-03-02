package com.lelouch.cheeseandcream.service.impl;

import com.lelouch.cheeseandcream.entity.Agent;
import com.lelouch.cheeseandcream.entity.Invoice;
import com.lelouch.cheeseandcream.entity.InvoiceProduct;
import com.lelouch.cheeseandcream.entity.Product;
import com.lelouch.cheeseandcream.exception.BadRequestException;
import com.lelouch.cheeseandcream.repository.AgentRepository;
import com.lelouch.cheeseandcream.repository.InvoiceRepository;
import com.lelouch.cheeseandcream.repository.ProductRepository;
import com.lelouch.cheeseandcream.service.InvoiceCreationService;
import com.lelouch.cheeseandcream.service.model.invoice.InvoiceProductRequest;
import com.lelouch.cheeseandcream.service.model.invoice.InvoiceRequest;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class InvoiceCreationServiceImpl implements InvoiceCreationService {

    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final AgentRepository agentRepository;

    public InvoiceCreationServiceImpl(InvoiceRepository invoiceRepository, ProductRepository productRepository, AgentRepository agentRepository) {
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
        this.agentRepository = agentRepository;
    }

    @Override
    @Transactional
    public void createInvoice(InvoiceRequest invoiceData) {

        Agent agent = agentRepository.findById(invoiceData.agentId())
                .orElseThrow(() -> new BadRequestException("Agent not found"));

        List<InvoiceProduct> invoiceProducts = new ArrayList<>();

        List<Long> productIds = invoiceData.products().stream()
                .map(InvoiceProductRequest::productId)
                .toList();

        Map<Long, Product> productsMap = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        List<Long> missingIds = productIds.stream()
                .filter(id -> !productsMap.containsKey(id))
                .toList();

        if (!missingIds.isEmpty()) {
            throw new BadRequestException("Products not found: " + missingIds);
        }

        invoiceData.products().forEach(productRequest -> {

            Product product = productsMap.get(productRequest.productId());

            double newQuantity = product.getQuantity() - productRequest.quantity();

            if (newQuantity < 0) {
                throw new BadRequestException("Insufficient stock for product ID " + product.getId());
            }

            product.setQuantity(newQuantity);

            InvoiceProduct invoiceProduct = InvoiceProduct.builder()
                    .product(product)
                    .quantity(productRequest.quantity())
                    .totalPrice(productRequest.quantity() * product.getPrice())
                    .totalProfit(productRequest.quantity() * (product.getPrice() - product.getCost()))
                    .build();


            invoiceProducts.add(invoiceProduct);

        });

        if (invoiceData.hasBalance()) {
            double updatedBalance = agent.getBalance() + invoiceData.balanceAmount();
            agent.setBalance(updatedBalance);
        }

        double totalAmount = invoiceProducts.stream()
                .mapToDouble(InvoiceProduct::getTotalPrice)
                .sum();

        double totalProfit = invoiceProducts.stream()
                .mapToDouble(InvoiceProduct::getTotalProfit)
                .sum();

        Invoice invoice = Invoice.builder()
                .invoiceType(invoiceData.invoiceType())
                .agent(agent)
                .totalAmount(totalAmount)
                .balance(invoiceData.hasBalance() ? invoiceData.balanceAmount() : 0.0)
                .totalProfit(totalProfit)
                .build();

        invoiceProducts.forEach(ip -> ip.setInvoice(invoice));

        invoice.setProducts(invoiceProducts);

        invoiceRepository.save(invoice);

    }
}
