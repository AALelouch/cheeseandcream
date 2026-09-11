package com.lelouch.cheeseandcream.service.impl;

import com.lelouch.cheeseandcream.mapper.AgentMapper;
import com.lelouch.cheeseandcream.model.agent.AgentResponse;
import com.lelouch.cheeseandcream.repository.AgentRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchAgentByTermQuery implements com.lelouch.cheeseandcream.service.SearchAgentByTermQuery {

    private final AgentRepository agentRepository;
    private final AgentMapper agentMapper;

    public SearchAgentByTermQuery(AgentRepository agentRepository, AgentMapper agentMapper) {
        this.agentRepository = agentRepository;
        this.agentMapper = agentMapper;
    }

    @Override
    public Page<AgentResponse> searchAgentByTerm(String term, Pageable pageable) {

        return agentRepository.searchByTerm(term, pageable)
                .map(agentMapper::toResponse);
    }
}