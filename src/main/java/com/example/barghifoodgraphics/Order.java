package com.example.barghifoodgraphics;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;

public class Order {
    public class FoodData {
        private int count;
        private double totalPrice, discount;

        public int getCount() {
            return count;
        }
        public double getDiscount(){ return discount; }
        public void setCount(int count) {
            this.count = count;
        }
        public void setDiscount(double discount) {
            this.discount = discount;
        }
        public double getTotalPrice() {
            return totalPrice;
        }
        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }
        public void addPrice(double value) {
            totalPrice += value;
        }
        public void addCount(int value) {
            count += value;
        }
    }
    private int id, price;
    private Restaurant restaurant;
    private User user;
    private Deliveryman deliveryman;
    private String status;
    private HashMap<Integer, FoodData> items;

    public Order(int id, int price, Restaurant restaurant, User user, Deliveryman deliveryman, HashMap<Integer, FoodData> items) {
        this.id = id;
        this.price = price;
        this.restaurant = restaurant;
        this.user = user;
        this.deliveryman = deliveryman;
        this.items = items;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setDeliveryman(Deliveryman deliveryman) {
        this.deliveryman = deliveryman;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Restaurant getRestaurant() {
        return restaurant;
    }
    public int getId() {
        return id;
    }
    public Deliveryman getDeliveryman() {
        return deliveryman;
    }
    public User getUser() {
        return user;
    }
    public void setItems(HashMap<Integer, FoodData> items) {
        this.items = items;
    }
    public HashMap<Integer, FoodData> getItems() {
        return items;
    }
    public void addItem(Food item) {
        if (item == null)
            return;
        if (items.containsKey(item.getId())) {
            FoodData tmp = items.get(item.getId());
            tmp.addCount(1);
            tmp.addPrice(item.getPrice());
        }
        else {
            FoodData tmp = new FoodData();
            tmp.setCount(1);
            tmp.setTotalPrice(item.getPrice());
            items.put(item.getId(), tmp);
        }
        price += item.getPrice();
    }
    public void removeItem(Food item) {
        if (item == null)
                return;
        if (items.containsKey(item.getId())) {
            FoodData tmp = items.get(item.getId());
            tmp.addCount(-1);
            tmp.addPrice(-item.getPrice());
            if (tmp.getCount() == 0)
                items.remove(item.getId());
            price += item.getPrice();
        }
    }
    static public Order getOrder(int ID) {
        Order result;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue("src/data/orders/" + ID + ".json", Order.class);
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    static public void saveOrder(int ID, Order order) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/data/orders/" + ID + ".json"), order);
        } catch (Exception ignored) {}
    }
    public void showOrder(int estimatedTime,int orderId)
    {
        System.out.println("orderId: " + orderId + "EstimatedTime: " + estimatedTime);
    }
}
