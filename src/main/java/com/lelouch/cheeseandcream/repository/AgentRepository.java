package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.agent.Agent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    Optional<Agent> findById(Long id);

    List<Agent> findByModifiedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
