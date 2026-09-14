package com.lelouch.cheeseandcream.application.port.in;

import com.lelouch.cheeseandcream.domain.Agent;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgentRepositoryPort {

    List<Agent> findByModifiedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    boolean existsByNameOrEmailOrAddressOrIdentificationNumber(String name, String email, String address, String identificationNumber);
    boolean existsByNameOrEmailOrAddressOrIdentificationNumberAndIdNot(String name, String email, String address, String identificationNumber, Long id);

    Agent save(Agent agent);
    Page<Agent> findAllByActiveIsTrue(Pageable pageable);
    List<Agent> findAllByActiveIsTrue();

    Double getTotalPendingBalance();
    Double getPendingBalanceByAgent(Long agentId);

    Page<Agent> searchByTerm(Pageable pageable, String term);

}
