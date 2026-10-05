package com.lelouch.cheeseandcream.infra.financialoperation.adapter;

import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationDetailsResponse;
import com.lelouch.cheeseandcream.application.financialoperation.query.FindDetailsQuery;
import com.lelouch.cheeseandcream.infra.financialoperation.persistence.OperationProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JpaFindFinancialOperationDetailsQueryAdapter implements FindDetailsQuery {

    private final OperationProductRepository operationProductRepository;

    public JpaFindFinancialOperationDetailsQueryAdapter(OperationProductRepository operationProductRepository) {
        this.operationProductRepository = operationProductRepository;
    }

    @Override
    public List<FinancialOperationDetailsResponse.ProductResponse> findDetailsById(Long id) {
        return operationProductRepository.findDetailsByOperationId(id).stream().map(projection -> new FinancialOperationDetailsResponse.ProductResponse(
                projection.getProductId(),
                projection.getProductName(),
                projection.getQuantity(),
                projection.getPrice(),
                projection.getTotalPrice()
        )).toList();
    }
}