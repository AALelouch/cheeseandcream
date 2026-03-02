package com.lelouch.cheeseandcream.service.impl;

import com.lelouch.cheeseandcream.exception.NotFoundException;
import com.lelouch.cheeseandcream.entity.Agent;
import com.lelouch.cheeseandcream.repository.AgentRepository;
import com.lelouch.cheeseandcream.service.AgentCrudService;
import com.lelouch.cheeseandcream.service.mapper.AgentMapper;
import com.lelouch.cheeseandcream.service.model.agent.AgentRequest;
import com.lelouch.cheeseandcream.service.model.agent.AgentResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AgentCrudServiceImpl implements AgentCrudService {

    private final AgentRepository agentRepository;
    private final AgentMapper agentMapper;

    public AgentCrudServiceImpl(AgentRepository agentRepository, AgentMapper agentMapper) {
        this.agentMapper = agentMapper;
        this.agentRepository = agentRepository;
    }

    @Override
    public void createAgent(AgentRequest agentData) {
        agentRepository.save(agentMapper.requestToEntity(agentData));
    }

    @Override
    public void updateAgent(Long agentId, AgentRequest agentData) {

        Agent agent = agentMapper.requestToEntity(agentData);
        agent.setId(agentId);
        agentRepository.save(agent);

    }

    @Override
    public void deleteAgent(Long agentId) {
        agentRepository.deleteById(agentId);
    }

    @Override
    public AgentResponse getAgent(Long agentId) {
        return agentRepository.findById(agentId)
                .map(agentMapper::entityToResponse)
                .orElseThrow(() -> new NotFoundException("Agent not found"));
    }

    @Override
    public List<AgentResponse> getAllAgents() {
        return agentRepository.findAll().stream()
                .map(agentMapper::entityToResponse)
                .toList();
    }
}
