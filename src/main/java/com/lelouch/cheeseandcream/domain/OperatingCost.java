package com.lelouch.cheeseandcream.domain;

import java.time.LocalDateTime;

public class OperatingCost {

    private Long id;
    private String concept;
    private Double amount;
    private LocalDateTime creationDate;

    private OperatingCost() {
    }

    public static OperatingCost create(String concept, Double amount) {
        OperatingCost operatingCost = new OperatingCost();
        operatingCost.concept = concept;
        operatingCost.amount = amount;
        return operatingCost;
    }

    public static OperatingCost create(String concept, Double amount, Long id, LocalDateTime creationDate) {
        OperatingCost operatingCost = create(concept, amount);
        operatingCost.id = id;
        operatingCost.creationDate = creationDate;
        return operatingCost;
    }

    public Long getId() {
        return id;
    }

    public String getConcept() {
        return concept;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
