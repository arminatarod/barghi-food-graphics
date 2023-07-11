package com.example.barghifoodgraphics;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.HashMap;

public class Order {
    private int id, restaurantId, userId, deliverymanId, userLocation;
    private String status;
    private HashMap<Integer, Integer> items;

    public Order(int id, int restaurant, int user, int deliveryman, int userLocation) {
        this.id = id;
        this.restaurantId = restaurant;
        this.userId = user;
        this.deliverymanId = deliveryman;
        this.userLocation = userLocation;
    }
    public Order(){

    }
    public int getUserLocation() {
        return userLocation;
    }
    public void setRestaurant(int restaurant) {
        this.restaurantId = restaurant;
    }
    public void setStatus(String status) {
        this.status = status;
        save();
    }
    public String getStatus() {
        return status;
    }
    public void setId(int id) {
        this.id = id;
        save();
    }
    public void setDeliveryman(int deliveryman) {
        this.deliverymanId = deliveryman;
        save();
    }
    public void setUser(int user) {
        this.userId = user;
        save();
    }
    public int getRestaurant() {
        return restaurantId;
    }
    public int getId() {
        return id;
    }
    public int getDeliveryman() {
        return deliverymanId;
    }
    public int getUser() {
        return userId;
    }
    public HashMap<Integer, Integer> getItems() {
        return items;
    }
    public void addItem(int itemId, int count) {
        if (items.containsKey(itemId)){
            items.replace(itemId, items.get(itemId) + count);
        }
        else {
            items.put(itemId, count);
        }
        save();
    }
    public void removeItem(Food item, int count) {
        if (item == null)
                return;
        if (items.containsKey(item.getId())) {
            int tmp = items.get(item.getId());
            items.replace(item.getId(), tmp - count);
        }
        save();
    }
    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/data/orders/" + id + ".json"), this);
        } catch (Exception ignored) {}
    }
}
