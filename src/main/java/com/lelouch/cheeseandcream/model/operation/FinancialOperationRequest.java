package com.lelouch.cheeseandcream.model.operation;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialOperationRequest {

    private HashMap<Long, Double> products; // productId, quantity
    private Long idAgent;
    private Double amount;
    private String concept;
    private OperationType operationType;

    public enum OperationType {
        SALE, PURCHASE
    }

}
