package com.lelouch.cheeseandcream.infra.financialoperation.persistence;

import com.lelouch.cheeseandcream.domain.FinancialOperation;
import com.lelouch.cheeseandcream.domain.OperationType;
import com.lelouch.cheeseandcream.infra.agent.persistence.AgentEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "financial_operation")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialOperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private AgentEntity agentEntity;

    @OneToMany(mappedBy = "financialOperationEntity", fetch = jakarta.persistence.FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<OperationProductEntity> products = new ArrayList<>();

    private Double total = 0.0;
    private String concept;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    private boolean active = true;


    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }

    public void addProduct(OperationProductEntity product) {
        products.add(product);
        product.setFinancialOperationEntity(this);
    }

    public FinancialOperation toDomain() {
        List<FinancialOperation.OperationProduct> operationProducts = products.stream()
                .map(OperationProductEntity::toDomain)
                .toList();

        return FinancialOperation.create(agentEntity.toDomain(), operationProducts, concept, operationType, total, id, creationDate);
    }

}
