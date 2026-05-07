package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.financial.operation.OperationProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationProductRepository extends JpaRepository<OperationProduct, Long> {
}
