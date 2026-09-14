package com.lelouch.cheeseandcream.infra.adapter.in;

import com.lelouch.cheeseandcream.application.port.in.AgentRepositoryPort;
import com.lelouch.cheeseandcream.domain.Agent;
import com.lelouch.cheeseandcream.infra.orm.agent.AgentEntity;
import com.lelouch.cheeseandcream.infra.repository.AgentRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AgentRepositoryAdapter implements AgentRepositoryPort{

    private final AgentRepository agentRepository;

    @Override
    public List<Agent> findByModifiedDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return agentRepository.findByModifiedDateBetween(startDate, endDate).stream().map(AgentEntity::toDomain).toList();
    }

    @Override
    public boolean existsByNameOrEmailOrAddressOrIdentificationNumber(String name, String email, String address,
            String identificationNumber) {
        return agentRepository.existsByNameOrEmailOrAddressOrIdentificationNumber(name, email, address, identificationNumber);
    }

    @Override
    public boolean existsByNameOrEmailOrAddressOrIdentificationNumberAndIdNot(String name, String email, String address,
            String identificationNumber, Long id) {
        return agentRepository.existsByNameOrEmailOrAddressOrIdentificationNumberAndIdNot(name, email, address, identificationNumber, id);
    }

    @Override
    public Page<Agent> findAllByActiveIsTrue(Pageable pageable) {
        return agentRepository.findAllByActiveIsTrue(pageable).map(AgentEntity::toDomain);
    }

    @Override
    public List<Agent> findAllByActiveIsTrue() {
        return agentRepository.findAllByActiveIsTrue().stream().map(AgentEntity::toDomain).toList();
    }

    @Override
    public Double getTotalPendingBalance() {
        return agentRepository.getTotalPendingBalance();
    }

    @Override
    public Double getPendingBalanceByAgent(Long agentId) {
        return agentRepository.getPendingBalanceByAgent(agentId);
    }

    @Override
    public Page<Agent> searchByTerm(Pageable pageable, String term) {
        return agentRepository.searchByTerm(term, pageable).map(AgentEntity::toDomain);
    }

    @Override
    public Agent save(Agent agent) {
        return agentRepository.save(AgentEntity.fromDomain(agent)).toDomain();
    }
}
