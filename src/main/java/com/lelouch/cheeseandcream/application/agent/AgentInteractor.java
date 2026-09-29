package com.lelouch.cheeseandcream.application.agent;

import com.lelouch.cheeseandcream.application.agent.command.DeactivateAgentCommand;
import com.lelouch.cheeseandcream.application.agent.command.SaveAgentCommand;
import com.lelouch.cheeseandcream.application.agent.dto.AgentRequest;
import com.lelouch.cheeseandcream.application.agent.dto.AgentResponse;
import com.lelouch.cheeseandcream.application.agent.dto.AgentTermRequest;
import com.lelouch.cheeseandcream.application.agent.query.AgentExistsQuery;
import com.lelouch.cheeseandcream.application.agent.query.FindActiveAgentsQuery;
import com.lelouch.cheeseandcream.application.agent.query.FindAgentByIdQuery;
import com.lelouch.cheeseandcream.application.agent.query.SearchAgentsByTermQuery;
import com.lelouch.cheeseandcream.domain.Agent;
import com.lelouch.cheeseandcream.domain.Role;
import com.lelouch.cheeseandcream.domain.exception.BadRequestException;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgentInteractor implements AgentUseCase {

    private final SaveAgentCommand saveAgentCommand;
    private final DeactivateAgentCommand deactivateAgentCommand;
    private final FindAgentByIdQuery findAgentByIdQuery;
    private final FindActiveAgentsQuery findActiveAgentsQuery;
    private final SearchAgentsByTermQuery searchAgentsByTermQuery;
    private final AgentExistsQuery agentExistsQuery;
    private final AgentOutputPort agentOutputPort;

    public AgentInteractor(SaveAgentCommand saveAgentCommand, DeactivateAgentCommand deactivateAgentCommand,
            FindAgentByIdQuery findAgentByIdQuery, FindActiveAgentsQuery findActiveAgentsQuery,
            SearchAgentsByTermQuery searchAgentsByTermQuery, AgentExistsQuery agentExistsQuery,
            AgentOutputPort agentOutputPort) {
        this.saveAgentCommand = saveAgentCommand;
        this.deactivateAgentCommand = deactivateAgentCommand;
        this.findAgentByIdQuery = findAgentByIdQuery;
        this.findActiveAgentsQuery = findActiveAgentsQuery;
        this.searchAgentsByTermQuery = searchAgentsByTermQuery;
        this.agentExistsQuery = agentExistsQuery;
        this.agentOutputPort = agentOutputPort;
    }

    @Override
    @Transactional
    @Caching(evict = {
        @CacheEvict(cacheNames = "agents", allEntries = true),
        @CacheEvict(cacheNames = "agents-by-id", allEntries = true)
    })
    public void createAgent(AgentRequest request) {
        validateUniqueData(request, null);
        saveAgentCommand.save(toDomain(null, request));
    }

    @Override
    @Transactional
    @Caching(evict = {
        @CacheEvict(cacheNames = "agents", allEntries = true),
        @CacheEvict(cacheNames = "agents-by-id", allEntries = true)
    })
    public void updateAgent(Long agentId, AgentRequest request) {
        findAgentByIdQuery.findById(agentId)
                .orElseThrow(() -> new NotFoundException("AgentEntity not found"));
        validateUniqueData(request, agentId);
        saveAgentCommand.save(toDomain(agentId, request));
    }

    @Override
    @Transactional
    @Caching(evict = {
        @CacheEvict(cacheNames = "agents", allEntries = true),
        @CacheEvict(cacheNames = "agents-by-id", allEntries = true)
    })
    public void deleteAgent(Long agentId) {
        deactivateAgentCommand.deactivate(agentId);
    }

    @Override
    @Cacheable(cacheNames = "agents-by-id", key = "#agentId")
    public AgentResponse getAgent(Long agentId) {
        return findAgentByIdQuery.findById(agentId)
                .map(agentOutputPort::mapToResponse)
                .orElseThrow(() -> new NotFoundException("AgentEntity not found"));
    }

    @Override
    @Cacheable(cacheNames = "agents", key = "#role.name() + '-' +#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<AgentResponse> getAllAgents(Pageable pageable, Role role) {
        return agentOutputPort.mapToResponse(findActiveAgentsQuery.findAll(pageable, role));
    }

    @Override
    public Page<AgentResponse> searchAgents(AgentTermRequest term, Role role, Pageable pageable) {
        return agentOutputPort.mapToResponse(searchAgentsByTermQuery.searchByTerm(term, role, pageable));
    }

    private void validateUniqueData(AgentRequest request, Long agentId) {
        boolean duplicated = agentId == null
                ? agentExistsQuery.existsWithSameUniqueData(request.name(), request.email(), request.address(), request.identificationNumber())
                : agentExistsQuery.existsWithSameUniqueDataExcludingId(request.name(), request.email(), request.address(), request.identificationNumber(), agentId);
        if (duplicated) {
            throw new BadRequestException("AgentEntity with the same name, email, address or identification number already exists");
        }
    }

    private Agent toDomain(Long id, AgentRequest request) {

        return Agent.create(id, request.name(), request.email(), request.phoneNumber()
                , request.address(), request.receivables(), request.payables(), request.role(),
                request.identificationNumber(), request.identificationTypeId());
    }
}
