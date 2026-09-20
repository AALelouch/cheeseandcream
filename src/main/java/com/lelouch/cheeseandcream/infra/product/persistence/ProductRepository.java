package com.lelouch.cheeseandcream.infra.product.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findByAgentEntityIdAndActiveIsTrue(Long agentId, Pageable pageable);
    Optional<ProductEntity> findByIdAndActiveIsTrue(Long id);
    List<ProductEntity> findAllByIdInAndActiveIsTrue(List<Long> productIds);
    boolean existsByNameAndActiveIsTrueAndAgentEntityId(String name, Long  agentId);
    boolean existsByNameAndActiveIsTrueAndIdNotAndAgentEntityId(String name, Long id, Long agentId);

    @Query("""
            SELECT p FROM ProductEntity p
            WHERE p.active = true
              AND p.agentEntity.id = :agentId
              AND LOWER(p.name) LIKE LOWER(CONCAT('%', :term, '%'))
            """)
    Page<ProductEntity> searchByAgentIdAndName(@Param("agentId") Long agentId, @Param("term") String term,
            Pageable pageable);

}
