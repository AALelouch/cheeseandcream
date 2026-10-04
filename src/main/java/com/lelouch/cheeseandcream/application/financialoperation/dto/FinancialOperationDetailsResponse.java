package com.lelouch.cheeseandcream.application.financialoperation.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialOperationDetailsResponse {

    List<ProductResponse> products;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductResponse {
        private Long id;
        private String name;
        private Double quantity;
        private Double price;
        private Double totalPrice;
    }

}