package com.lelouch.cheeseandcream.infra.financialoperation.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationProductRepository extends JpaRepository<OperationProductEntity, Long> {
}
