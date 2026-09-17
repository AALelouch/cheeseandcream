package com.lelouch.cheeseandcream.infra.identificationtype.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentificationTypeRepository extends JpaRepository<IdentificationTypeEntity, Long> {

    List<IdentificationTypeEntity> findAllByActiveIsTrue();
    Optional<IdentificationTypeEntity> findByIdAndActiveIsTrue(Long id);
    boolean existsByNameAndActiveIsTrue(String name);
    boolean existsByNameAndActiveIsTrueAndIdNot(String name, Long id);


}
