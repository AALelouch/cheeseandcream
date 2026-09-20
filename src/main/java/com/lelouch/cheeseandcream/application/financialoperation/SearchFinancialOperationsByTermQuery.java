package com.lelouch.cheeseandcream.application.financialoperation;

import com.lelouch.cheeseandcream.domain.FinancialOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchFinancialOperationsByTermQuery {

    Page<FinancialOperation> searchByTerm(Long agentId, FinancialOperationTermRequest term, Pageable pageable);
}
