package com.lelouch.cheeseandcream.application.financialoperation.dto;

import com.lelouch.cheeseandcream.domain.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialOperationResponse {

    private Long id;
    private Long idAgent;
    private String concept;
    private Double total;
    private OperationType operationType;
    private String date;

}
