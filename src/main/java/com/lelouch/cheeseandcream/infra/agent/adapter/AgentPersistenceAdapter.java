package com.lelouch.cheeseandcream.infra.agent.adapter;

import com.lelouch.cheeseandcream.application.agent.AgentExistsQuery;
import com.lelouch.cheeseandcream.application.agent.AgentTermRequest;
import com.lelouch.cheeseandcream.application.agent.DeactivateAgentCommand;
import com.lelouch.cheeseandcream.application.agent.FindActiveAgentsQuery;
import com.lelouch.cheeseandcream.application.agent.FindAgentsWithProductsQuery;
import com.lelouch.cheeseandcream.application.agent.FindAgentByIdQuery;
import com.lelouch.cheeseandcream.application.agent.SaveAgentCommand;
import com.lelouch.cheeseandcream.application.agent.SearchAgentsByTermQuery;
import com.lelouch.cheeseandcream.domain.Agent;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import com.lelouch.cheeseandcream.infra.agent.persistence.AgentEntity;
import com.lelouch.cheeseandcream.infra.agent.persistence.AgentRepository;
import com.lelouch.cheeseandcream.infra.identificationtype.persistence.IdentificationTypeEntity;
import com.lelouch.cheeseandcream.infra.identificationtype.persistence.IdentificationTypeRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AgentPersistenceAdapter implements SaveAgentCommand, DeactivateAgentCommand, FindAgentByIdQuery,
        FindActiveAgentsQuery, FindAgentsWithProductsQuery, SearchAgentsByTermQuery, AgentExistsQuery {

    private final AgentRepository agentRepository;
    private final IdentificationTypeRepository identificationTypeRepository;

    public AgentPersistenceAdapter(AgentRepository agentRepository, IdentificationTypeRepository identificationTypeRepository) {
        this.agentRepository = agentRepository;
        this.identificationTypeRepository = identificationTypeRepository;
    }

    @Override
    public Agent save(Agent agent) {
        IdentificationTypeEntity identificationType = identificationTypeRepository.findByIdAndActiveIsTrue(agent.getIdentificationTypeId())
                .orElseThrow(() -> new NotFoundException("Identification type not found"));
        AgentEntity entity = AgentEntity.fromDomain(agent);
        entity.setIdentificationTypeEntity(identificationType);
        return agentRepository.save(entity).toDomain();
    }

    @Override
    public void deactivate(Long agentId) {
        AgentEntity agent = agentRepository.findByIdAndActiveIsTrue(agentId)
                .orElseThrow(() -> new NotFoundException("AgentEntity not found"));
        agent.setActive(false);
        agentRepository.save(agent);
    }

    @Override
    public Optional<Agent> findById(Long agentId) {
        return agentRepository.findByIdAndActiveIsTrue(agentId).map(AgentEntity::toDomain);
    }

    @Override
    public Page<Agent> findAll(Pageable pageable) {
        return agentRepository.findAllByActiveIsTrue(pageable).map(AgentEntity::toDomain);
    }

    @Override
    public Page<Agent> findAgentsWithProducts(Pageable pageable) {
        return agentRepository.findActiveAgentsWithProducts(pageable).map(AgentEntity::toDomain);
    }

    @Override
    public Page<Agent> searchByTerm(AgentTermRequest term, Pageable pageable) {
        return agentRepository.searchByTerm(term.term(), pageable).map(AgentEntity::toDomain);
    }

    @Override
    public boolean existsWithSameUniqueData(String name, String email, String address, String identificationNumber) {
        return agentRepository.existsByNameOrEmailOrAddressOrIdentificationNumber(name, email, address, identificationNumber);
    }

    @Override
    public boolean existsWithSameUniqueDataExcludingId(String name, String email, String address,
            String identificationNumber, Long agentId) {
        return agentRepository.existsByNameOrEmailOrAddressOrIdentificationNumberAndIdNot(name, email, address,
                identificationNumber, agentId);
    }
}
