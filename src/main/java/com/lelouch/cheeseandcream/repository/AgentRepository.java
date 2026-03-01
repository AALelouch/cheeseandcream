package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.Agent;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findById(Long id);

}
