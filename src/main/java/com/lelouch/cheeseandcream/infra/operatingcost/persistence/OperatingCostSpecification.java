package com.lelouch.cheeseandcream.infra.operatingcost.persistence;

import jakarta.persistence.criteria.Path;
import java.time.LocalDateTime;
import java.time.YearMonth;
import org.springframework.data.jpa.domain.Specification;

public class OperatingCostSpecification {

    public static Specification<OperatingCostEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("active"), true);
    }

    public static Specification<OperatingCostEntity> isActiveAndCreatedInMonth(
            int month,
            int year
    ) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endExclusive = yearMonth.plusMonths(1).atDay(1).atStartOfDay();

        return isActive().and((root, query, criteriaBuilder) -> {
            Path<LocalDateTime> creationDate = root.get("creationDate");

            return criteriaBuilder.and(criteriaBuilder.isNotNull(creationDate), criteriaBuilder.greaterThanOrEqualTo(creationDate, start),
                    criteriaBuilder.lessThan(creationDate, endExclusive));
        });
    }
}
