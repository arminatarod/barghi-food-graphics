package com.example.barghifoodgraphics;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashSet;

public class Deliveryman extends Account {
    private int location, balance = 0;
    private HashSet<Integer> orders = new HashSet<>();
    private int activeOrder = -1;

    public Deliveryman(String userName, String password, String recoveryQuestion, String recoveryQuestionAnswer, int id) {
        super(userName, password, recoveryQuestion, recoveryQuestionAnswer, id);
    }

    public int getLocation() {
        return location;
    }
    public void setLocation(int location) {
        this.location = location;
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
    public void addMoney(int amount) {
        balance += amount;
    }
    public int getActiveOrder() {
        return activeOrder;
    }
    public void setActiveOrder(int activeOrder) {
        this.activeOrder = activeOrder;
    }
    static public Deliveryman getDeliveryman(int ID) {
        Deliveryman result;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue("src/data/deliverymen/" + ID + ".json", Deliveryman.class);
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    static public void saveDeliveryman(int ID, Deliveryman deliveryman) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/data/deliverymen/" + ID + ".json"), deliveryman);
        } catch (Exception ignored) {}
    }
}
