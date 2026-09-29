package com.lelouch.cheeseandcream.application.financialoperation.dto;

import com.lelouch.cheeseandcream.domain.OperationType;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialOperationRequest {

    private Map<Long, ProductOperationRequest> products; // productId, quantity
    private Long idAgent;
    @NotNull
    private Double amount;
    private String concept;
    @NotNull
    private OperationType operationType;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductOperationRequest {
        private Double quantity;
        private Double price;
    }

}
