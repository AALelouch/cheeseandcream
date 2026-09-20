package com.lelouch.cheeseandcream.application.financialoperation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FinancialOperationUseCase {

    void addOperation(FinancialOperationRequest financialOperationRequest);
    Page<FinancialOperationResponse> getOperationsByAgentId(Long idAgent, Pageable pageable);
    Page<FinancialOperationResponse> searchOperations(Long agentId, FinancialOperationTermRequest term,
            Pageable pageable);


}
