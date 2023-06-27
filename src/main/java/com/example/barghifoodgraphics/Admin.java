package com.example.barghifoodgraphics;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class Admin extends Account {
    public HashSet<Integer> restaurants = new HashSet<>();
    public Admin(String userName, String password, String recoveryQuestion, String recoveryQuestionAnswer, int id) {
        super(userName, password, recoveryQuestion, recoveryQuestionAnswer, id);
    }
    public void addRestaurant (int restaurantID) {
        restaurants.add(restaurantID);
    }
    public void removeRestaurant(int restaurantID) {
        restaurants.remove(restaurantID);
    }
    public HashSet<Integer> getRestaurants() {
        return restaurants;
    }
    public void showRestaurants() {
        HashMap<String, Restaurant> mp = new HashMap<>();
        for(Integer restaurantId : restaurants) {
            mp.put(Restaurant.getRestaurant(restaurantId).getName(),Restaurant.getRestaurant(restaurantId));
        }

    }
    static public Admin getAdmin(int ID) {
        Admin result;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue("src/data/admins/" + ID + ".json", Admin.class);
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    static public void saveAdmin(int ID, Admin admin) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/data/admins/" + ID + ".json"), admin);
        } catch (Exception ignored) {}
    }
}
