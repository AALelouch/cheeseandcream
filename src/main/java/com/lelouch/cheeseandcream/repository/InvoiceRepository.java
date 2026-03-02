package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.Invoice;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT i FROM Invoice i " +
            "LEFT JOIN FETCH i.agent " +
            "LEFT JOIN FETCH i.products p " +
            "LEFT JOIN FETCH p.product " +
            "WHERE i.id = :id")
    Optional<Invoice> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT DISTINCT i FROM Invoice i " +
            "LEFT JOIN FETCH i.agent " +
            "LEFT JOIN FETCH i.products p " +
            "LEFT JOIN FETCH p.product")
    List<Invoice> findAllWithDetails();

    @Query("SELECT DISTINCT i FROM Invoice i " +
            "LEFT JOIN FETCH i.agent " +
            "LEFT JOIN FETCH i.products p " +
            "LEFT JOIN FETCH p.product " +
            "WHERE i.agent.id = :agentId")
    List<Invoice> findAllByAgentIdWithDetails(@Param("agentId") Long agentId);

    List<Invoice> findByModifiedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
