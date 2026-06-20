package com.lelouch.cheeseandcream.service.impl;

import com.lelouch.cheeseandcream.ValidatorUtils;
import com.lelouch.cheeseandcream.entity.agent.IdentificationType;
import com.lelouch.cheeseandcream.exception.NotFoundException;
import com.lelouch.cheeseandcream.entity.agent.Agent;
import com.lelouch.cheeseandcream.repository.AgentRepository;
import com.lelouch.cheeseandcream.repository.IdentificationTypeRepository;
import com.lelouch.cheeseandcream.service.AgentCrudService;
import com.lelouch.cheeseandcream.mapper.AgentMapper;
import com.lelouch.cheeseandcream.model.agent.AgentRequest;
import com.lelouch.cheeseandcream.model.agent.AgentResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AgentCrudServiceImpl implements AgentCrudService {

    private final AgentRepository agentRepository;
    private final AgentMapper agentMapper;
    private final IdentificationTypeRepository identificationTypeRepository;

    public AgentCrudServiceImpl(AgentRepository agentRepository, AgentMapper agentMapper,
            IdentificationTypeRepository identificationTypeRepository) {
        this.agentRepository = agentRepository;
        this.agentMapper = agentMapper;
        this.identificationTypeRepository = identificationTypeRepository;
    }

    @Override
    public void createAgent(AgentRequest agentData) {

        ValidatorUtils.validateData(() -> agentRepository.existsByNameOrEmailOrAddressOrIdentificationNumber(agentData.name(), agentData.email(),
                agentData.address(), agentData.identificationNumber()), "Agent with the same name, email, address or identification number already exists");

        Agent agent = agentMapper.toEntity(agentData);
        IdentificationType identificationType = identificationTypeRepository.findById(agentData.identificationTypeId())
                .orElseThrow(() -> new NotFoundException("Identification type not found"));
        agent.setIdentificationType(identificationType);

        agentRepository.save(agent);
    }

    @Override
    public void updateAgent(Long agentId, AgentRequest agentData) {

        ValidatorUtils.validateData(() -> agentRepository.existsByNameOrEmailOrAddressOrIdentificationNumberAndIdNot(agentData.name(), agentData.email(),
                agentData.address(), agentData.identificationNumber(), agentId), "Agent with the same name, email, address or identification number already exists");

        Agent agent = agentMapper.toEntity(agentData);
        IdentificationType identificationType = identificationTypeRepository.findById(agentData.identificationTypeId())
                .orElseThrow(() -> new NotFoundException("Identification type not found"));
        agent.setIdentificationType(identificationType);
        agent.setId(agentId);

        agentRepository.save(agent);
    }

    @Override
    public void deleteAgent(Long agentId) {
        Agent agent = agentRepository.findByIdAndActiveIsTrue(agentId).orElseThrow(() -> new NotFoundException("Agent not found"));
        agent.setActive(false);
        agentRepository.save(agent);
    }

    @Override
    public AgentResponse getAgent(Long agentId) {
        return agentRepository.findByIdAndActiveIsTrue(agentId)
                .map(agentMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Agent not found"));
    }

    @Override
    public List<AgentResponse> getAllAgents() {
        return agentRepository.findAllByActiveIsTrue().stream()
                .map(agentMapper::toResponse)
                .toList();
    }
}
