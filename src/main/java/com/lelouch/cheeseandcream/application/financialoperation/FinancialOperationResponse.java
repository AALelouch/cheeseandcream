package com.lelouch.cheeseandcream.application.financialoperation;

import com.lelouch.cheeseandcream.domain.OperationType;
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
    private String concept;
    private Double total;
    private OperationType operationType;
    private String date;

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
