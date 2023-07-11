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
        save();
    }
    public void removeRestaurant(int restaurantID) {
        restaurants.remove(restaurantID);
        save();
    }
    public HashSet<Integer> getRestaurants() {
        return restaurants;
    }
    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/data/accounts/" + this.getId() + "a.json"), this);
        } catch (Exception ignored) {}
    }
}
