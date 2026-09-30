package com.lelouch.cheeseandcream.infra.operatingcost.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OperatingCostRepository extends JpaRepository<OperatingCostEntity, Long>, JpaSpecificationExecutor<OperatingCostEntity> {


    @Query("""
    SELECT a FROM OperatingCostEntity a\s
    WHERE a.active = true
      AND (LOWER(a.concept) LIKE LOWER(CONCAT('%', :term, '%')))
""")
    Page<OperatingCostEntity> searchByTerm(@Param("term") String term, Pageable pageable);


}
