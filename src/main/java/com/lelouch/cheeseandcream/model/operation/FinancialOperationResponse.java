package com.lelouch.cheeseandcream.model.operation;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialOperationResponse {

    private Long id;
    private List<ProductResponse> productResponses;
    private Long idAgent;
    private Double amount;
    private String concept;
    private Double total;
    private OperationType operationType;
    private String date;


    public enum OperationType {
        SALE, PURCHASE
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductResponse {
        private Long id;
        private String name;
        private Double quantity;
        private Double totalPrice;
    }
}
