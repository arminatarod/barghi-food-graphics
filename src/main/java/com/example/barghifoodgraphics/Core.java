package com.example.barghifoodgraphics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.time.LocalTime;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Integer.min;

public class Core {
    MapG map;
    HashMap<Integer, Account> accounts;
    HashMap<Integer, Order> orders;
    HashMap<Integer, Restaurant> restaurants;
    HashMap<Integer, Food> foods;
    HashMap<Integer, Comment> comments;
    int loggedInAccount, loggedInUser, loggedInAdmin, loggedInDeliveryman, selectedRestaurant, selectedFood, selectedOrder, tmpPassForgot;
    public double getPrice (HashMap<Integer, Integer> items) {
        double ans = 0.0;
        for (int i : items.keySet())
            ans += items.get(i) * foods.get(i).getFoodPrice();
        return ans;
    }
    public int getFileCount(File f) {
        File[] koft = f.listFiles();
        if (koft == null)
            return 0;
        return koft.length;
    }
    public Core() throws FileNotFoundException {
        map = new MapG();
        map.readGraphFromFile("src/data/graph2.txt");
        accounts = new HashMap<>();
        orders = new HashMap<>();
        restaurants = new HashMap<>();
        foods = new HashMap<>();
        comments = new HashMap<>();
        loggedInAccount = -1;
        loggedInUser = -1;
        loggedInAdmin = -1;
        loggedInDeliveryman = -1;
        selectedRestaurant = -1;
        selectedFood = -1;
        selectedOrder = -1;
        File use = new File("src/data/accounts/");
        File ord = new File("src/data/orders/");
        File rest = new File("src/data/restaurants/");
        File com = new File("src/data/comments/");
        File fd = new File("src/data/foods/");
        // Reading users accounts
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        for (int i = 0; i < getFileCount(use); i++) {
            int koft = 0;
            try {
                accounts.put(i, mapper.readValue(new File("src/data/accounts/" + i + "a.json"), Admin.class));
            } catch (Exception e) {
                koft++;
            }
            try {
                accounts.put(i, mapper.readValue(new File("src/data/accounts/" + i + "u.json"), User.class));
            } catch (Exception e) {
                koft++;
            }
            try {
                accounts.put(i, mapper.readValue(new File("src/data/accounts/" + i + "d.json"), Deliveryman.class));
            } catch (Exception e) {koft++;}
            if (koft == 3)
                System.out.println("there is a missing file with id : " + i + " in accounts database");
        }
        // Reading orders
        for (int i = 0; i < getFileCount(ord); i++) {
            Order result;
            try {
                result = mapper.readValue(new File("src/data/orders/" + i + ".json"), Order.class);
                orders.put(result.getId(), result);
            } catch (Exception e) {
                System.out.println("there is a missing file in orders data base");
            }
        }
        // Reading restaurants
        for (int i = 0; i < getFileCount(rest); i++) {
            Restaurant result;
            try {
                result = mapper.readValue(new File("src/data/restaurants/" + i + ".json"), Restaurant.class);
                restaurants.put(result.getId(), result);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("there is a missing file in restaurants database");
            }
        }
        // Reading comments
        for (int i = 0; i < getFileCount(com); i++) {
            Comment result;
            try {
                result = mapper.readValue(new File("src/data/comments/" + i + ".json"), Comment.class);
                comments.put(result.getId(), result);
            } catch (Exception e) {
                System.out.println("there is a missing file in comments database");
            }
        }
        // Reading foods
        for (int i = 0; i < getFileCount(fd); i++) {
            Food result;
            try {
                result = mapper.readValue(new File("src/data/foods/" + i + ".json"), Food.class);
                foods.put(result.getId(), result);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("there is a missing file in foods database");
            }
        }
    }
    
