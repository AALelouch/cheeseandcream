package com.lelouch.cheeseandcream.infra.repository;

import com.lelouch.cheeseandcream.infra.orm.agent.IdentificationTypeEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentificationTypeRepository extends JpaRepository<IdentificationTypeEntity, Long> {

    List<IdentificationTypeEntity> findAllByActiveIsTrue();
    Optional<IdentificationTypeEntity> findByIdAndActiveIsTrue(Long id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);


}
