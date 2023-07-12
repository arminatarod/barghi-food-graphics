package com.example.barghifoodgraphics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
        cart.setUserLocation(1);
        locations.add(1);
        selectedLocation = 1;
    }
    @JsonCreator
    public User(@JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("type") String type, @JsonProperty("recoveryQuestion") String recoveryQuestion, @JsonProperty("recoveryQuestionAnswer") String recoveryQuestionAnswer, @JsonProperty("id") int id,@JsonProperty("locations") HashSet<Integer> locations,@JsonProperty("orders") HashSet<Integer> orders,@JsonProperty("comments") HashSet<Integer> comments,@JsonProperty("cart") Order cart,@JsonProperty("selectedLocation") int selectedLocation, @JsonProperty("balance")double balance) {
        super(username, password, type, recoveryQuestion, recoveryQuestionAnswer, id);
        this.locations = locations;
        this.orders = orders;
        this.comments = comments;
        this.cart = cart;
        this.selectedLocation = selectedLocation;
        this.balance = balance;
    }
    public void setLocations(HashSet<Integer> locations) {
        this.locations = locations;
    }

    public void setOrders(HashSet<Integer> orders) {
        this.orders = orders;
    }

    public void setComments(HashSet<Integer> comments) {
        this.comments = comments;
    }

    public void setCart(Order cart) {
        this.cart = cart;
        save();
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
