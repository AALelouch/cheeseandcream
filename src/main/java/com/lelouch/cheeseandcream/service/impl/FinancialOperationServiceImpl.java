package com.lelouch.cheeseandcream.service.impl;

import com.lelouch.cheeseandcream.entity.agent.Agent;
import com.lelouch.cheeseandcream.entity.financial.operation.FinancialOperation;
import com.lelouch.cheeseandcream.entity.financial.operation.OperationProduct;
import com.lelouch.cheeseandcream.entity.product.Product;
import com.lelouch.cheeseandcream.exception.BadRequestException;
import com.lelouch.cheeseandcream.exception.NotFoundException;
import com.lelouch.cheeseandcream.mapper.FinancialOperationMapper;
import com.lelouch.cheeseandcream.model.operation.FinancialOperationRequest;
import com.lelouch.cheeseandcream.model.operation.FinancialOperationResponse;
import com.lelouch.cheeseandcream.repository.AgentRepository;
import com.lelouch.cheeseandcream.repository.FinancialOperationRepository;
import com.lelouch.cheeseandcream.repository.ProductRepository;
import com.lelouch.cheeseandcream.service.FinancialOperationService;
import jakarta.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FinancialOperationServiceImpl implements FinancialOperationService {

    private final FinancialOperationRepository financialOperationRepository;
    private final AgentRepository agentRepository;
    private final ProductRepository productRepository;
    private final FinancialOperationMapper financialOperationMapper;

    public FinancialOperationServiceImpl(FinancialOperationRepository financialOperationRepository, AgentRepository agentRepository, ProductRepository productRepository,
            FinancialOperationMapper financialOperationMapper) {
        this.financialOperationRepository = financialOperationRepository;
        this.agentRepository = agentRepository;
        this.productRepository = productRepository;
        this.financialOperationMapper = financialOperationMapper;
    }

    @Override
    @Transactional
    public void addOperation(FinancialOperationRequest financialOperationRequest) {


        Agent agent = agentRepository.findByIdAndActiveIsTrue(financialOperationRequest.getIdAgent())
                .orElseThrow(() -> new NotFoundException("Agent not found with id: " + financialOperationRequest.getIdAgent()));

        FinancialOperation financialOperation = new FinancialOperation();
        financialOperation.setOperationType(FinancialOperation.OperationType.fromString(financialOperationRequest.getOperationType().name()));
        financialOperation.setAgent(agent);
        financialOperation.setConcept(financialOperationRequest.getConcept());
        financialOperation.setAmount(financialOperationRequest.getAmount());

        if (financialOperationRequest.getProducts() == null) {
            throw new BadRequestException("Products list must not be null; send an empty list when there are no products");
        }

        boolean hasProducts = !financialOperationRequest.getProducts().isEmpty();
        Double amount = financialOperationRequest.getAmount();

        if (hasProducts) {
            if (amount == null || amount != 0) {
                throw new BadRequestException("Amount must be 0 when products are provided");
            }
            performProductBasedOperation(financialOperation, agent, financialOperationRequest);
            return;
        }

        if (amount == null || amount <= 0) {
            throw new BadRequestException("Amount must be greater than 0 when no products are provided");
        }
        performSingleAmountOperation(financialOperation, agent, financialOperationRequest);
    }

    private void performSingleAmountOperation(FinancialOperation financialOperation, Agent agent, FinancialOperationRequest financialOperationRequest){

        FinancialOperationRequest.OperationType operationType = financialOperationRequest.getOperationType();
        financialOperation.setTotal(financialOperationRequest.getAmount());

        if (financialOperationRequest.getAmount() != null && financialOperationRequest.getAmount() > 0) {
            switch (operationType) {
                case SALE, PAYMENT -> agent.setBalance(agent.getBalance() - financialOperationRequest.getAmount());
                case PURCHASE -> agent.setBalance(agent.getBalance() + financialOperationRequest.getAmount());
                default -> throw new BadRequestException("Invalid operation type: " + operationType);
            }
        }

        financialOperationRepository.save(financialOperation);
    }

    private void performProductBasedOperation(FinancialOperation financialOperation, Agent agent, FinancialOperationRequest financialOperationRequest){

        List<Long> productIds = financialOperationRequest.getProducts().keySet().stream().toList();
        List<Product> products = productRepository.findAllByActiveIsTrue(productIds);

        if (products.size() != productIds.size()) {
            throw new BadRequestException("Some products not found with ids: " + productIds);
        }

        FinancialOperationRequest.OperationType operationType = financialOperationRequest.getOperationType();

        List<OperationProduct> operationProducts = new LinkedList<>();

        products.forEach(product -> {
            Double quantity = financialOperationRequest.getProducts().get(product.getId());

            if (quantity == null || quantity <= 0) {
                throw new BadRequestException("Invalid quantity for product with id: " + product.getId());
            }

            if (operationType == FinancialOperationRequest.OperationType.SALE
                    && product.getQuantity() < quantity) {
                throw new BadRequestException("Insufficient stock for product with id: " + product.getId());
            }

            double newQuantity = operationType == FinancialOperationRequest.OperationType.SALE
                    ? product.getQuantity() - quantity
                    : product.getQuantity() + quantity;

            product.setQuantity(newQuantity);
            operationProducts.add(new OperationProduct(null, quantity, product.getPrice()*quantity, financialOperation, product));

        });

        double total = operationProducts.stream().mapToDouble(OperationProduct::getTotalPrice).sum();

        switch (operationType) {
            case PURCHASE -> agent.setBalance(agent.getBalance() + total);
            case SALE, PAYMENT -> agent.setBalance(agent.getBalance() - total);
            default -> throw new BadRequestException("Invalid operation type: " + operationType);
        }

        financialOperation.setTotal(total);

        financialOperation.setProducts(operationProducts);
        financialOperationRepository.save(financialOperation);
    }

    @Override
    public List<FinancialOperationResponse> getOperationsByAgentId(Long idAgent) {
        return financialOperationRepository.findAllByAgentIdAndActiveIsTrue(idAgent).stream()
                .map(financialOperationMapper::toResponse)
                .toList();
    }
}
