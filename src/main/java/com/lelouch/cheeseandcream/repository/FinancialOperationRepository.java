package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.financial.operation.FinancialOperation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialOperationRepository extends JpaRepository<FinancialOperation, Long> {

    List<FinancialOperation> findAllByAgentId(Long idAgent);

}
