package com.lelouch.cheeseandcream.application.financialoperation.query;

import com.lelouch.cheeseandcream.application.financialoperation.dto.FinancialOperationTermRequest;
import com.lelouch.cheeseandcream.domain.FinancialOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchFinancialOperationsByTermQuery {

    Page<FinancialOperation> searchByTerm(Long agentId, FinancialOperationTermRequest term, Pageable pageable);
}
