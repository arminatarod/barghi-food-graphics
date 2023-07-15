package com.example.barghifoodgraphics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;

public class Food {
    private int id, price, restaurantId;
    private LocalTime discountTimestamp;
    private double averageRating, discount;
    private HashMap<Integer, Integer> ratings;
    private String name, foodType;
    private boolean isActive;
    private HashSet<Integer> comments;

    public Food(int id, int price, int restaurantId, String name) {
        this.id = id;
        this.price = price;
        this.restaurantId = restaurantId;
        this.name = name;
        ratings = new HashMap<>();
        isActive = false;
        comments = new HashSet<>();
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public HashMap<Integer, Integer> getRatings() {
        return ratings;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setDiscountTimestamp(LocalTime discountTimestamp) {
        this.discountTimestamp = discountTimestamp;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setRatings(HashMap<Integer, Integer> ratings) {
        this.ratings = ratings;
    }

    public void setComments(HashSet<Integer> comments) {
        this.comments = comments;
    }

    @JsonCreator
    public Food(@JsonProperty("id")int id, @JsonProperty("price") int price, @JsonProperty("restaurantId") int restaurantId, @JsonProperty("discountTimestamp") LocalTime discountTimestamp, @JsonProperty("averageRating") double averageRating, @JsonProperty("discount") double discount, @JsonProperty("ratings") HashMap<Integer, Integer> ratings, @JsonProperty("name") String name, @JsonProperty("isActive") boolean isActive, @JsonProperty("comments") HashSet<Integer> comments) {
        this.id = id;
        this.price = price;
        this.restaurantId = restaurantId;
        this.discountTimestamp = discountTimestamp;
        this.averageRating = averageRating;
        this.discount = discount;
        this.ratings = ratings;
        this.name = name;
        this.isActive = isActive;
        this.comments = comments;
    }

    public void setRestaurant(int restaurant) {
        this.restaurantId = restaurant;
        save();
    }
    public void setDiscount(double discount, LocalTime timestamp) {
        this.discount = discount;
        this.discountTimestamp = timestamp;
        save();
    }
    public double getDiscount() {
        if (LocalTime.now().isAfter(discountTimestamp)) {
            return 0;
        }
        else {
            return  0;
        }
    }
    public LocalTime getDiscountTimestamp() {
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
    public void setName(String name) {
        this.name = name;
        save();
    }
    public void setPrice(int price) {
        this.price = price;
        save();
    }
    public double getPrice() {
        if (LocalTime.now().isAfter(discountTimestamp)) {
            return price;
        }
        else {
            return  price * (1 - discount);
        }
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
    public void removeComment(int id) {
        comments.remove(id);
    }
}
