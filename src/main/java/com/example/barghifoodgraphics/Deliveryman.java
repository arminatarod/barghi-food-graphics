package com.example.barghifoodgraphics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashSet;

public class Deliveryman extends Account {
    private int location, balance;
    private HashSet<Integer> orders;
    private int activeOrder;
    public Deliveryman(String userName, String password, String recoveryQuestion, String recoveryQuestionAnswer, int id) {
        super(userName, password, recoveryQuestion, recoveryQuestionAnswer, id);
        balance = 0;
        activeOrder = -1;
        orders = new HashSet<>();
        location = 1;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setOrders(HashSet<Integer> orders) {
        this.orders = orders;
    }
    @JsonCreator
    public Deliveryman(@JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("type") String type, @JsonProperty("recoveryQuestion") String recoveryQuestion, @JsonProperty("recoveryQuestionAnswer") String recoveryQuestionAnswer, @JsonProperty("id") int id, @JsonProperty("location")int location, @JsonProperty("balance")int balance, @JsonProperty("orders")HashSet<Integer> orders, @JsonProperty("activeOrder")int activeOrder) {
        super(username, password, type, recoveryQuestion, recoveryQuestionAnswer, id);
        this.location = location;
        this.balance = balance;
        this.orders = orders;
        this.activeOrder = activeOrder;
    }

    public int getLocation() {
        return location;
    }
    public void setLocation(int location) {
        this.location = location;
        save();
    }
    public void addOrder(int orderID) {
        orders.add(orderID);
    }
    public HashSet<Integer> getOrders() {
        return orders;
    }
    public int getBalance() {
        return balance;
    }
    public void addBalance(int amount) {
        balance += amount;
    }
    public int getActiveOrder() {
        return activeOrder;
    }
    public void withdraw() {
        balance = 0;
        save();
    }
    public void setActiveOrder(int activeOrder) {
        this.activeOrder = activeOrder;
        save();
    }
    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/data/accounts/" + this.getId() + "d.json"), this);
        } catch (Exception ignored) {}
    }
}
