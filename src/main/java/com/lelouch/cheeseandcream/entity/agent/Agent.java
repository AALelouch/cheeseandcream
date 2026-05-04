package com.lelouch.cheeseandcream.entity.agent;

import com.lelouch.cheeseandcream.entity.invoice.Invoice;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Table(name = "agent")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Double balance;
    private String identificationNumber;

    @Column(updatable = false)
    private LocalDateTime creationDate;

    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "agent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Invoice> invoices;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "idenfication_type_id", nullable = false)
    private IdentificationType identificationType;

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
