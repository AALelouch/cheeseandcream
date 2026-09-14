package com.lelouch.cheeseandcream.domain;

public class Agent {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Double balance = 0.0;
    private String identificationNumber;

    private Agent() {
    }

    public static Agent create(Long id, String name, String email, String phoneNumber, String address, Double balance, String identificationNumber) {
        Agent agent = new Agent();
        agent.id = id;
        agent.name = name;
        agent.email = email;
        agent.phoneNumber = phoneNumber;
        agent.address = address;
        agent.balance = balance;
        agent.identificationNumber = identificationNumber;
        return agent;
    }

    public void increaseBalance(Double balance) {
        this.balance += balance;
    }

    public void decreaseBalance(Double balance) {
        this.balance -= balance;
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

    public Double getBalance() {
        return balance;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }
}
