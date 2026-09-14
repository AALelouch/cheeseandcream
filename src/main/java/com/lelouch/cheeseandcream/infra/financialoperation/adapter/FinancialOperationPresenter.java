package com.lelouch.cheeseandcream.infra.financialoperation.adapter;

import com.lelouch.cheeseandcream.application.financialoperation.FinancialOperationOutputPort;
import com.lelouch.cheeseandcream.application.financialoperation.FinancialOperationResponse;
import com.lelouch.cheeseandcream.domain.FinancialOperation;
import com.lelouch.cheeseandcream.infra.financialoperation.FinancialOperationMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class FinancialOperationPresenter implements FinancialOperationOutputPort {

    private final FinancialOperationMapper financialOperationMapper;

    public FinancialOperationPresenter(FinancialOperationMapper financialOperationMapper) {
        this.financialOperationMapper = financialOperationMapper;
    }

    @Override
    public Page<FinancialOperationResponse> mapToResponse(Page<FinancialOperation> financialOperations) {
        return financialOperations.map(financialOperationMapper::toResponse);
    }
}
