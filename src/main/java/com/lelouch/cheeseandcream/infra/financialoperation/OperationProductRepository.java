package com.lelouch.cheeseandcream.infra.financialoperation;

import com.lelouch.cheeseandcream.infra.financialoperation.persistence.OperationProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationProductRepository extends JpaRepository<OperationProductEntity, Long> {
}
