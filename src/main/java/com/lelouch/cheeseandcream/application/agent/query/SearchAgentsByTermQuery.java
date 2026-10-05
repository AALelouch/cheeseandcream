package com.lelouch.cheeseandcream.application.agent.query;

import com.lelouch.cheeseandcream.application.agent.dto.AgentIdNameResponse;
import com.lelouch.cheeseandcream.application.agent.dto.AgentTermRequest;
import com.lelouch.cheeseandcream.domain.Agent;
import com.lelouch.cheeseandcream.domain.Role;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchAgentsByTermQuery {

    Page<Agent> searchByTerm(AgentTermRequest term, Role role, Pageable pageable);
    Page<AgentIdNameResponse> searchByTermIdName(AgentTermRequest term, Role role, Pageable pageable);
}
