package com.lelouch.cheeseandcream.application.agent.query;

import com.lelouch.cheeseandcream.domain.Agent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindActiveAgentsQuery {

    Page<Agent> findAll(Pageable pageable);
}
