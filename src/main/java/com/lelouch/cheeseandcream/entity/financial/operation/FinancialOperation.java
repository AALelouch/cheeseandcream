package com.lelouch.cheeseandcream.entity.financial.operation;

import com.lelouch.cheeseandcream.entity.agent.Agent;
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
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "financial_operation")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @OneToMany(mappedBy = "financialOperation", fetch = jakarta.persistence.FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OperationProduct> products;

    private Double amount = 0.0;
    private Double total = 0.0;
    private String concept;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    private boolean active = true;


    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;

    public enum OperationType {
        SALE, PURCHASE, PAYMENT;

        public static OperationType fromString(String value) {
            for (OperationType type : OperationType.values()) {
                if (type.name().equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant " + OperationType.class.getCanonicalName() + "." + value);
        }
    }

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }

}
