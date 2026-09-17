package com.lelouch.cheeseandcream.infra.agent.persistence;

import com.lelouch.cheeseandcream.domain.Agent;
import com.lelouch.cheeseandcream.infra.identificationtype.persistence.IdentificationTypeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "agent")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    @Column(unique = true)
    private String address;
    private Double balance = 0.0;
    @Column(unique = true)
    private String identificationNumber;
    private boolean active = true;

    @Column(updatable = false)
    private LocalDateTime creationDate;

    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "idenfication_type_id", nullable = false)
    private IdentificationTypeEntity identificationTypeEntity;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
        this.active = true;
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }

    public Agent toDomain() {
        return Agent.create(
                this.id,
                this.name,
                this.email,
                this.phoneNumber,
                this.address,
                this.balance,
                this.identificationNumber,
                this.identificationTypeEntity == null ? null : this.identificationTypeEntity.getId()
        );
    }

    public static AgentEntity fromDomain(Agent agent) {
        AgentEntity agentEntity = new AgentEntity();
        agentEntity.setId(agent.getId());
        agentEntity.setName(agent.getName());
        agentEntity.setEmail(agent.getEmail());
        agentEntity.setPhoneNumber(agent.getPhoneNumber());
        agentEntity.setAddress(agent.getAddress());
        agentEntity.setBalance(agent.getBalance());
        agentEntity.setIdentificationNumber(agent.getIdentificationNumber());
        return agentEntity;
    }
}
