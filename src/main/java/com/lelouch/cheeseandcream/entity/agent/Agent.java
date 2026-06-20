package com.lelouch.cheeseandcream.entity.agent;

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
public class Agent {

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
    private IdentificationType identificationType;

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
}
