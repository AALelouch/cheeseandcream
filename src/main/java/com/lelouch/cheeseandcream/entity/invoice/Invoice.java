package com.lelouch.cheeseandcream.entity.invoice;

import com.lelouch.cheeseandcream.entity.agent.Agent;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "invoice")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invoiceType;
    private Double balance;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @OneToMany(mappedBy = "invoice", fetch = jakarta.persistence.FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InvoiceProduct> products;

    private Double totalAmount;
    private Double totalProfit;

    @Column(updatable = false)
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
}
