package com.example.barghifoodgraphics;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Core {
    MapG map;
    HashMap<Integer, Account> accounts;
    HashMap<Integer, Order> orders;
    HashMap<Integer, Restaurant> restaurants;
    HashMap<Integer, Food> foods;
    HashMap<Integer, Comment> comments;
    int loggedInAccount, loggedInUser, loggedInAdmin, loggedInDeliveryman, selectedRestaurant, selectedFood, selectedOrder;
    public double getPrice (HashMap<Integer, Integer> items) {
        double ans = 0.0;
        for (int i : items.keySet())
            ans += items.get(i) * foods.get(i).getPrice();
        return ans;
    }
    public Core() throws FileNotFoundException {
        map = new MapG();
        map.readGraphFromFile("src/data/graph.txt");
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
        for (int i = 0; i < use.listFiles().length; i++) {
            int koft = 0;
            ObjectMapper mapper = new ObjectMapper();
            try {
                accounts.put(i, mapper.readValue("src/data/accounts/" + i + "a.json", Admin.class));
            } catch (Exception e) {koft++;}
            try {
                accounts.put(i, mapper.readValue("src/data/accounts/" + i + "u.json", User.class));
            } catch (Exception e) {koft++;}
            try {
                accounts.put(i, mapper.readValue("src/data/accounts/" + i + "d.json", Deliveryman.class));
            } catch (Exception e) {koft++;}
            if (koft == 3)
                System.out.println("there is an missing file with id : " + i + " in accounts database");
        }
        // Reading orderss
        for (int i = 0; i < ord.listFiles().length; i++) {
            Order result;
            ObjectMapper mapper = new ObjectMapper();
            try {
                result = mapper.readValue("src/data/orders/" + i + ".json", Order.class);
                orders.put(result.getId(), result);
            } catch (Exception e) {
                System.out.println("there is an missing file in orders data base");
            }
        }
        // Reading restaurants
        for (int i = 0; i < rest.listFiles().length; i++) {
            Restaurant result;
            ObjectMapper mapper = new ObjectMapper();
            try {
                result = mapper.readValue("src/data/restaurants/" + i + ".json", Restaurant.class);
                restaurants.put(result.getId(), result);
            } catch (Exception e) {
                System.out.println("there is an missing file in restaurants database");
            }
        }
        // Reading comments
        for (int i = 0; i < com.listFiles().length; i++) {
            Comment result;
            ObjectMapper mapper = new ObjectMapper();
            try {
                result = mapper.readValue("src/data/comments/" + i + ".json", Comment.class);
                comments.put(result.getId(), result);
            } catch (Exception e) {
                System.out.println("there is an missing file in comments database");
            }
        }
        // Reading users
        for (int i = 0; i < fd.listFiles().length; i++) {
            Food result;
            ObjectMapper mapper = new ObjectMapper();
            try {
                result = mapper.readValue("src/data/foods/" + i + ".json", Food.class);
                foods.put(result.getId(), result);
            } catch (Exception e) {
                System.out.println("there is an missing file in foods database");
            }
        }
    }
    
    public void login(String userName, String password) {
        if (loggedInAccount != -1) {
            System.out.println("You are already logged in.");
            return;
        }
        for (Account acc : accounts.values()) {
            if (userName.equals(acc.getUsername())) {
                if (password.equals(acc.getPassword())) {
                    System.out.println("Logged in successfully.");
                    if (acc.getType().equals("User"))
                        loggedInUser = acc.getId();
                    else if (acc.getType().equals("Admin"))
                        loggedInAdmin = acc.getId();
                    else
                        loggedInDeliveryman = acc.getId();
                    loggedInAccount = acc.getId();
                    return;
                }
                System.out.println("Incorrect password!");
                return;
            }
        }
        System.out.println("There is no account with this username!");
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
    public void addUser(String username, String password, String recoveryQuestion, String recoveryQuestionAnswer) {
        User tmp = new User(username, password, recoveryQuestion, recoveryQuestionAnswer, accounts.size());
        tmp.save();
        accounts.put(tmp.getId(), tmp);
    }
    public void addDelivery(String username, String password, String recoveryQuestion, String recoveryQuestionAnswer) {
        Deliveryman tmp = new Deliveryman(username, password, recoveryQuestion, recoveryQuestionAnswer, accounts.size());
        tmp.save();
        accounts.put(tmp.getId(), tmp);
    }
    public void addAdmin(String username, String password, String recoveryQuestion, String recoveryQuestionAnswer) {
        Admin tmp = new Admin(username, password, recoveryQuestion, recoveryQuestionAnswer, accounts.size());
        tmp.save();
        accounts.put(tmp.getId(), tmp);
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
        User tmp = (User) accounts.get(loggedInUser);
        tmp.addLocation(nodeId);
        System.out.println("location added successfully");
    }
    public void showFoodType() {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        }
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
        else if (!restaurants.get(selectedRestaurant).getActiveOrders().isEmpty()) {
            System.out.println("The restaurant has active orders!");
            return 2;
        }
        boolean koft = false;
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
        else if (!restaurants.get(selectedRestaurant).getActiveOrders().isEmpty()) {
            System.out.println("The restaurant has active orders!");
            return 2;
        }
        boolean koft = false;
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
        // TODO : taghirat gostarde !
        if (!forSure) {
            System.out.println("ARE YOU SURE YOU WANT TO EDIT THIS FOOD TYPE IN YOUR RESTAURANT?");
            return 5;
        }
        else if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return 1;
        }
        else if (!restaurants.get(selectedRestaurant).getActiveOrders().isEmpty()) {
            System.out.println("The restaurant has active orders!");
            return 2;
        }
        boolean koft = false;
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
            System.out.println("ID: " + i + ", NAME: " + foods.get(i).getName() + ", PRICE: " + foods.get(i).getPrice() + ", DISCOUNT: " + foods.get(i).getDiscount() + ", RATING: " + foods.get(i).getAverageRating() + ", IS ACTIVE: " + foods.get(i).getActive());
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
    public void addFood(String foodName, int foodPrice) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        }
        else if (restaurants.get(selectedRestaurant).getAdmin() != loggedInAdmin) {
            System.out.println("You are not the owner !");
        }
        for (int i : restaurants.get(selectedRestaurant).getMenu()){
            if (foods.get(i).getName() == foodName) {
                System.out.println("The selected restaurant has a food with the given name!");
                return;
            }
        }
        restaurants.get(selectedRestaurant).addFoodToMenu(foods.size());
        foods.put(foods.size(), new Food(foods.size(), foodPrice, selectedRestaurant, foodName));
        System.out.println("The food added successfully.");

    }
    public void activateFood(int foodID) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        }
        else if (restaurants.get(selectedRestaurant).getAdmin() != loggedInAdmin) {
            System.out.println("You are not the owner !");
        }
        else if (!restaurants.get(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        } else {
            foods.get(foodID).setActive(true);
            System.out.println("Information updated successfully.");
        }
    }
    public void deactivateFood(int foodID) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
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
            return;
        }
        else if (restaurants.get(selectedRestaurant).getAdmin() != loggedInAdmin) {
            System.out.println("You are not the owner !");
        }
        else if (!restaurants.get(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        }
        else {
            // TODO : time work of food discount
            foods.get(foodID).setDiscount(discountPercentage, timestamp);
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
                System.out.println("ID: " + i + ", USER: " + accounts.get(comments.get(i).getCommenter()).getUsername() + ", CONTENT: " + comments.get(i).getContent() + ", RESPONSE: " + comments.get(i).getAnswer());
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
                    System.out.println("Order  " + i + "  : " + "id : " + orderId + " Restaurant id : " + orders.get(orderId).getRestaurant() + " Status : " + orders.get(orderId).getStatus());
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
                    System.out.println("Order  " + i + "  : " + "id : " + orderId + " Deliverymen id : " + orders.get(orderId).getDeliveryman() + " Status : " + orders.get(orderId).getStatus());
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
                System.out.println("Order id : " + tmp2.getId() + " Restaurant location : " + restaurants.get(tmp2.getRestaurant()).getLocation() + " User location : " + tmp2.getUserLocation());
            }
        }
    }
    public void editOrderStatus(String status) {
        if (selectedOrder == -1) {
            System.out.println("no order has benn selected !");
            return;
        }
        if (!orders.containsKey(selectedOrder)) {
            System.out.println("order dose not exist");
            return;
        }
        Order tmp = orders.get(selectedOrder);
        if (tmp.getRestaurant() != selectedRestaurant && tmp.getUser() != loggedInUser && tmp.getDeliveryman() != loggedInDeliveryman) {
            System.out.println("you does not have access to this order !");
            return;
        }
        tmp.setStatus(status);
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
            System.out.println("ID: " + i + ", STATUS: " + orders.get(i).getStatus() + " Restaurant : " + orders.get(i).getRestaurant() + ", PRICE: " + getPrice(orders.get(i).getItems()));
    }
    /// modirate sefaresh :::::::
    public void selectRestaurant(int restaurantID) {
        if (loggedInAdmin == -1 && loggedInUser == -1) {
            System.out.println("login first !");
        } else {
            selectedRestaurant = restaurantID;
            System.out.println("Restaurant successfully selected.");
        }
    }
    public void searchRestaurantName(String name) {
        List<Account> tmp = new ArrayList<>(accounts.values());
        if(loggedInAdmin != -1)
        {
            boolean check = false;
            Admin admin = (Admin) tmp.get(loggedInAdmin);
            for(Integer restaurantId : admin.getRestaurants())
            {
                if(restaurants.get(restaurantId).getName().substring(0,name.length()-1).equals(name))
                {
                    System.out.println("Restaurant name : "+ restaurants.get(restaurantId).getName()+ " Restaurant ID: "+ restaurantId);
                    check = true;
                }
            }
            if(!check)
            {
                System.out.println("There is no restaurant with this given name!!!");
            }
        }
        else if(loggedInUser != -1)
        {
            boolean check = false;
            for(Account acc : tmp)
            {
                if(acc.getClass() == Admin.class)
                {
                    Admin admin = (Admin) acc;
                    for(Integer restaurantId : admin.getRestaurants())
                    {
                        if(restaurants.get(restaurantId).getName().substring(0,name.length()-1).equals(name))
                        {
                            System.out.println("Restaurant name : "+ restaurants.get(restaurantId).getName()+ " Restaurant ID: "+ restaurantId);
                            check = true;
                        }
                    }
                }
            }
            if(!check)
            {
                System.out.println("There is no restaurant with this given name!!!");
            }
        }
    }
    public void searchFoodName(String name) {
        if(loggedInUser == -1)
        {
            System.out.println("No one has logged in!!!");
        }
        else if(selectedRestaurant == -1)
        {
            System.out.println("no restaurant has been selected!!!");
        }
        else{
            for(Integer foodId : restaurants.get(selectedRestaurant).getMenu())
            {
                if(name.equals(foods.get(foodId).getName().substring(0,name.length())));
                {
                    System.out.println("food name: "+foods.get(foodId).getName()+" food price: "+ foods.get(foodId).getPrice());
                }
            }
        }
    }
    public void addComment(String content) {
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
        if (tmp.getCommenter() != loggedInUser) {
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
        else if (foods.get(selectedFood).getRaters().containsKey(loggedInUser)) {
            System.out.println("You have already added a rating for this food!");
        } else {
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
        else if (!foods.get(selectedFood).getRaters().containsKey(loggedInAccount)) {
            System.out.println("You have not submitted a rating for this food!");
        }
        else {
            foods.get(selectedFood).editRating(loggedInAccount, rating);
            System.out.println("Rating submitted successfully.");
        }
    }
    public void addToCart(int count) {
        if(loggedInUser == -1) {
            System.out.println("No one has logged in!!!");
        }
        else if(selectedFood == -1) {
            System.out.println("you have not selected any food!!!");
        }
        else {
            ((User)accounts.get(loggedInUser)).getCart().addItem(selectedFood, count);
            System.out.println("added successfully.");
        }
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
            if (orders.get(id).getRestaurant() != selectedRestaurant) {
                System.out.println("this order id is not yours !");
                return;
            }
        }
        else if (loggedInUser != -1){
            if (orders.get(id).getUser() != loggedInUser) {
                System.out.println("this order id is not yours !");
                return;
            }
        }
        else if (orders.get(id).getDeliveryman() != loggedInDeliveryman) {
            System.out.println("this order id is not yours !");
            return;
        }
        selectedOrder = id;
        System.out.println("order " + id + " selected successfully");
    }
    public void unselectOrder(int id) {
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
                System.out.println("food name: " + foods.get(item.getKey()).getName() +  " count: " + item.getValue() + " discount: %" + foods.get(item.getValue()).getDiscount() + " total-price: " + foods.get(item.getValue()).getPrice() * item.getValue());
            }
        }
    }
    public void confirmOrder() {
        if(loggedInUser == -1) {
            System.out.println("No one has logged in!!!");
        }
        else if(((User)accounts.get(loggedInUser)).getCart().getItems().size() == 0) {
            System.out.println("you have not chosen anything!!!");
        }
        else {
            System.out.println("Done!");
            Order tmp = ((User)accounts.get(loggedInUser)).getCart();
            tmp.setStatus("CONFIRMED");
            ((User)accounts.get(loggedInUser)).setCart(new Order());
            tmp.setId(orders.size());
            restaurants.get(tmp.getRestaurant()).addOrder(orders.size());
            tmp.setId(orders.size());
            orders.put(tmp.getId(), tmp);
            tmp.save();
        }
    }
    public void showEstimatedDeliveryTime() {
        if (loggedInUser == -1 && loggedInDeliveryman == -1) {
            System.out.println("login as deliverymen or User first !");
            return;
        }
        if (selectedOrder == -1 && selectedRestaurant == -1) {
            System.out.println("select order or restaurant first !");
            return;
        }
        int tmp = -1, tmp2 = -1;
        if (loggedInUser != -1)
            tmp = ((User)accounts.get(loggedInUser)).getSelectedLocation();
        else
            tmp = ((Deliveryman)accounts.get(loggedInDeliveryman)).getLocation();
        if (selectedOrder == -1)
            tmp2 = restaurants.get(selectedRestaurant).getLocation();
        else
            tmp2 = restaurants.get(orders.get(selectedOrder).getRestaurant()).getLocation();
        System.out.println("Estimated Delivery time from restaurant to User selected location : " + map.getDistance(tmp, tmp2));
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
            tmp2 = restaurants.get(orders.get(selectedOrder).getRestaurant()).getLocation();
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
            System.out.println("No restaurant has been selected!!!");
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
            for(int i = 0; i < tmp.size() / 3; i++) {
                Food tmp2 = foods.get(tmp.get(i));
                System.out.println("id : " + tmp2.getId() + " ratings :  " + tmp2.getAverageRating() + " price : " + tmp2.getPrice() + " discount : " + tmp2.getDiscount());
            }
        }
    }
    public void setLocation(int id) {
        //for delivery man only
        if (loggedInDeliveryman == -1) {
            System.out.println("login as delivery man");
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
        Path path = map.getShortestPath(tmp.getLocation(), restaurants.get(orders.get(tmp.getActiveOrder()).getRestaurant()).getLocation());
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
        Path path = map.getShortestPath(tmp.getLocation(), restaurants.get(orders.get(tmp.getActiveOrder()).getRestaurant()).getLocation());
        System.out.println("estimated time : " + path.getTime());
        for (int i = 0; i < path.getNodeCount() - 1; i++)
            System.out.print(path.getNode(i) + " -> ");
        System.out.println(path.getNode(path.getNodeCount() - 1) + ".");
    }
    public void withdraw() {
        if (loggedInDeliveryman == -1) {
            System.out.println("first login as a deliverymen !");
            return;
        }
        Deliveryman tmp = ((Deliveryman) accounts.get(loggedInDeliveryman));
        System.out.println(tmp.getBalance() + " $ withdrew ");
        tmp.withdraw();
    }
    public void acceptOrder(int id) {
        if (loggedInDeliveryman == -1) {
            System.out.println("login az deliverymen !");
            return;
        }
        if (orders.get(id).getDeliveryman() != -1){
            System.out.println("order is taken !");
            return;
        }
        if (((Deliveryman)accounts.get(loggedInDeliveryman)).getActiveOrder() != -1){
            System.out.println("you have an active order !");
            return;
        }
        orders.get(id).setDeliveryman(loggedInDeliveryman);
        ((Deliveryman)accounts.get(loggedInDeliveryman)).addOrder(id);
        ((Deliveryman)accounts.get(loggedInDeliveryman)).setActiveOrder(id);
        System.out.println("order accepted successfully!");
    }
    public void showAvailableOrders() {
        if(loggedInDeliveryman == -1) {
            System.out.println("Deliveryman has not logged in!!!");
        }
        else {
            ArrayList<Integer> tmp  =new ArrayList<>();
            for(Account account : accounts.values()) {
                if(account.getClass() == Admin.class) {
                    for(Integer restaurantId : ((Admin) account).getRestaurants()) {
                        if(restaurants.get(restaurantId).getActiveOrders().size() != 0) {
                            for(Integer orderId : restaurants.get(restaurantId).getActiveOrders()) {
                                tmp.add(orderId);
                            }
                        }
                    }
                }
            }
            for(int i=0;i<tmp.size();i++) {
                for(int j=0;j< tmp.size();j++) {
                    if(map.getDistance(restaurants.get(orders.get(tmp.get(i)).getRestaurant()).getLocation(), ((Deliveryman) accounts.get(loggedInDeliveryman)).getLocation()) +
                            map.getDistance(restaurants.get(orders.get(tmp.get(i)).getRestaurant()).getLocation(), ((User)accounts.get(orders.get(tmp.get(i)).getUser())).getSelectedLocation()) >
                            map.getDistance(restaurants.get(orders.get(tmp.get(j)).getRestaurant()).getLocation(), ((Deliveryman) accounts.get(loggedInDeliveryman)).getLocation()) +
                                    map.getDistance(restaurants.get(orders.get(tmp.get(j)).getRestaurant()).getLocation(), ((User)accounts.get(orders.get(tmp.get(j)).getUser())).getSelectedLocation())) {
                        Collections.swap(tmp,i,j);
                    }
                }
            }
            for(int i=0;i<5;i++) {
                Order tmp2 = orders.get(tmp.get(i));
                int koft = map.getDistance(restaurants.get(orders.get(tmp.get(i)).getRestaurant()).getLocation(), ((Deliveryman) accounts.get(loggedInDeliveryman)).getLocation()) + map.getDistance(restaurants.get(orders.get(tmp.get(i)).getRestaurant()).getLocation(), ((User)accounts.get(orders.get(tmp.get(i)).getUser())).getSelectedLocation());
                System.out.println("id : " + tmp2.getId() + " Restaurant id : " + tmp2.getRestaurant() + " Estimated time : " + koft);
            }
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
        User tmp = (User) accounts.get(loggedInUser);
        if (!tmp.getLocations().contains(id)) {
            System.out.println("this location is not in your locations list, please add it first!");
            return;
        }
        tmp.setSelectedLocation(id);
        System.out.println("location set successfully");

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
    }
}
