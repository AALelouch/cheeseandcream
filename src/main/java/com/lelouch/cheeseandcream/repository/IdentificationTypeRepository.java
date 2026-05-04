package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.agent.IdentificationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentificationTypeRepository extends JpaRepository<IdentificationType, Long> {
}
