package com.example.barghifoodgraphics;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class Food {
    private int id, price, discount, discountTimestamp, restaurantId;

    private double averageRating;
    private HashMap<Integer, Integer> ratings;
    //String imageUrl;
    // baraye bakhsh graphic khobe aksesham dashte bashim !? ye string url
    private String name;
    private boolean activeDiscount, isActive;
    private HashSet<Integer> comments;

    public Food(int id, int price, int restaurantId, String name) {
        this.id = id;
        this.price = price;
        this.restaurantId = restaurantId;
        this.name = name;
        ratings = new HashMap<>();
        comments = new HashSet<>();
    }

    public void setRestaurant(int restaurant) {
        this.restaurantId = restaurant;
        save();
    }
    public void setDiscount(int discount, int timestamp) {
        this.discount = discount;
        this.discountTimestamp = timestamp;
        save();
    }
    public int getDiscount() {
        return discount;
    }
    public int getDiscountTimestamp() {
        return discountTimestamp;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public boolean getActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
        save();
    }
    public void setActiveDiscount(boolean activeDiscount) {
        this.activeDiscount = activeDiscount;
        save();
    }
    public void setName(String name) {
        this.name = name;
        save();
    }
    public void setPrice(int price) {
        this.price = price;
        save();
    }
    public double getPrice() {
        // TODO : if discount is available count that
        return price;
    }
    public String getName() {
        return name;
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
    public int getRestaurant() {
        return restaurantId;
    }
    public void editRating(int raterId, int newRating) {
        averageRating = (averageRating * ratings.size() - ratings.get(raterId) + newRating) / ratings.size();
        ratings.replace(raterId, newRating);
        save();
    }
    public void addComment(int commentId) {
        comments.add(commentId);
    }
    public HashSet<Integer> getComments() {
        return comments;
    }
    public HashMap<Integer, Integer> getRaters() {
        return ratings;
    }
    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/data/foods/" + id + ".json"), this);
        } catch (Exception ignored) {}
    }
}
