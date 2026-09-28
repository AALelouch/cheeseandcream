package com.lelouch.cheeseandcream.infra.agent.persistence;

import com.lelouch.cheeseandcream.domain.Role;
import org.springframework.data.jpa.domain.Specification;

public class AgentSpecifications {

    public static Specification<AgentEntity> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("active"));
    }

     public static Specification<AgentEntity> hasRole(Role role) {
        return isActive().and((root, query, criteriaBuilder) -> {
            if (role == null) {
                return criteriaBuilder.conjunction(); // No filtering if role is null
            }
            return criteriaBuilder.equal(root.get("role"), role);
        });
     }

}
