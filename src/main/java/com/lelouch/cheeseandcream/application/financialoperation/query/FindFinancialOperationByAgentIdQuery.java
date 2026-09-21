package com.lelouch.cheeseandcream.application.financialoperation.query;

import com.lelouch.cheeseandcream.domain.FinancialOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindFinancialOperationByAgentIdQuery {

    Page<FinancialOperation> findByAgentId(Long idAgent, Pageable pageable);


}
