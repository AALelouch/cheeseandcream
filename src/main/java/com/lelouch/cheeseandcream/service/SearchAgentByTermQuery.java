package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.model.agent.AgentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchAgentByTermQuery {

    Page<AgentResponse> searchAgentByTerm(String term, Pageable pageable);

}
