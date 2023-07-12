package com.example.barghifoodgraphics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class Restaurant {
    private int location, adminID, id;
    private String name,type;
    private HashSet<Integer> orders, menu;
    private HashSet<String> foodType;
    private HashMap<Integer, Integer> ratings;
    private double averageRating, balance;
    private HashSet<Integer> comments;

    public Restaurant(int location, String name, String type, int id) {
        this.location = location;
        this.name = name;
        this.type = type;
        this.id = id;
        ratings = new HashMap<>();
        orders = new HashSet<>();
        menu = new HashSet<>();
        foodType = new HashSet<>();
        comments = new HashSet<>();
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<Integer, Integer> getRatings() {
        return ratings;
    }
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
    public void setRatings(HashMap<Integer, Integer> ratings) {
        this.ratings = ratings;
    }

    public void setOrders(HashSet<Integer> orders) {
        this.orders = orders;
    }

    public void setMenu(HashSet<Integer> menu) {
        this.menu = menu;
    }

    public void setFoodType(HashSet<String> foodType) {
        this.foodType = foodType;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setComments(HashSet<Integer> comments) {
        this.comments = comments;
    }
    public HashSet<Integer> getComments() {
        return comments;
    }
    public void addComment(int commentId) {
        comments.add(commentId);
    }

    @JsonCreator
    public Restaurant(@JsonProperty("location")int location, @JsonProperty("adminID") int adminID, @JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("type") String type, @JsonProperty("orders") HashSet<Integer> orders, @JsonProperty("menu") HashSet<Integer> menu, @JsonProperty("averageRating") double averageRating, @JsonProperty("ratings") HashMap<Integer, Integer> ratings, @JsonProperty("foodType") HashSet<String> foodType, @JsonProperty("balance") double balance, @JsonProperty("comments") HashSet<Integer> comments) {
        this.location = location;
        this.adminID = adminID;
        this.id = id;
        this.name = name;
        this.type = type;
        this.orders = orders;
        this.menu = menu;
        this.foodType = foodType;
        this.balance = balance;
        this.averageRating = averageRating;
        this.ratings = ratings;
        this.comments = comments;
    }


    public double getAverageRating() {
        return averageRating;
    }
    public int getRatingCount() {
        return ratings.size();
    }
    public void addRating(int raterID, int rating) {
        averageRating = (averageRating * ratings.size() + rating) / (ratings.size() + 1);
        ratings.put(raterID, rating);
    }
    public void editRating(int raterId, int newRating) {
        averageRating = (averageRating * ratings.size() - ratings.get(raterId) + newRating) / ratings.size();
        ratings.replace(raterId, newRating);
        save();
    }
    public HashMap<Integer, Integer> getRaters() {
        return ratings;
    }
    public int getId() {
        return id;
    }
    public HashSet<String> getFoodType() {
        return foodType;
    }
    public void addFoodToMenu(int id) {
        menu.add(id);
        save();
    }
    public void addFoodType(String type) {
        foodType.add(type);
        save();
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
        orders.add(orderID);
        save();
    }
    public HashSet<Integer> getOrders() {
        return orders;
    }
    public double getBalance() {
        return balance;
    }
    public void addBalance(double value) {
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
    public void withdraw() {
        balance = 0;
        save();
    }
}
