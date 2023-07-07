package com.example.barghifoodgraphics;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashSet;

public class Restaurant {
    private int location, balance, ratingCount, adminID;
    private double averageRating;
    private String name;
    private HashSet<Integer> orders = new HashSet<>(), activeOrders = new HashSet<>(), comments = new HashSet<>(), menu = new HashSet<>();
    private HashSet<String> foodType = new HashSet<>();
    private HashSet<Food> foods = new HashSet<>();
    public HashSet<String> getFoodType() {
        return foodType;
    }
    public void addFoodToMenu(String foodName,int id,Restaurant restaurant,int price)
    {
        foods.add(new Food(id,price,restaurant, foodName));
        menu.add(id);
    }
    public void addFoodType(String type) {
        foodType.add(type);
    }
    public void removeFoodType(String type) {
        foodType.remove(type);
    }
    public void setLocation(int location) {
        this.location = location;
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
    }
    public HashSet<Integer> getMenu() {
        return menu;
    }
    public void deleteMenu() {
        menu.clear();
    }
    public double getAverageRating() {
        return averageRating;
    }
    public int getRatingCount() {
        return ratingCount;
    }
    public void addRating(int rating) {
        averageRating = (averageRating * ratingCount + rating) / (ratingCount + 1);
        ratingCount++;
    }
    public void editRating(int oldRating, int newRating) {
        averageRating = (averageRating * ratingCount - oldRating + newRating) / ratingCount;
    }
    public void addComment(int commentID) {
        comments.add(commentID);
    }
    public HashSet<Integer> getComments() {
        return comments;
    }
    public void setAdmin(int adminID) {
        this.adminID = adminID;
    }
    public int getAdmin() {
        return adminID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    static public Restaurant getRestaurant(int ID) {
        Restaurant result;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue("src/data/restaurants/" + ID + ".json", Restaurant.class);
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    static public void saveRestaurant(int ID, Restaurant restaurant) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/data/restaurants/" + ID + ".json"), restaurant);
        } catch (Exception ignored) {}
    }
    public void showRestaurant()
    {
        System.out.println("Restaurant name: " + name + " " + "Average rating: " + averageRating);
    }
}
