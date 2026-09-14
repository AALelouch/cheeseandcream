package com.lelouch.cheeseandcream.application.service.impl;

import com.lelouch.cheeseandcream.domain.ValidatorUtils;
import com.lelouch.cheeseandcream.infra.orm.agent.IdentificationTypeEntity;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import com.lelouch.cheeseandcream.infra.orm.agent.AgentEntity;
import com.lelouch.cheeseandcream.infra.repository.AgentRepository;
import com.lelouch.cheeseandcream.infra.repository.IdentificationTypeRepository;
import com.lelouch.cheeseandcream.application.service.AgentCrudService;
import com.lelouch.cheeseandcream.infra.mapper.AgentMapper;
import com.lelouch.cheeseandcream.application.model.agent.AgentRequest;
import com.lelouch.cheeseandcream.application.model.agent.AgentResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @CacheEvict(cacheNames = "agents", allEntries = true)
    public void createAgent(AgentRequest agentData) {

        ValidatorUtils.validateData(() -> agentRepository.existsByNameOrEmailOrAddressOrIdentificationNumber(agentData.name(), agentData.email(),
                agentData.address(), agentData.identificationNumber()), "AgentEntity with the same name, email, address or identification number already exists");

        AgentEntity agentEntity = agentMapper.toEntity(agentData);
        IdentificationTypeEntity identificationTypeEntity = identificationTypeRepository.findById(agentData.identificationTypeId())
                .orElseThrow(() -> new NotFoundException("Identification type not found"));
        agentEntity.setIdentificationTypeEntity(identificationTypeEntity);

        agentRepository.save(agentEntity);
    }

    @Override
    @CacheEvict(cacheNames = "agents", allEntries = true)
    public void updateAgent(Long agentId, AgentRequest agentData) {

        ValidatorUtils.validateData(() -> agentRepository.existsByNameOrEmailOrAddressOrIdentificationNumberAndIdNot(agentData.name(), agentData.email(),
                agentData.address(), agentData.identificationNumber(), agentId), "AgentEntity with the same name, email, address or identification number already exists");

        AgentEntity agentEntity = agentRepository.findById(agentId).orElseThrow(() -> new NotFoundException("AgentEntity not found"));
        IdentificationTypeEntity identificationTypeEntity = identificationTypeRepository.findById(agentData.identificationTypeId())
                .orElseThrow(() -> new NotFoundException("Identification type not found"));
        agentEntity.setIdentificationTypeEntity(identificationTypeEntity);
        agentEntity.setId(agentId);
        agentEntity.setBalance(Double.valueOf(agentData.balance()));
        agentEntity.setName(agentData.name());
        agentEntity.setEmail(agentData.email());
        agentEntity.setAddress(agentData.address());
        agentEntity.setIdentificationNumber(agentData.identificationNumber());

        agentRepository.save(agentEntity);
    }

    @Override
    @CacheEvict(cacheNames = "agents", allEntries = true)
    public void deleteAgent(Long agentId) {
        AgentEntity agentEntity = agentRepository.findByIdAndActiveIsTrue(agentId).orElseThrow(() -> new NotFoundException("AgentEntity not found"));
        agentEntity.setActive(false);
        agentRepository.save(agentEntity);
    }

    @Override
    @Cacheable(cacheNames = "agents-by-id", key = "#agentId")
    public AgentResponse getAgent(Long agentId) {
        return agentRepository.findByIdAndActiveIsTrue(agentId)
                .map(agentMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("AgentEntity not found"));
    }

    @Override
    @Cacheable(cacheNames = "agents", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<AgentResponse> getAllAgents(Pageable pageable) {
        return agentRepository.findAllByActiveIsTrue(pageable).map(agentMapper::toResponse);
    }
}