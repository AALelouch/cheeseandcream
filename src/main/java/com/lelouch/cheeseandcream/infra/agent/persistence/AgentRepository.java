package com.lelouch.cheeseandcream.infra.agent.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AgentRepository extends JpaRepository<AgentEntity, Long> {

    List<AgentEntity> findByModifiedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("""
            SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AgentEntity a
            WHERE a.active = true AND (a.name = :name OR a.email = :email OR a.address = :address
            OR a.identificationNumber = :identificationNumber)
            """)
    boolean existsByNameOrEmailOrAddressOrIdentificationNumber(@Param("name") String name, @Param("email") String email,
            @Param("address") String address, @Param("identificationNumber") String identificationNumber);

    @Query("""
            SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AgentEntity a
            WHERE a.active = true AND a.id <> :id AND (a.name = :name OR a.email = :email OR a.address = :address
            OR a.identificationNumber = :identificationNumber)
            """)
    boolean existsByNameOrEmailOrAddressOrIdentificationNumberAndIdNot(@Param("name") String name, @Param("email") String email,
            @Param("address") String address, @Param("identificationNumber") String identificationNumber, @Param("id") Long id);

    Optional<AgentEntity> findByIdAndActiveIsTrue(Long agentId);
    Page<AgentEntity> findAllByActiveIsTrue(Pageable pageable);
    List<AgentEntity> findAllByActiveIsTrue();


    /**
     * Calculates the total pending balance (Accounts Receivable) across all active agents.
     * Returns the sum of all active agents' balance field.
     *
     * @return Total pending balance from all agents
     */
    @Query("SELECT COALESCE(SUM(a.balance), 0) " +
           "FROM AgentEntity a " +
           "WHERE a.active = true")
    Double getTotalPendingBalance();

    /**
     * Calculates the pending balance for a specific agentEntity/customer.
     * Returns the agentEntity's balance field directly.
     *
     * @param agentId ID of the agentEntity/customer
     * @return Pending balance from agentEntity's balance field
     */
    @Query("SELECT COALESCE(a.balance, 0) " +
           "FROM AgentEntity a " +
           "WHERE a.active = true AND a.id = :agentId")
    Double getPendingBalanceByAgent(@Param("agentId") Long agentId);

    @Query("""
    SELECT a FROM AgentEntity a\s
    WHERE a.active = true\s
      AND (LOWER(a.name) LIKE LOWER(CONCAT('%', :term, '%'))
       OR LOWER(a.email) LIKE LOWER(CONCAT('%', :term, '%'))
       OR LOWER(a.address) LIKE LOWER(CONCAT('%', :term, '%'))
       OR LOWER(a.identificationNumber) LIKE LOWER(CONCAT('%', :term, '%')))
""")
    Page<AgentEntity> searchByTerm(@Param("term") String term, Pageable pageable);
}


