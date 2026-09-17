package com.lelouch.cheeseandcream.application.agent;

import com.lelouch.cheeseandcream.domain.Agent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchAgentsByTermQuery {

    Page<Agent> searchByTerm(String term, Pageable pageable);
}
