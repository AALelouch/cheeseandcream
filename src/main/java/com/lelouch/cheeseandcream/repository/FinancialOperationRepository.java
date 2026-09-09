package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.financial.operation.FinancialOperation;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FinancialOperationRepository extends JpaRepository<FinancialOperation, Long> {

    Page<FinancialOperation> findAllByAgentIdAndActiveIsTrue(Long idAgent, Pageable pageable);

    @Query("SELECT SUM(t.total) FROM FinancialOperation t WHERE t.active = true and t.creationDate BETWEEN :startDate AND :endDate and t.operationType = 'SALE'")
    Double sumRevenueByTimeRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(t.total) FROM FinancialOperation t WHERE t.active = true and t.creationDate BETWEEN :startDate AND :endDate and t.operationType = 'PURCHASE'")
    Double sumDebtByTimeRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Calculates actual profit by deducting product costs from sales revenue.
     * Profit = SUM(OperationProduct.totalPrice - Product.cost * OperationProduct.quantity)
     * Only includes SALE operations.
     *
     * @param startDate Start date of the range
     * @param endDate End date of the range
     * @return Actual profit after considering product costs
     */
    @Query("SELECT COALESCE(SUM(op.totalPrice - (p.cost * op.quantity)), 0) " +
           "FROM FinancialOperation f " +
           "JOIN f.products op " +
           "JOIN op.product p " +
           "WHERE f.active = true AND f.operationType = 'SALE' " +
           "AND f.creationDate BETWEEN :startDate AND :endDate")
    Double sumProfitByTimeRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

     /**
      * Calculates total profit across all time.
      * Profit = SUM(OperationProduct.totalPrice - Product.cost * OperationProduct.quantity)
      * Only includes SALE operations.
      *
      * @return Total profit after considering product costs
      */
     @Query("SELECT COALESCE(SUM(op.totalPrice - (p.cost * op.quantity)), 0) " +
            "FROM FinancialOperation f " +
            "JOIN f.products op " +
            "JOIN op.product p " +
            "WHERE f.active = true AND f.operationType = 'SALE'")
     Double sumTotalProfit();


}
