package com.lelouch.cheeseandcream.infra.financialoperation.adapter;

import com.lelouch.cheeseandcream.application.financialoperation.FindFinancialOperationByAgentIdQuery;
import com.lelouch.cheeseandcream.domain.FinancialOperation;
import com.lelouch.cheeseandcream.infra.financialoperation.persistence.FinancialOperationEntity;
import com.lelouch.cheeseandcream.infra.financialoperation.FinancialOperationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FindFinancialOperationByAgentIdQueryAdapter implements FindFinancialOperationByAgentIdQuery {

    private final FinancialOperationRepository financialOperationRepository;

    public FindFinancialOperationByAgentIdQueryAdapter(FinancialOperationRepository financialOperationRepository) {
        this.financialOperationRepository = financialOperationRepository;
    }


    @Override
    public Page<FinancialOperation> findByAgentId(Long idAgent, Pageable pageable){
        return financialOperationRepository.findAllByAgentEntityIdAndActiveIsTrue(idAgent, pageable).map(FinancialOperationEntity::toDomain);
    }

}
