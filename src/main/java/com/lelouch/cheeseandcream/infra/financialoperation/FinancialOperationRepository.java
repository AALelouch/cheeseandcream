package com.lelouch.cheeseandcream.infra.financialoperation;

import com.lelouch.cheeseandcream.infra.financialoperation.persistence.FinancialOperationEntity;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FinancialOperationRepository extends JpaRepository<FinancialOperationEntity, Long> {

    @EntityGraph(attributePaths = {
            "agentEntity",
            "products",
            "products.productEntity",
            "products.productEntity.categoryEntity"
    })
    Page<FinancialOperationEntity> findAllByAgentEntityIdAndActiveIsTrue(Long idAgent, Pageable pageable);

    @Query("SELECT SUM(t.total) FROM FinancialOperationEntity t WHERE t.active = true " +
            "AND t.creationDate >= :startDate AND t.creationDate < :endDate AND t.operationType = 'SALE'")
    Double sumRevenueByTimeRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(t.total) FROM FinancialOperationEntity t WHERE t.active = true and t.creationDate BETWEEN :startDate AND :endDate and t.operationType = 'PURCHASE'")
    Double sumDebtByTimeRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COALESCE(SUM(CASE WHEN f.operationType = 'SALE' THEN f.total " +
            "WHEN f.operationType = 'PAYMENT' THEN -f.total ELSE 0 END), 0) " +
            "FROM FinancialOperationEntity f WHERE f.active = true " +
            "AND f.creationDate >= :startDate AND f.creationDate < :endDate")
    Double sumPendingBalanceByTimeRange(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);

    /**
     * Calculates actual profit by deducting productEntity costs from sales revenue.
     * Profit = SUM(OperationProductEntity.totalPrice - ProductEntity.cost * OperationProductEntity.quantity)
     * Only includes SALE operations.
     *
     * @param startDate Start date of the range
     * @param endDate End date of the range
     * @return Actual profit after considering productEntity costs
     */
    @Query("SELECT COALESCE(SUM(op.totalPrice - (p.cost * op.quantity)), 0) " +
           "FROM FinancialOperationEntity f " +
           "JOIN f.products op " +
           "JOIN op.productEntity p " +
           "WHERE f.active = true AND f.operationType = 'SALE' " +
           "AND f.creationDate >= :startDate AND f.creationDate < :endDate")
    Double sumProfitByTimeRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

     /**
      * Calculates total profit across all time.
      * Profit = SUM(OperationProductEntity.totalPrice - ProductEntity.cost * OperationProductEntity.quantity)
      * Only includes SALE operations.
      *
      * @return Total profit after considering productEntity costs
      */
     @Query("SELECT COALESCE(SUM(op.totalPrice - (p.cost * op.quantity)), 0) " +
            "FROM FinancialOperationEntity f " +
            "JOIN f.products op " +
            "JOIN op.productEntity p " +
            "WHERE f.active = true AND f.operationType = 'SALE'")
     Double sumTotalProfit();


}
