package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.model.operation.FinancialOperationRequest;
import com.lelouch.cheeseandcream.model.operation.FinancialOperationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FinancialOperationService {

    void addOperation(FinancialOperationRequest financialOperationRequest);
    Page<FinancialOperationResponse> getOperationsByAgentId(Long idAgent, Pageable pageable);


}
