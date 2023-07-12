package com.example.barghifoodgraphics;

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
