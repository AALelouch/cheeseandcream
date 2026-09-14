package com.lelouch.cheeseandcream.domain;

import com.lelouch.cheeseandcream.domain.exception.BadRequestException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class FinancialOperation {

    private Long id;
    private Agent agent;
    private List<OperationProduct> operationProducts;

    private Double total = 0.0;
    private String concept;
    private OperationType operationType;
    private LocalDateTime creationDate;

    private FinancialOperation() {
    }

    public static FinancialOperation create(Agent agent, List<OperationProduct> operationProducts, String concept, OperationType operationType) {
        FinancialOperation operation = new FinancialOperation();
        operation.agent = agent;
        operation.operationProducts = operationProducts;
        operation.concept = concept;
        operation.operationType = operationType;
        return operation;
    }

    public static FinancialOperation create(Agent agent, List<OperationProduct> operationProducts, String concept, OperationType operationType, Double total, Long id, LocalDateTime creationDate) {
        FinancialOperation operation = create(agent, operationProducts, concept, operationType);
        operation.total = total;
        operation.id = id;
        operation.creationDate = creationDate;
        return operation;
    }

    public void performSingleAmountOperation(Double amount) {

        total = amount;

        if (total != null && total > 0) {
            switch (operationType) {
                case SALE, PAYMENT -> agent.decreaseBalance(total);
                case CLIENT_PAYMENT, PURCHASE -> agent.increaseBalance(total);
                default -> throw new BadRequestException("Invalid operation type: " + operationType);
            }
        }else {
            throw new BadRequestException("Amount must be greater than 0 for operation type: " + operationType);
        }

    }

    public void performProductBasedOperation(List<Product> products, Map<Long, Double> productRequested) {


        if (productRequested.size() != products.size()) {
            throw new BadRequestException("Some productEntities not found with ids: " + productRequested.keySet());
        }

        products.forEach(product -> {
            Double quantity = productRequested.get(product.getId());

            if (operationType == OperationType.SALE){
                product.decreaseQuantity(quantity);
            }else if (operationType == OperationType.PURCHASE){
                product.increaseQuantity(quantity);
            }

            operationProducts.add(FinancialOperation.OperationProduct.create(quantity, product.getPrice()*quantity, product));

        });

        this.total = operationProducts.stream().mapToDouble(FinancialOperation.OperationProduct::getTotalPrice).sum();

        switch (operationType) {
            case PURCHASE -> agent.increaseBalance(total);
            case SALE, PAYMENT -> agent.decreaseBalance(total);
            default -> throw new BadRequestException("Invalid operation type: " + operationType);
        }

    }

    public Agent getAgent() {
        return agent;
    }

    public List<OperationProduct> getOperationProducts() {
        return operationProducts;
    }

    public Double getTotal() {
        return total;
    }

    public String getConcept() {
        return concept;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Long getId() {
        return id;
    }

    public static class OperationProduct {

        private Double quantity = 0.0;
        private Double totalPrice= 0.0;
        private Product product;

        private OperationProduct() {
        }

        public static OperationProduct create(Double quantity, Double totalPrice, Product product) {
            OperationProduct operationProduct = new OperationProduct();
            operationProduct.quantity = quantity;
            operationProduct.totalPrice = totalPrice;
            operationProduct.product = product;
            return operationProduct;
        }

        public Double getQuantity() {
            return quantity;
        }

        public Double getTotalPrice() {
            return totalPrice;
        }

        public Product getProduct() {
            return product;
        }
    }

}
