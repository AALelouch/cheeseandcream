package com.lelouch.cheeseandcream.domain;

public class Agent {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Double receivables = 0.0;
    private Double payables = 0.0;
    private Role role;
    private String identificationNumber;
    private Long identificationTypeId;

    private Agent() {
    }

    public static Agent create(Long id, String name, String email, String phoneNumber, String address,
            Double receivables, Double payables, Role role, String identificationNumber,
            Long identificationTypeId) {

        Agent agent = new Agent();
        agent.id = id;
        agent.name = name;
        agent.email = email;
        agent.phoneNumber = phoneNumber;
        agent.address = address;
        agent.receivables = receivables;
        agent.payables = payables;
        agent.role = role;
        agent.identificationNumber = identificationNumber;
        agent.identificationTypeId = identificationTypeId;
        return agent;
    }

    public Double getBalance() {
        return receivables - payables;
    }

    public void increasePayables(Double value) {
        this.payables += value;
    }

    public void decreasePayables(Double value) {
        this.payables -= value;
    }

    public void increaseReceivables(Double value) {
        this.receivables += value;
    }

    public void decreaseReceivables(Double value) {
        this.receivables -= value;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public Long getIdentificationTypeId() {
        return identificationTypeId;
    }

    public Double getReceivables() {
        return receivables;
    }

    public Double getPayables() {
        return payables;
    }

    public Role getRole() {
        return role;
    }
}
