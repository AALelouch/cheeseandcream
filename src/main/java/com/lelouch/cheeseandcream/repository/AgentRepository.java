package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.agent.Agent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    List<Agent> findByModifiedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    boolean existsByNameOrEmailOrAddressOrIdentificationNumber(String name, String email, String address, String identificationNumber);
    boolean existsByNameOrEmailOrAddressOrIdentificationNumberAndIdNot(String name, String email, String address, String identificationNumber, Long id);

    Optional<Agent> findByIdAndActiveIsTrue(Long agentId);
    List<Agent> findAllByActiveIsTrue();
}
