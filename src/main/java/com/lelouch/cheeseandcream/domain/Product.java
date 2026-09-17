package com.lelouch.cheeseandcream.domain;

public class Product {

    private Long id;
    private String name;
    private Double quantity = 0.0;
    private Double price = 0.0;
    private Double cost = 0.0;
    private String unitType;
    private Category category;
    private Agent agent;

    private Product() {
    }

    public static Product create(Long id, String name, Double quantity, Double price, Double cost, String unitType, Category category) {
        return create(id, name, quantity, price, cost, unitType, category, null);
    }

    public static Product create(Long id, String name, Double quantity, Double price, Double cost, String unitType, Category category, Agent agent) {
        Product product = new Product();
        product.id = id;
        product.name = name;
        product.quantity = quantity;
        product.price = price;
        product.cost = cost;
        product.unitType = unitType;
        product.category = category;
        product.agent = agent;
        return product;
    }

    public void increaseQuantity(Double amount) {

        ValidatorUtils.validateData(a -> a == null || a <= 0, amount ,"Amount to increase must be valid");

        this.quantity += amount;
    }

    public void decreaseQuantity(Double amount) {

        ValidatorUtils.validateData(a -> a == null || a <= 0, amount ,"Amount to increase must be valid");
        Double newQuantity = quantity - amount;
        ValidatorUtils.validateData(a -> a < 0, newQuantity, "Insufficient quantity for product: " + name + ". Current quantity: " + quantity + ", requested decrease: " + amount);

        this.quantity = newQuantity;
    }

    public static class Category {
        private Long id;
        private String name;

        private Category() {
        }

        public static Category create(Long id, String name) {
            Category category = new Category();
            category.id = id;
            category.name = name;
            return category;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static class Agent {
        private Long id;
        private String name;

        private Agent() {
        }

        public static Agent create(Long id, String name) {
            Agent agent = new Agent();
            agent.id = id;
            agent.name = name;
            return agent;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Double getCost() {
        return cost;
    }

    public String getUnitType() {
        return unitType;
    }

    public Category getCategory() {
        return category;
    }

    public Agent getAgent() {
        return agent;
    }
}