    public int login(String userName, String password) {
        if (loggedInAccount != -1) {
            System.out.println("You are already logged in.");
            return 3;
        }
        for (Account acc : accounts.values()) {
            if (userName.equals(acc.getUsername())) {
                if (password.equals(acc.getPassword())) {
                    System.out.println("Logged in successfully.");
                    loggedInAccount = acc.getId();
                    if (acc.getType().equals("User")) {
                        loggedInUser = acc.getId();
                        return 0;
                    }
                    else if (acc.getType().equals("Admin")) {
                        loggedInAdmin = acc.getId();
                        return 1;
                    }
                    else {
                        loggedInDeliveryman = acc.getId();
                        return 2;
                    }
                }
                System.out.println("Incorrect password!");
                return 4;
            }
        }
        System.out.println("There is no account with this username!");
        return 5;
    }
    public void logout() {
        if (loggedInAccount == -1) {
            System.out.println("No one has logged in!");
        } else {
            if (loggedInDeliveryman != -1) {
                System.out.println("Logged out successfully.");
                loggedInDeliveryman = -1;
            } else if (loggedInUser != -1) {
                System.out.println("Logged out successfully.");
                loggedInAdmin = -1;
            } else {
                System.out.println("Logged out successfully.");
                loggedInUser = -1;
            }
        }
        loggedInAccount = -1;
        selectedRestaurant = -1;
        selectedFood = -1;
        selectedOrder = -1;
    }
    public int addUser(String username, String password, String recoveryQuestion, String recoveryQuestionAnswer) {
        if (loggedInAccount != -1) {
            System.out.println("Please logout first!");
            return 1;
        }
        for (Account i : accounts.values())
            if (i.getUsername().equals(username)) {
                System.out.println("Username taken!");
                return 2;
            }
        if (!Account.checkPassword(password)) {
            System.out.println("wrong password format");
            return 3;
        }
        User tmp = new User(username, password, recoveryQuestion, recoveryQuestionAnswer, accounts.size());
        tmp.setType("User");
        tmp.save();
        accounts.put(tmp.getId(), tmp);
        System.out.println("User has been registered successfully.");
        return 0;
    }
    public int addDeliveryman(String username, String password, String recoveryQuestion, String recoveryQuestionAnswer) {
        if (loggedInAccount != -1) {
            System.out.println("Please logout first!");
            return 1;
        }
        for (Account i : accounts.values())
            if (i.getUsername().equals(username)) {
                System.out.println("Username taken!");
                return 2;
            }
        if (!Account.checkPassword(password)) {
            System.out.println("wrong password format");
            return 3;
        }
        Deliveryman tmp = new Deliveryman(username, password, recoveryQuestion, recoveryQuestionAnswer, accounts.size());
        tmp.setType("Deliveryman");
        tmp.save();
        accounts.put(tmp.getId(), tmp);
        System.out.println("Deliveryman has been registered successfully.");
        return 0;
    }
    public int addAdmin(String username, String password, String recoveryQuestion, String recoveryQuestionAnswer) {
        if (loggedInAccount != -1) {
            System.out.println("Please logout first!");
            return 1;
        }
        for (Account i : accounts.values())
            if (i.getUsername().equals(username)) {
                System.out.println("Username taken!");
                return 2;
            }
        if (!Account.checkPassword(password)) {
            System.out.println("wrong password format");
            return 3;
        }
        Admin tmp = new Admin(username, password, recoveryQuestion, recoveryQuestionAnswer, accounts.size());
        tmp.setType("Admin");
        tmp.save();
        accounts.put(tmp.getId(), tmp);
        System.out.println("Admin has been registered successfully.");
        return 0;
    }
    public void showLocation() {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        }
        System.out.println(restaurants.get(selectedRestaurant).getLocation());
    }
    public void editLocation(int nodeID) {
        if (loggedInAdmin == -1) {
            System.out.println("You should login as an admin");
            return;
        }
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        }
        if (nodeID > map.getN()) {
            System.out.println("invalid nodeId");
            return;
        }
        Restaurant tmp = restaurants.get(selectedRestaurant);
        if (tmp.getAdmin() != loggedInAdmin){
            System.out.println("Your not the owner of selected restaurant");
            return;
        }
        tmp.setLocation(nodeID);
        System.out.println("The location has been updated successfully.");
    }
    public void addLocation(int nodeId) {
        if (loggedInUser == -1) {
            System.out.println("you should login as an User !");
            return;
        }
        if (nodeId > map.getN()) {
            System.out.println("invalid nodeId");
            return;
        }
        User tmp = (User) accounts.get(loggedInUser);
        tmp.addLocation(nodeId);
        System.out.println("location added successfully");
    }
    public void removeLocation(int nodeId){
        if (loggedInUser == -1) {
            System.out.println("you should login as an User !");
            return;
        }
        if (nodeId > map.getN()) {
            System.out.println("invalid nodeId");
            return;
        }
        User tmp = (User) accounts.get(loggedInUser);
        if (!tmp.getLocations().contains(nodeId)) {
            System.out.println("this location is not in your location lists");
            return;
        }
        tmp.removeLocation(nodeId);
        System.out.println("location removed successfully");
    }
    public void showFoodType() {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        }
        System.out.println("Restaurant food types : ");
        for (String s : restaurants.get(selectedRestaurant).getFoodType())
            System.out.println(s);
    }
    public int addFoodType(String types, boolean forSure) {
        if (!forSure) {
            System.out.println("OPERATION CANCELED (not sure)");
            return 4;
        }
        else if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return 1;
        }
        Restaurant tmp = restaurants.get(selectedRestaurant);
        boolean koft = false;
        for (int i : tmp.getOrders()) {
            if (orders.get(i).getStatus().equals("inr")) {
                koft = true;
                break;
            }
        }
        if (koft) {
            System.out.println("The restaurant has active orders!");
            return 2;
        }
        String[] type = types.split(",");
        for (String t : type) {
            if (restaurants.get(selectedRestaurant).getFoodType().contains(t)) {
                System.out.println("The restaurant already contains " + t + " food type!");
                koft = true;
            }
            else {
                restaurants.get(selectedRestaurant).addFoodType(t);
            }
        }
        if (koft)
            return 3;
        else
            return 0;
    }
    public int removeFoodType(String types, boolean forSure) {
        if (!forSure) {
            System.out.println("OPERATION CANCELED (not sure)");
            return 4;
        }
        else if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return 1;
        }
        Restaurant tmp = restaurants.get(selectedRestaurant);
        boolean koft = false;
        for (int i : tmp.getOrders()) {
            if (orders.get(i).getStatus().equals("inr")) {
                koft = true;
                break;
            }
        }
        if (koft) {
            System.out.println("The restaurant has active orders!");
            return 2;
        }
        String[] type = types.split(",");
        for(String t : type) {
            if (!restaurants.get(selectedRestaurant).getFoodType().contains(t)) {
                System.out.println("The restaurant does not contain " + t + " food type!");
                koft = true;
            }
            else {
                restaurants.get(selectedRestaurant).removeFoodType(t);
                restaurants.get(selectedRestaurant).deleteMenu();
            }
        }
        if (koft)
            return 3;
        return 0;
    }
    public int editFoodType(String types, boolean forSure) {
        if (!forSure) {
            System.out.println("ARE YOU SURE YOU WANT TO EDIT THIS FOOD TYPE IN YOUR RESTAURANT?");
            return 5;
        }
        else if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return 1;
        }
        Restaurant tmp = restaurants.get(selectedRestaurant);
        boolean koft = false;
        for (int i : tmp.getOrders()) {
            if (orders.get(i).getStatus().equals("inr")) {
                koft = true;
                break;
            }
        }
        if (koft) {
            System.out.println("The restaurant has active orders!");
            return 2;
        }
        String[] type = types.split(",");
        restaurants.get(selectedRestaurant).deleteFoodType();
        for (String t : type)
            restaurants.get(selectedRestaurant).addFoodType(t);
        return 0;
    }
    public void showMenu() {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        }
        for (int i : restaurants.get(selectedRestaurant).getMenu())
            System.out.println("ID: " + i + ", NAME: " + foods.get(i).getName() + ", PRICE: " + foods.get(i).getFoodPrice() + ", DISCOUNT: " + foods.get(i).getFoodDiscount() + ", RATING: " + foods.get(i).getAverageRating() + ", IS ACTIVE: " + foods.get(i).getActive());
    }
    public void editFoodPrice(int foodID, int price) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        }
        else if (restaurants.get(selectedRestaurant).getAdmin() != loggedInAdmin) {
            System.out.println("You are not the owner !");
        }
        else if (!restaurants.get(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        }
        else {
            foods.get(foodID).setPrice(price);
            System.out.println("Information updated successfully.");
        }
    }
    public void editFoodName(int foodID, String newName) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        }
        else if (restaurants.get(selectedRestaurant).getAdmin() != loggedInAdmin) {
            System.out.println("You are not the owner !");
        }
        else if (!restaurants.get(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        }
        else {
            foods.get(foodID).setName(newName);
            System.out.println("Information updated successfully.");
        }
    }
    public void addFood(String foodName, int foodPrice, String foodType) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        }
        else if (restaurants.get(selectedRestaurant).getAdmin() != loggedInAdmin) {
            System.out.println("You are not the owner !");
        }
        for (int i : restaurants.get(selectedRestaurant).getMenu()){
            if (foods.get(i).getName().equals(foodName)) {
                System.out.println("The selected restaurant has a food with the given name!");
                return;
            }
        }
        restaurants.get(selectedRestaurant).addFoodToMenu(foods.size());
        Food tmp = new Food(foods.size(), foodPrice, selectedRestaurant, foodName, foodType);
        tmp.save();
        foods.put(foods.size(), tmp);
        System.out.println("The food added successfully.");

    }
    public void removeFood(int id) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        }
        else if (restaurants.get(selectedRestaurant).getAdmin() != loggedInAdmin) {
            System.out.println("You are not the owner !");
        }
        for (int i : restaurants.get(selectedRestaurant).getMenu()){
            if (foods.get(i).getId() == id) {
                restaurants.get(selectedRestaurant).removeFood(id);
                System.out.println("food successfullt removed from menu");
                if (selectedFood == id) {
                    selectedFood = -1;
                }
                return;
            }
        }
        System.out.println("there is not any food with this is in this restaurant!");
    }
    public void activateFood(int foodID) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        } else if (restaurants.get(selectedRestaurant).getAdmin() != loggedInAdmin) {
            System.out.println("You are not the owner !");
        } else if (!restaurants.get(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        } else {
            foods.get(foodID).setActive(true);
            System.out.println("Information updated successfully.");
        }
    }
    public void deactivateFood(int foodID) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        } else if (restaurants.get(selectedRestaurant).getAdmin() != loggedInAdmin) {
            System.out.println("You are not the owner !");
        } else if (!restaurants.get(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        } else {
            foods.get(foodID).setActive(false);
            System.out.println("Information updated successfully.");
        }
    }
    public void discountFood(int foodID, int discountPercentage, int timestamp) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        } else if (restaurants.get(selectedRestaurant).getAdmin() != loggedInAdmin) {
            System.out.println("You are not the owner !");
        } else if (!restaurants.get(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        } else {
            if (discountPercentage > 100 || discountPercentage < 1) {
                System.out.println("wrong discount percentage amount !");
                return;
            }
            foods.get(foodID).setDiscount((double) discountPercentage / 100, LocalTime.now().plusMinutes(timestamp));
            System.out.println("Information updated successfully.");
        }
    }
    public void selectFood(int foodID) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        } else if (!restaurants.get(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        } else {
            selectedFood = foodID;
            System.out.println("Food selected successfully.");
        }
    }
    public void showRestaurants() {
        if (loggedInAdmin != -1) {
            Admin tmp = (Admin) accounts.get(loggedInAdmin);
            for (int i : tmp.getRestaurants()) {
                System.out.println("id : " + i + " Name : " + restaurants.get(i).getName() + " food type : " + restaurants.get(i).getType());
            }
        }
    }
    public void displayRating() {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else {
            System.out.println("AVERAGE RATING: " + foods.get(selectedFood).getAverageRating());
        }
    }
    public void displayComments() {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else {
            for (int i : foods.get(selectedFood).getComments())
                System.out.println("ID: " + i + ", USER: " + accounts.get(comments.get(i).getCommenterID()).getUsername() + ", CONTENT: " + comments.get(i).getContent() + ", RESPONSE: " + comments.get(i).getAnswer());
        }
    }
    public void addResponse(int commentID, String message) {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        }
        else if (restaurants.get(selectedRestaurant).getAdmin() != loggedInAdmin) {
            System.out.println("You are not the owner !");
        }
        else if (!foods.get(selectedFood).getComments().contains(commentID)) {
            System.out.println("The selected food does not have a comment with the given ID!");
        }
        else if (comments.get(commentID).getAnswer() != null) {
            System.out.println("This comment has already been answered!");
        }
        else {
            comments.get(commentID).setAnswer(message);
            System.out.println("Response added successfully.");
        }
    }
    public void removeResponse(int commentID) {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        }
        else if (restaurants.get(selectedRestaurant).getAdmin() != loggedInAdmin) {
            System.out.println("You are not the owner !");
        }
        else if (!foods.get(selectedFood).getComments().contains(commentID)) {
            System.out.println("The selected food does not have a comment with the given ID!");
        }
        else {
            comments.get(commentID).setAnswer("");
            System.out.println("Response added successfully.");
        }
    }
    public void editResponse(int commentID, String message) {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        }
        else if (restaurants.get(selectedRestaurant).getAdmin() != loggedInAdmin) {
            System.out.println("You are not the owner !");
        }
        else if (!foods.get(selectedFood).getComments().contains(commentID)) {
            System.out.println("The selected food does not have a comment with the given ID!");
        }
        else if (comments.get(commentID).getAnswer() == null) {
            System.out.println("This comment has never been answered!");
        }
        else {
            comments.get(commentID).setAnswer(message);
            System.out.println("Response edited successfully.");
        }
    }
    public void unselectFood() {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else {
            selectedFood = -1;
            System.out.println("Food unselected successfully.");
        }
    }
    public void unselectRestaurant() {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        } else {
            selectedRestaurant = -1;
            selectedFood = -1;
            System.out.println("Restaurant unselected successfully.");
        }
    }
    public void displayActiveOrders() {
        if(loggedInAccount == -1) {
            System.out.println("No one has logged in yet!!!");
        }
        if (loggedInUser != -1) {
            User tmp = (User) accounts.get(loggedInUser);
            HashSet<Integer> tmp2 = tmp.getOrders();
            if (tmp2.isEmpty()) {
                System.out.println("There is no order!!!");
            } else {
                int i = 0;
                for (Integer orderId : tmp2) {
                    if (orders.get(orderId).getStatus().equals("done"))
                        continue;
                    i++;
                    System.out.println("Order  " + i + "  : " + "id : " + orderId + " Restaurant id : " + orders.get(orderId).getRestaurantId() + " Status : " + orders.get(orderId).getStatus());
                }
            }
        }
        else if (loggedInAdmin != -1) {
            if (selectedRestaurant == -1) {
                System.out.println(" please first select restaurant!");
                return;
            }
            Restaurant tmp = restaurants.get(selectedRestaurant);
            HashSet<Integer> tmp2 = tmp.getOrders();
            if (tmp2.isEmpty()) {
                System.out.println("There is no order!!!");
            } else {
                int i = 0;
                for (Integer orderId : tmp2) {
                    if (orders.get(orderId).getStatus().equals("done")){
                        continue;
                    }
                    i++;
                    System.out.println("Order  " + i + "  : " + "id : " + orderId + " Deliverymen id : " + orders.get(orderId).getDeliverymanId() + " Price : " + getPrice(orders.get(orderId).getItems()));
                }
            }
        }
        else {
            Deliveryman tmp = (Deliveryman) accounts.get(loggedInDeliveryman);
            if (tmp.getActiveOrder() == -1) {
                System.out.println("you don't have any active order");
            }
            else {
                Order tmp2 = orders.get(tmp.getActiveOrder());
                System.out.println("Order id : " + tmp2.getId() + " Restaurant location : " + restaurants.get(tmp2.getRestaurantId()).getLocation() + " User location : " + tmp2.getUserLocation() + " Delivery price :" + tmp2.getDeliveryPrice());
            }
        }
    }
    public void editOrderStatus(String status) {
        if (selectedOrder == -1) {
            System.out.println("no order has benn selected !");
            return;
        }
        Order tmp = orders.get(selectedOrder);
        if (tmp.getUserId() == loggedInUser && status.equals("done") && tmp.getStatus().equals("ind")) {
            System.out.println("Order is done, have a nice day");
            tmp.setStatus(status);
            return;
        }
        else if (tmp.getDeliverymanId() == loggedInDeliveryman && status.equals("ind") && tmp.getStatus().equals("inr")) {
            System.out.println("Order is done, have a nice day");
            tmp.setStatus(status);
            return;
        }
        System.out.println("you don't have access to change the status");
    }
    public int showDeliveryPrice(int restaurantID) {
        if (loggedInUser == -1) {
            System.out.println("Login as user first!");
            return -1;
        }
        int price = map.getDistance(((User)accounts.get(loggedInUser)).getSelectedLocation(), restaurants.get(restaurantID).getLocation()) + 5000;
        System.out.println("Delivery price is : " + price);
        return price;
    }
    public void showOrderHistory() {
        if (loggedInUser == -1 && loggedInDeliveryman == -1 && selectedRestaurant == -1) {
            System.out.println("login as User or deliverymen or select your owen restaurant!");
            return;
        }
        HashSet<Integer> tmp;
        if (loggedInUser != -1)
            tmp = ((User) accounts.get(loggedInUser)).getOrders();
        else if (loggedInDeliveryman != -1)
            tmp = ((Deliveryman) accounts.get(loggedInUser)).getOrders();
        else
            tmp = restaurants.get(selectedRestaurant).getOrders();
        for (int i : tmp)
            System.out.println("ID: " + i + ", STATUS: " + orders.get(i).getStatus() + " Restaurant : " + orders.get(i).getRestaurantId() + ", PRICE: " + getPrice(orders.get(i).getItems()) + " Delivery price : " + orders.get(i).getDeliveryPrice());
    }
    public void selectRestaurant(int restaurantID) {
        if (loggedInAdmin == -1 && loggedInUser == -1) {
            System.out.println("login first !");
            return;
        }
        if (!restaurants.containsKey(restaurantID)) {
            System.out.println("invalid id !");
            return;
        }
        selectedRestaurant = restaurantID;
        System.out.println("Restaurant successfully selected.");
    }
    public ArrayList<Integer> getNearestRestaurants() {
        ArrayList<Integer> result = new ArrayList<>(restaurants.keySet());
        for (int i = 0; i < result.size(); i++)
            for (int j = i + 1; j < result.size(); j++)
                if (map.getDistance(restaurants.get(i).getLocation(), ((User)accounts.get(loggedInUser)).getSelectedLocation()) > map.getDistance(restaurants.get(j).getLocation(), ((User)accounts.get(loggedInUser)).getSelectedLocation()))
                    Collections.swap(result, i, j);
        if (result.size() > 5)
            for (int i = 5; i < result.size(); i++)
                result.remove(i);
        return result;
    }
    public ArrayList<String> searchRestaurantName(String name) {
        ArrayList<String> result = new ArrayList<>();
        boolean check = false;
        if (loggedInAdmin != -1) {
            Admin admin = (Admin)accounts.get(loggedInAdmin);
            for (Integer restaurantId : admin.getRestaurants())
                if (restaurants.get(restaurantId).getName().startsWith(name)) {
                    result.add(restaurants.get(restaurantId).getName());
                    System.out.println("Restaurant name : " + restaurants.get(restaurantId).getName() + " Restaurant ID: " + restaurantId);
                    check = true;
                }
            if (!check)
                System.out.println("There is no restaurant with this given name!");
            return result;
        } else if (loggedInUser != -1) {
            for (int id : restaurants.keySet())
                if (restaurants.get(id).getName().startsWith(name)) {
                    result.add(restaurants.get(id).getName());
                    System.out.println("Restaurant name : " + restaurants.get(id).getName() + ", Restaurant ID: " + id);
                    check = true;
                }
            if (!check)
                System.out.println("There is no restaurant with this given name!");
            return result;
        } else
            System.out.println("Login first!");
        return null;
    }
    public ArrayList<String> searchFoodName(String name) {
        ArrayList<String> result = new ArrayList<>();
        if (loggedInUser == -1) {
            System.out.println("No one has logged in!!!");
            return null;
        } else
            for (Integer foodId : foods.keySet())
                if (foods.get(foodId).getName().startsWith(name)) {
                    result.add(foods.get(foodId).getName());
                    System.out.println("Food name: " + foods.get(foodId).getName() + ", Food price: "+ foods.get(foodId).getFoodPrice());
                }
        return result;
    }
    public void searchFoodName(int restaurantID, String name) {
        if (loggedInUser == -1) {
            System.out.println("No one has logged in!!!");
        } else if(restaurantID == -1) {
            System.out.println("no restaurant has been selected!!!");
        } else
            for (Integer foodId : restaurants.get(restaurantID).getMenu())
                if (foods.get(foodId).getName().startsWith(name))
                    System.out.println("food name: "+foods.get(foodId).getName()+" food price: "+ foods.get(foodId).getFoodPrice());
    }
    public void addComment(String content) {
        if (loggedInUser == -1) {
            System.out.println("login az user !");
            return;
        }
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else if (restaurants.get(foods.get(selectedFood).getRestaurant()).getAdmin() == loggedInAdmin) {
            System.out.println("You cannot write comments on your own products!");
        } else {
            Comment tmp = new Comment(comments.size(), loggedInUser, selectedFood, 1, content);
            ((User)accounts.get(loggedInUser)).addComment(comments.size());
            foods.get(selectedFood).addComment(comments.size());
            tmp.save();
            comments.put(tmp.getId(), tmp);
            System.out.println("Comment added successfully.");
        }
    }
    public void addCommentRestaurant(String content) {
        if (loggedInUser == -1) {
            System.out.println("login az user !");
            return;
        }
        if (selectedRestaurant == -1) {
            System.out.println("No Restuarant has been selected!");
        }
        else {
            Comment tmp = new Comment(comments.size(), loggedInUser, selectedRestaurant, 2, content);
            ((User)accounts.get(loggedInUser)).addComment(comments.size());
            restaurants.get(selectedRestaurant).addComment(comments.size());
            tmp.save();
            comments.put(tmp.getId(), tmp);
            System.out.println("Comment added successfully.");
        }
    }
    public void removeComment(int id) {
        if (loggedInUser == -1) {
            System.out.println("login az user !");
            return;
        }
        if (!comments.containsKey(id)) {
            System.out.println("invalid comment id!");
            return;
        }
        Comment cmp = comments.get(id);
        if (cmp.getReceptionType() == 1) {
            if (!foods.get(cmp.getReceptionId()).getComments().contains(id)) {
                System.out.println("invalid comment id!");
            } else if (!((User) accounts.get(loggedInUser)).getComments().contains(id)) {
                System.out.println("you are not the owner of the coment !");
            } else {
                foods.get(cmp.getReceptionId()).removeComment(id);
                ((User) accounts.get(loggedInUser)).removeComment(id);
                System.out.println("comment removed successfully!");
            }
        }
        else {
            if (!restaurants.get(cmp.getReceptionId()).getComments().contains(id)) {
                System.out.println("invalid comment id!");
            } else if (!((User) accounts.get(loggedInUser)).getComments().contains(id)) {
                System.out.println("you are not the owner of the coment !");
            } else {
                restaurants.get(cmp.getReceptionId()).removeComment(id);
                ((User) accounts.get(loggedInUser)).removeComment(id);
                System.out.println("comment removed successfully!");
            }
        }
    }
    public void editComment(int commentID, String content) {
        if (loggedInUser == -1) {
            System.out.println("login first !");
            return;
        }
        if (!comments.containsKey(commentID)) {
            System.out.println("comment id does not exist!");
            return;
        }
        Comment tmp = comments.get(commentID);
        if (tmp.getCommenterID() != loggedInUser) {
            System.out.println("this is not your comment !");
            return;
        }
        tmp.setContent(content);
        System.out.println("content change successfully");
    }
    public void submitRating(int rating) {
        if (loggedInUser == -1) {
            System.out.println("login as User first !");
            return;
        }
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        }
        else if (foods.get(selectedFood).getRatings().containsKey(loggedInUser)) {
            System.out.println("You have already added a rating for this food!");
        }
        else if (rating < 0 || rating > 5) {
            System.out.println("valid range for rating is 0-5!");
            return;
        }
        else {
            foods.get(selectedFood).addRating(loggedInAccount, rating);
            System.out.println("Rating submitted successfully.");
        }
    }
    public void editRating(int rating) {
        if (loggedInUser == -1) {
            System.out.println("login as User first !");
            return;
        }
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        }
        else if (!foods.get(selectedFood).getRatings().containsKey(loggedInAccount)) {
            System.out.println("You have not submitted a rating for this food!");
        }
        else if (rating < 0 || rating > 5) {
            System.out.println("valid range for rating is 0-5!");
            return;
        }
        else {
            foods.get(selectedFood).editRating(loggedInAccount, rating);
            System.out.println("Rating submitted successfully.");
        }
    }
    public void addToCart(int count) {
        if(loggedInUser == -1) {
            System.out.println("No one has logged in!!!");
            return;
        }
        else if(selectedFood == -1) {
            System.out.println("you have not selected any food!!!");
            return;
        }
        Order cart = ((User)accounts.get(loggedInUser)).getCart();
        if (cart.getRestaurantId() != -1 && cart.getRestaurantId() != selectedRestaurant) {
            System.out.println("you can order food from one restaurant at each time");
            return;
        }
        if (cart.getRestaurantId() == -1) {
            cart.setRestaurantId(selectedRestaurant);
            int price = map.getDistance(cart.getUserLocation(), restaurants.get(selectedRestaurant).getLocation()) + 5000;
            cart.setDeliveryPrice(price);
            System.out.println("cart selected restaurant : " + selectedRestaurant + " price of delivery : " + price);
        }
        if (!foods.get(selectedFood).getActive()) {
            System.out.println("this food is not active sorry");
            return;
        }
        cart.addItem(selectedFood, count);
        System.out.println("added successfully.");
    }
    public void removeFromCart(int count) {
        if(loggedInUser == -1) {
            System.out.println("No one has logged in!!!");
            return;
        }
        else if(selectedFood == -1) {
            System.out.println("you have not selected any food!!!");
            return;
        }
        Order cart = ((User)accounts.get(loggedInUser)).getCart();
        if (cart.getRestaurantId() != -1 && cart.getRestaurantId() != selectedRestaurant) {
            System.out.println("you can order food from one restaurant at each time");
            return;
        }
        if (cart.getRestaurantId() == -1) {
            cart.setRestaurantId(selectedRestaurant);
        }
        if (!cart.getItems().containsKey(selectedFood)) {
            System.out.println("you don't have any of this food in your cart");
            return;
        }
        if (cart.getItems().get(selectedFood) < count) {
            System.out.println("wrong count !!!!!");
            return;
        }
        cart.removeItem(selectedFood, count);
        if (cart.getItems().size() == 0) {
            cart.setRestaurantId(-1);
            cart.setDeliveryPrice(0);
        }
        System.out.println("items removed successfully successfully.");
    }
    public void selectOrder(int id) {
        if (loggedInAccount == -1) {
            System.out.println("login first !");
            return;
        }
        if (!orders.containsKey(id)) {
            System.out.println("order id does not exist!");
            return;
        }
        if (loggedInAdmin != -1) {
            if (selectedRestaurant == -1) {
                System.out.println("select your restaurant first !");
                return;
            }
            if (orders.get(id).getRestaurantId() != selectedRestaurant) {
                System.out.println("this order id is not yours !");
                return;
            }
        }
        else if (loggedInUser != -1){
            if (orders.get(id).getUserId() != loggedInUser) {
                System.out.println("this order id is not yours !");
                return;
            }
        }
        else if (orders.get(id).getDeliverymanId() != loggedInDeliveryman) {
            System.out.println("this order id is not yours !");
            return;
        }
        selectedOrder = id;
        System.out.println("order " + id + " selected successfully");
    }
    public void unselectOrder() {
        if (selectedOrder == -1) {
            System.out.println("there is no seleceted order !");
            return;
        }
        selectedOrder = -1;
        System.out.println("ordered unselected successfully");
    }
    public void showCart() {
        if(loggedInUser == -1)  {
            System.out.println("No one has logged in!!!");
        }
        else if(((User)accounts.get(loggedInUser)).getCart().getItems().size() == 0)  {
            System.out.println("There is no order!!!");
        }
        else  {
            System.out.println("your cart is: ");
            for(Map.Entry<Integer, Integer> item : ((User) accounts.get(loggedInUser)).getCart().getItems().entrySet()) {
                System.out.println("food name: " + foods.get(item.getKey()).getName() +  " count: " + item.getValue() + " discount: %" + foods.get(item.getValue()).getFoodDiscount() + " total-price: " + foods.get(item.getValue()).getFoodPrice() * item.getValue());
            }
            System.out.println(" Delivery price : " + ((User)accounts.get(loggedInUser)).getCart().getDeliveryPrice());
        }
    }
    public int confirmOrder() {
        if (loggedInUser == -1) {
            System.out.println("No one has logged in!");
            return 1;
        } else if(((User)accounts.get(loggedInUser)).getCart().getItems() == null || ((User)accounts.get(loggedInUser)).getCart().getItems().isEmpty()) {
            System.out.println("You have not chosen anything!");
            return 2;
        } else {
            if (((User)accounts.get(loggedInUser)).getBalance() < getPrice(((User)accounts.get(loggedInUser)).getCart().getItems()) + ((User)accounts.get(loggedInUser)).getCart().getDeliveryPrice()) {
                System.out.println("Insufficient balance!");
                return 3;
            }
            Order tmp = ((User)accounts.get(loggedInUser)).getCart();
            boolean koft = false;
            for (int i : tmp.getItems().keySet())
                if (!foods.get(i).getActive()) {
                    System.out.println("Food with ID : " + i + " is no longer active!");
                    koft = true;
                }
            if (koft) {
                System.out.println("Please remove the non-active food(s) from your cart.");
                return 4;
            }
            double price = getPrice(((User)accounts.get(loggedInUser)).getCart().getItems());
            ((User)accounts.get(loggedInUser)).addBalance(- price - ((User)accounts.get(loggedInUser)).getCart().getDeliveryPrice());
            System.out.println("The order has been confirmed and sent to the restaurant.");
            tmp.setStatus("inr");
            Order cart = new Order(-(loggedInAccount + 1), -1, loggedInAccount, -1, tmp.getUserLocation());
            cart.setStatus("pend");
            ((User)accounts.get(loggedInUser)).setCart(cart);
            tmp.setId(orders.size());
            restaurants.get(tmp.getRestaurantId()).addBalance(price);
            restaurants.get(tmp.getRestaurantId()).addOrder(orders.size());
            ((User)accounts.get(loggedInUser)).addOrder(orders.size());
            tmp.setId(orders.size());
            orders.put(tmp.getId(), tmp);
            tmp.save();
            return 0;
        }
    }
    public int showEstimatedDeliveryTime() {
        if (loggedInUser == -1 && loggedInDeliveryman == -1) {
            System.out.println("Login as deliverymen or User first!");
            return -1;
        }
        if (selectedOrder == -1 && selectedRestaurant == -1) {
            System.out.println("Select order or restaurant first!");
            return -2;
        }
        int tmp, tmp2;
        if (loggedInUser != -1)
            tmp = ((User)accounts.get(loggedInUser)).getSelectedLocation();
        else
            tmp = ((Deliveryman)accounts.get(loggedInDeliveryman)).getLocation();
        if (selectedOrder == -1)
            tmp2 = restaurants.get(selectedRestaurant).getLocation();
        else
            tmp2 = restaurants.get(orders.get(selectedOrder).getRestaurantId()).getLocation();
        System.out.println("Estimated Delivery time from restaurant to User selected location : " + map.getDistance(tmp, tmp2));
        return map.getDistance(tmp, tmp2);
    }
    public void chargeAccount(int value) {
        if(loggedInUser == -1) {
            System.out.println("No one has logged in!!!");
        }
        else  {
            ((User)accounts.get(loggedInUser)).addBalance(value);
            System.out.println("your balance charged successfully.");
        }
    }
    public void displayAccountBalance() {
        if(loggedInUser == -1 && loggedInDeliveryman == -1)  {
            System.out.println("login first !!");
            return;
        }
        if (loggedInUser != -1) {
            System.out.println("your balance is: " + ((User)accounts.get(loggedInUser)).getBalance());
        }
        else
            System.out.println("your balance is: " + ((Deliveryman)accounts.get(loggedInDeliveryman)).getBalance());
    }
    public void showPath() {
        if (loggedInUser == -1 && loggedInDeliveryman == -1) {
            System.out.println("login as deliverymen or User first !");
            return;
        }
        if (selectedOrder == -1 ) {
            System.out.println("select order first !");
            return;
        }
        int tmp, tmp2;
        tmp = ((Deliveryman)accounts.get(loggedInDeliveryman)).getLocation();
        if (selectedOrder == -1)
            tmp2 = restaurants.get(selectedRestaurant).getLocation();
        else
            tmp2 = restaurants.get(orders.get(selectedOrder).getRestaurantId()).getLocation();
        Path path = map.getShortestPath(tmp2, tmp);
        System.out.println("estimated time : " + path.getTime());
        for (int i = 0; i < path.getNodeCount() - 1; i++)
            System.out.print(path.getNode(i) + " -> ");
        System.out.println(path.getNode(path.getNodeCount() - 1) + ".");
    }
    public void suggestFood() {
        if(loggedInUser == -1) {
            System.out.println("No one has logged in!!!");
        }
        else if(selectedRestaurant == -1) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for(Food i : foods.values()) {
                if (i.getActive()) {
                    tmp.add(i.getId());
                }
            }
            for(int i=0;i<tmp.size();i++) {
                for(int j=i+1;j<tmp.size();j++) {
                    if(foods.get(tmp.get(i)).getAverageRating() > foods.get(tmp.get(j)).getAverageRating()) {
                        Collections.swap(tmp,i,j);
                    }
                }
            }
            System.out.println("suggestions : ");
            for(int i = 0; i < min(tmp.size() / 3, 5); i++) {
                Food tmp2 = foods.get(tmp.get(i));
                System.out.println("id : " + tmp2.getId() + " restuarant id :" + tmp2.getRestaurant() + " ratings :  " + tmp2.getAverageRating() + " price : " + tmp2.getFoodPrice() + " discount : " + tmp2.getFoodDiscount());
            }
        }
        else {
            ArrayList<Integer> tmp = new ArrayList<>();
            for(Integer foodId : restaurants.get(selectedRestaurant).getMenu()) {
                if (foods.get(foodId).getActive()) {
                    tmp.add(foodId);
                }
            }
            for(int i=0;i<tmp.size();i++) {
                for(int j=i+1;j<tmp.size();j++) {
                    if(foods.get(tmp.get(i)).getAverageRating() > foods.get(tmp.get(j)).getAverageRating()) {
                        Collections.swap(tmp,i,j);
                    }
                }
            }
            System.out.println("suggestions : ");
            for(int i = 0; i < min(tmp.size() / 3, 5); i++) {
                Food tmp2 = foods.get(tmp.get(i));
                System.out.println("id : " + tmp2.getId() + " ratings :  " + tmp2.getAverageRating() + " price : " + tmp2.getFoodPrice() + " discount : " + tmp2.getFoodDiscount());
            }
        }
    }
    public void setLocation(int id) {
        //for delivery man only
        if (loggedInDeliveryman == -1) {
            System.out.println("login as delivery man");
            return;
        }
        if (map.getN() < id) {
            System.out.println("invalid location node id!");
            return;
        }
        Deliveryman tmp = (Deliveryman) accounts.get(loggedInDeliveryman);
        tmp.setLocation(id);
        System.out.println("location set successfully");
    }
    public void getPathToRestaurant() {
        if (loggedInDeliveryman == -1) {
            System.out.println("first login as a deliverymen !");
            return;
        }
        Deliveryman tmp = ((Deliveryman) accounts.get(loggedInDeliveryman));
        if (tmp.getActiveOrder() == -1) {
            System.out.println("you don't have any active order!");
            return;
        }
        Path path = map.getShortestPath(tmp.getLocation(), restaurants.get(orders.get(tmp.getActiveOrder()).getRestaurantId()).getLocation());
        System.out.println("estimated time : " + path.getTime());
        for (int i = 0; i < path.getNodeCount() - 1; i++)
            System.out.print(path.getNode(i) + " -> ");
        System.out.println(path.getNode(path.getNodeCount() - 1) + ".");
    }
    public void getPathToUser() {
        if (loggedInDeliveryman == -1) {
            System.out.println("first login as a deliverymen !");
            return;
        }
        Deliveryman tmp = ((Deliveryman) accounts.get(loggedInDeliveryman));
        if (tmp.getActiveOrder() == -1) {
            System.out.println("you don't have any active order!");
            return;
        }
        Path path = map.getShortestPath(tmp.getLocation(), restaurants.get(orders.get(tmp.getActiveOrder()).getRestaurantId()).getLocation());
        System.out.println("estimated time : " + path.getTime());
        for (int i = 0; i < path.getNodeCount() - 1; i++)
            System.out.print(path.getNode(i) + " -> ");
        System.out.println(path.getNode(path.getNodeCount() - 1) + ".");
    }
    public void withdraw() {
        if (loggedInDeliveryman == -1 && loggedInAdmin == -1) {
            System.out.println("first login as a deliverymen or admin!");
            return;
        }
        if (loggedInDeliveryman != -1) {
            Deliveryman tmp = ((Deliveryman) accounts.get(loggedInDeliveryman));
            System.out.println(tmp.getBalance() + " $ withdrew ");
            tmp.withdraw();
            return;
        }
        else if (selectedRestaurant == -1) {
            System.out.println("first select restaurant");
            return;
        }
        Restaurant tmp = restaurants.get(selectedRestaurant);
        System.out.println(tmp.getBalance() + " $ withdrew ");
        tmp.withdraw();
    }
    public void acceptOrder(int id) {
        if (loggedInDeliveryman == -1) {
            System.out.println("login az deliverymen !");
            return;
        }
        if (((Deliveryman)accounts.get(loggedInDeliveryman)).getActiveOrder() != -1){
            System.out.println("You already have an active order!");
            return;
        }
        if (!orders.containsKey(id)) {
            System.out.println("Order doesn't exist!");
            return;
        }
        if (orders.get(id).getDeliverymanId() != -1){
            System.out.println("Order is taken!");
            return;
        }
        orders.get(id).setDeliverymanId(loggedInDeliveryman);
        ((Deliveryman)accounts.get(loggedInDeliveryman)).addOrder(id);
        ((Deliveryman)accounts.get(loggedInDeliveryman)).setActiveOrder(id);
        System.out.println("Order accepted successfully.");
    }
    public ArrayList<Integer> showAvailableOrders() {
        if (loggedInDeliveryman == -1) {
            System.out.println("Deliveryman has not logged in!");
            return null;
        }
        else {
            ArrayList<Integer> tmp = new ArrayList<>();
            for (Order i : orders.values())
                if (i.getStatus().equals("inr") && i.getDeliverymanId() == -1)
                    tmp.add(i.getId());
            for (int i = 0; i < tmp.size(); i++)
                for (int j = 0; j < tmp.size(); j++)
                    if (map.getDistance(restaurants.get(orders.get(tmp.get(i)).getRestaurantId()).getLocation(), ((Deliveryman) accounts.get(loggedInDeliveryman)).getLocation()) +
                            map.getDistance(restaurants.get(orders.get(tmp.get(i)).getRestaurantId()).getLocation(), ((User)accounts.get(orders.get(tmp.get(i)).getUserId())).getSelectedLocation()) >
                            map.getDistance(restaurants.get(orders.get(tmp.get(j)).getRestaurantId()).getLocation(), ((Deliveryman) accounts.get(loggedInDeliveryman)).getLocation()) +
                                    map.getDistance(restaurants.get(orders.get(tmp.get(j)).getRestaurantId()).getLocation(), ((User)accounts.get(orders.get(tmp.get(j)).getUserId())).getSelectedLocation())) {
                        Collections.swap(tmp,i,j);
                    }
            ArrayList<Integer> result = new ArrayList<>();
            for (int i = 0; i < Math.min(5, tmp.size()); i++) {
                result.add(tmp.get(i));
                Order tmp2 = orders.get(tmp.get(i));
                int koft = map.getDistance(restaurants.get(orders.get(tmp.get(i)).getRestaurantId()).getLocation(), ((Deliveryman) accounts.get(loggedInDeliveryman)).getLocation()) + map.getDistance(restaurants.get(orders.get(tmp.get(i)).getRestaurantId()).getLocation(), ((User)accounts.get(orders.get(tmp.get(i)).getUserId())).getSelectedLocation());
                System.out.println("id : " + tmp2.getId() + " Restaurant id : " + tmp2.getRestaurantId() + " Estimated time : " + koft);
            }
            return result;
        }
    }
    public String forgetPasswordPressed(String username) {
        for(Account account : accounts.values()) {
            if(account.getUsername().equals(username)) {
                System.out.println(account.getRecoveryQuestion());
                return account.getRecoveryQuestion();
            }
        }
        return null;
    }
    public String checkRecoveryQuestionAnswer(String username,String recoveryQuestionAnswer) {
        for(Account account : accounts.values()) {
            if(account.getUsername().equals(username)) {
                if(account.getRecoveryQuestionAnswer().equals(recoveryQuestionAnswer)) {
                    System.out.println(account.getPassword());
                    return account.getPassword();
                }
                else {
                    System.out.println("Your answer is wrong!!!");
                    return null;
                }
            }
        }
        return null;
    }
    public void selectLocation(int id) {
        if (loggedInUser == -1) {
            System.out.println("you should login as User!");
            return;
        }
        if (map.getN() < id) {
            System.out.println("invalid id");
            return;
        }
        User tmp = (User) accounts.get(loggedInUser);
        if (!tmp.getLocations().contains(id)) {
            System.out.println("this location is not in your locations list, please add it first!");
            return;
        }
        tmp.getCart().setUserLocation(id);
        tmp.setSelectedLocation(id);
        System.out.println("location set successfully");

    }
    public void showSelectedLocation() {
        if (loggedInUser == -1) {
            System.out.println("you should login as User!");
            return;
        }
        System.out.println("selected location node id : " + ((User)accounts.get(loggedInUser)).getSelectedLocation());
    }
    public void addRestaurant(String name, String type, int locationId) {
        if (loggedInAdmin == -1) {
            System.out.println("first login as an admin !!!!!!");
            return;
        }
        Admin acc = (Admin) accounts.get(loggedInAdmin);
        for (Restaurant i : restaurants.values()) {
            if (i.getName().equals(name)) {
                System.out.println("there exist a restaurant with this name , please change it");
                return;
            }
        }
        Restaurant tmp = new Restaurant(locationId, name, type, restaurants.size());
        acc.addRestaurant(tmp.getId());
        restaurants.put(tmp.getId(), tmp);
        tmp.save();
        tmp.setAdmin(loggedInAdmin);
        System.out.println("restaurant added successfully");
    }
    public ArrayList<Integer> nearRestaurant() {
        ArrayList<Integer> tmp2 = new ArrayList<>();
        if (loggedInUser == -1) {
            System.out.println("login as User!");
            return tmp2;
        }
        ArrayList<Integer> tmp = new ArrayList<>(restaurants.keySet());
        User acc = (User) accounts.get(loggedInUser);
        for (int i = 0; i < tmp.size(); i++) {
            for (int j = 0; j < tmp.size() - 1; j++) {
                int loc1 = restaurants.get(tmp.get(j)).getLocation(), loc2 = restaurants.get(tmp.get(j + 1)).getLocation();
                if (map.getDistance(loc1, acc.getSelectedLocation()) > map.getDistance(loc2, acc.getSelectedLocation()))
                    Collections.swap(tmp, j, j + 1);
            }
        }
        System.out.println("Near Restaurants : ");

        for (int i = 0; i < min(tmp.size(), 5); i++){
            tmp2.add(tmp.get(i));
            System.out.println();
            System.out.println("id : " + tmp.get(i) + "location : " + restaurants.get(tmp.get(i)).getLocation() + " name : " + restaurants.get(tmp.get(i)).getName());
        }
        return tmp2;
    }
    public void getFood() {
        if (loggedInDeliveryman == -1) {
            System.out.println("login az deliverymen!");
            return;
        }
        Deliveryman acc = (Deliveryman) accounts.get(loggedInDeliveryman);
        if (acc.getActiveOrder() == -1) {
            System.out.println("you don;t have any active orders!");
            return;
        }
        Order ord = orders.get(acc.getActiveOrder());
        if (restaurants.get(ord.getRestaurantId()).getLocation() != acc.getLocation()) {
            System.out.println("you need to be at restaurant location!");
            return;
        }
        ord.setStatus("ind");
        System.out.println("order is now in ind mode");
    }
    public void giveFood() {
        if (loggedInDeliveryman == -1) {
            System.out.println("login az deliverymen!");
            return;
        }
        Deliveryman acc = (Deliveryman) accounts.get(loggedInDeliveryman);
        if (acc.getActiveOrder() == -1) {
            System.out.println("you don;t have any active orders!");
            return;
        }
        Order ord = orders.get(acc.getActiveOrder());
        if (ord.getUserLocation() != acc.getLocation()) {
            System.out.println("you need to be at User location!");
            return;
        }
        ord.setStatus("Done");
        acc.addBalance(ord.getDeliveryPrice());
        System.out.println("order is now in ind mode");
    }
    public double getTraffic(int u, int v) {
        if (map.getN() < u || map.getN() < v) {
            System.out.println("invalid edge id");
            return 0;
        }
        return map.getTraffic(u, v);
    }
    public void setTraffic(int u, int v, double traffic) {
        if (map.getN() < u || map.getN() < v) {
            System.out.println("invalid edge id");
            return;
        }
        map.setTraffic(u, v, traffic);
    }
    public void setFoodFoodType(String type) {
        if (loggedInAdmin == -1) {
            System.out.println("login az admin!");
            return;
        }
        if (selectedRestaurant == -1) {
            System.out.println("select restuarant first!");
            return;
        }
        if (selectedFood == -1) {
            System.out.println("select food first");
            return;
        }
        if (restaurants.get(foods.get(selectedFood).getRestaurant()).getAdmin() != loggedInAdmin) {
            System.out.println("you are not the owner !");
            return;
        }
        foods.get(selectedFood).setFoodType(type);
    }
    public void showFoodFoodType(int id) {
        if (loggedInAccount == -1) {
            System.out.println("login first!");
            return;
        }
        if (!foods.containsKey(id)) {
            System.out.println("invalid food id");
            return;
        }
        System.out.println("Food type : " + foods.get(id).getFoodType());
    }
    public void forgotPassword(String userName) {
        if (loggedInAccount != -1) {
            System.out.println("You are already logged in.");
            return;
        }
        for (Account acc : accounts.values()) {
            if (userName.equals(acc.getUsername())) {
                System.out.println("answer to : " + acc.getRecoveryQuestion() + "  plus new password");
                tmpPassForgot = acc.getId();
                return;
            }
        }
        System.out.println("There is no account with this username!");
    }
    public void resetPassword (String RecovAns, String newPass) {
        if (loggedInAccount != -1) {
            System.out.println("You are already logged in.");
            return;
        }
        Account tmp = accounts.get(tmpPassForgot);
        tmpPassForgot = -1;
        if (tmp.getRecoveryQuestion().equals(RecovAns)) {
            if (Account.checkPassword(newPass)) {
                tmp.setPassword(newPass);
                System.out.println("password changed successfully");
                return;
            }
            else {
                System.out.println("Wrong password format !");
                return;
            }
        }
        System.out.println("Wrong answer !!!");
    }
}
