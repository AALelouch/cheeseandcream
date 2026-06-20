package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.model.operation.FinancialOperationRequest;
import com.lelouch.cheeseandcream.model.operation.FinancialOperationResponse;
import java.util.List;

public interface FinancialOperationService {

    void addOperation(FinancialOperationRequest financialOperationRequest);
    List<FinancialOperationResponse> getOperationsByAgentId(Long idAgent);


}
