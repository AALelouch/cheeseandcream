package com.lelouch.cheeseandcream.domain;

import com.lelouch.cheeseandcream.domain.exception.BadRequestException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        if (amount != null && amount > 0) {
            switch (operationType) {
                case SALE -> agent.increaseReceivables(amount);
                case PAYMENT -> agent.decreasePayables(amount);
                case CLIENT_PAYMENT -> agent.decreaseReceivables(amount);
                case PURCHASE -> agent.increasePayables(amount);
                default -> throw new BadRequestException("Invalid operation type while performing single amount operation: " + operationType);
            }
        }else {
            throw new BadRequestException("Amount must be greater than 0 for operation type: " + operationType);
        }

        this.total = amount;

    }

    public void performProductBasedOperation(List<Product> products, Map<Long, OperationProduct> operationProducts) {


        if (operationProducts.size() != products.size()) {

            Map<Long, Product> allProductsMap = products.stream().collect(Collectors.toMap(Product::getId, product -> product));

            throw new BadRequestException("Some productEntities not found with ids: " +
                    operationProducts.keySet().stream().filter(id -> !allProductsMap.containsKey(id)).toList());

        }

        products.forEach(product -> {
            OperationProduct operationProduct = operationProducts.get(product.getId());
            Double quantity = operationProduct.getQuantity();

            switch (operationType) {
                case PURCHASE -> product.increaseQuantity(quantity);
                case SALE ->  product.decreaseQuantity(quantity);
                default -> throw new BadRequestException("Invalid operation type for changing inventory products at : " + operationType);
            }


            operationProduct.linkProduct(product);
            this.operationProducts.add(operationProduct);

        });

        this.total = this.operationProducts.stream().mapToDouble(FinancialOperation.OperationProduct::getTotalPrice).sum();

        switch (operationType) {
            case PURCHASE -> agent.increasePayables(total);
            case SALE -> agent.increaseReceivables(total);
            default -> throw new BadRequestException("Invalid operation type for increasing payables or receivables: " + operationType);
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

    public final static class OperationProduct {

        private Double quantity = 0.0;
        private Double totalPrice= 0.0;
        private Double price = 0.0;
        private Product product;

        private OperationProduct() {
        }

        public static OperationProduct create(Double quantity, Double price) {
            OperationProduct operationProduct = new OperationProduct();
            operationProduct.quantity = quantity;
            operationProduct.totalPrice = quantity * price;
            operationProduct.price = price;
            return operationProduct;
        }

        public static OperationProduct create(Double quantity, Double price, Product product) {
            OperationProduct operationProduct = create(quantity, price);
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

        public Double getPrice() {
            return price;
        }

        private void linkProduct(Product product) {
            this.product = product;
        }

    }

}
