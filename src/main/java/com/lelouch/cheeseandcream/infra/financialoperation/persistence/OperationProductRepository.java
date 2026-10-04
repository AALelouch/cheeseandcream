package com.lelouch.cheeseandcream.infra.financialoperation.persistence;

import com.lelouch.cheeseandcream.infra.financialoperation.persistence.projection.OperationProductDetailsProjection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OperationProductRepository extends JpaRepository<OperationProductEntity, Long> {

    @Query("""
    SELECT
        p.id AS productId,
        op.productName AS productName,
        op.quantity AS quantity,
        op.price AS price,
        op.totalPrice AS totalPrice
    FROM OperationProductEntity op
    JOIN op.productEntity p
    WHERE op.financialOperationEntity.id = :operationId
      AND op.financialOperationEntity.active = true
    """)
    List<OperationProductDetailsProjection> findDetailsByOperationId(Long operationId);

}
