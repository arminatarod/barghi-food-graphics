package com.example.barghifoodgraphics;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.HashSet;

public class Restaurant {
    private int location, balance, adminID, id;
    private String name,type;
    private HashSet<Integer> orders, activeOrders, menu;
    private HashSet<String> foodType;

    public Restaurant(int location, String name, String type, int id) {
        this.location = location;
        this.name = name;
        this.type = type;
        this.id = id;
        orders = new HashSet<>();
        activeOrders = new HashSet<>();
        menu = new HashSet<>();
        foodType = new HashSet<>();
    }

    public int getId() {
        return id;
    }
    public HashSet<String> getFoodType() {
        return foodType;
    }
    public void addFoodToMenu(int id) {
        menu.add(id);
    }
    public void addFoodType(String type) {
        foodType.add(type);
    }
    public void removeFoodType(String type) {
        foodType.remove(type);
        save();
    }
    public void setLocation(int location) {
        this.location = location;
        save();
    }
    public int getLocation() {
        return location;
    }
    public void addOrder(int orderID) {
        activeOrders.add(orderID);
    }
    public HashSet<Integer> getOrders() {
        return orders;
    }
    public HashSet<Integer> getActiveOrders() {
        return activeOrders;
    }
    public int getBalance() {
        return balance;
    }
    public void addBalance(int value) {
        balance += value;
        save();
    }
    public HashSet<Integer> getMenu() {
        return menu;
    }
    public void deleteMenu() {
        menu.clear();
        save();
    }
    public void deleteFoodType(){
        foodType.clear();
        menu.clear();
        save();
    }
    public void setAdmin(int adminID) {
        this.adminID = adminID;
        save();
    }
    public int getAdmin() {
        return adminID;
    }
    public void setName(String name) {
        this.name = name;
        save();
    }
    public String getName() {
        return name;
    }
    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/data/restaurants/" + id + ".json"), this);
        } catch (Exception ignored) {}
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
        save();
    }

}
