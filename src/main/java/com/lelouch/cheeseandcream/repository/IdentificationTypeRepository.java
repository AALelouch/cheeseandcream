package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.agent.IdentificationType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentificationTypeRepository extends JpaRepository<IdentificationType, Long> {

    List<IdentificationType> findAllByActiveIsTrue();
    Optional<IdentificationType> findByIdAndActiveIsTrue(Long id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);


}
