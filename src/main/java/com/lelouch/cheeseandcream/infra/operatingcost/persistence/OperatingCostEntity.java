package com.lelouch.cheeseandcream.infra.operatingcost.persistence;

import com.lelouch.cheeseandcream.domain.OperatingCost;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "operating_cost")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperatingCostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String concept;
    private Double amount;
    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;
    private Boolean active = true;

    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }

    public static OperatingCostEntity from(String concept, Double amount) {
        OperatingCostEntity operatingCostEntity = new OperatingCostEntity();
        operatingCostEntity.concept = concept;
        operatingCostEntity.amount = amount;
        return operatingCostEntity;
    }

    public OperatingCost toDomain(){
        return OperatingCost.create(concept, amount, id, creationDate);
    }

}
