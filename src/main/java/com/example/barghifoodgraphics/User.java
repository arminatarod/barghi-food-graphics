package com.example.barghifoodgraphics;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class User extends Account {
    private HashSet<Integer> locations, orders, comments;
    private Order cart;
    private int selectedLocation;
    private double balance;
    public User(String userName, String password, String recoveryQuestion, String recoveryQuestionAnswer, int id) {
        super(userName, password, recoveryQuestion, recoveryQuestionAnswer, id);
        locations = new HashSet<>();
        orders = new HashSet<>();
        comments = new HashSet<>();
        cart = new Order();
        cart.setId((-(this.getId() + 1)));
        cart.setUser(id);
        cart.setStatus("pend");
        cart.setRestaurant(-1);
    }
    public void setCart(Order cart) {
        this.cart = cart;
    }

    public Order getCart() {
        return cart;
    }
    public void addLocation(int location) {
        locations.add(location);
        save();
    }
    public void addOrder(int orderID) {
        orders.add(orderID);
    }
    public HashSet<Integer> getOrders() {
        return orders;
    }
    public HashSet<Integer> getComments() {
        return comments;
    }
    public HashSet<Integer> getLocations() {
        return locations;
    }
    public void setBalance(double amount) {
        this.balance = amount;
    }
    public double getBalance() {
        return balance;
    }
    public void addBalance(double amount) {
        this.balance += amount;
    }
    public void addComment(int commentID) {
        comments.add(commentID);
    }
    public void removeLocation(int id) {
        locations.remove(id);
        save();
    }
    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/data/accounts/" + this.getId() + "u.json"), this);
        } catch (Exception ignored) {}
    }

    public void setSelectedLocation(int selectedLocation) {
        this.selectedLocation = selectedLocation;
        save();
    }
    public int getSelectedLocation() {
        return selectedLocation;
    }
}
