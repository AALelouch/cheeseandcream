package com.lelouch.cheeseandcream.application.operatingcost.dto;

public record OperatingCostResponse(Long id, String concept, Double amount, String date) {

    public static OperatingCostResponse fromDomain(com.lelouch.cheeseandcream.domain.OperatingCost operatingCost) {
        return new OperatingCostResponse(
                operatingCost.getId(),
                operatingCost.getConcept(),
                operatingCost.getAmount(),
                operatingCost.getCreationDate().toString()
        );
    }

}
