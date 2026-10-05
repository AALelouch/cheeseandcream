package com.lelouch.cheeseandcream.infra.product.persistence;

import com.lelouch.cheeseandcream.infra.product.persistence.projection.ProductFullProjection;
import com.lelouch.cheeseandcream.infra.product.persistence.projection.ProductIdNameProjection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByIdAndActiveIsTrue(Long id);
    List<ProductEntity> findAllByIdInAndActiveIsTrue(List<Long> productIds);
    boolean existsByNameAndActiveIsTrueAndAgentEntityId(String name, Long  agentId);
    boolean existsByNameAndActiveIsTrueAndIdNotAndAgentEntityId(String name, Long id, Long agentId);

    @Query("""
            SELECT p.id AS id,
            p.name AS name,
            p.quantity AS quantity,
            p.cost AS cost,
            p.unitType as unitType,
            c.name AS categoryName

            FROM ProductEntity p
            JOIN p.categoryEntity c
            WHERE p.active = true
              AND p.agentEntity.id = :agentId
              AND LOWER(p.name) LIKE LOWER(CONCAT('%', :term, '%'))
            """)
    Page<ProductFullProjection> searchByAgentIdAndName(@Param("agentId") Long agentId, @Param("term") String term,
            Pageable pageable);

    @Query("""
            SELECT p.id AS id,
            p.name AS name, p.quantity as quantity FROM ProductEntity p
            WHERE p.active = true
              AND p.agentEntity.id = :agentId
              AND LOWER(p.name) LIKE LOWER(CONCAT('%', :term, '%'))
""")
    Page<ProductIdNameProjection> searchByAgentIdAndNameAndReturnIdName(@Param("agentId") Long agentId, @Param("term") String term,
            Pageable pageable);


    @Query("""
            SELECT p.id AS id,
            p.name AS name,
            p.quantity as quantity,
            p.cost AS cost,
            p.unitType as unitType,
            c.name AS categoryName

            FROM ProductEntity p
            JOIN p.categoryEntity c
            WHERE p.active = true
              AND p.agentEntity.id = :agentId
""")
    Page<ProductFullProjection> findAllByAgentId(@Param("agentId") Long agentId, Pageable pageable);

}
