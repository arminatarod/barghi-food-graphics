package com.example.barghifoodgraphics;

import java.util.*;

public class Core {
    MapG map;

    public Core() {
        map = new MapG();
    }

    HashSet<Account> accounts;
    int loggedInAccount = -1, loggedInUser = -1, loggedInAdmin = -1, loggedInDeliveryman = -1;
    int selectedRestaurant = -1, selectedFood = -1;
    public void login(String userName, String password) {
        if (loggedInAccount != -1) {
            System.out.println("You are already logged in.");
            return;
        }
        for (Account acc : accounts) {
            if (userName.equals(acc.getUsername())) {
                if (password.equals(acc.getPassword())) {
                    System.out.println("Logged in successfully.");
                    if (acc.getType().equals("User"))
                        loggedInUser = acc.getId();
                    else if (acc.getType().equals("Admin"))
                        loggedInUser = acc.getId();
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
    }
    public void addUser(String username, String password, String recoveryQuestion, String recoveryQuestionAnswer) {
        accounts.add(new User(username, password, recoveryQuestion, recoveryQuestionAnswer, accounts.size()));
    }
    public void addDelivery(String username, String password, String recoveryQuestion, String recoveryQuestionAnswer) {
        accounts.add(new Deliveryman(username, password, recoveryQuestion, recoveryQuestionAnswer, accounts.size()));
    }
    public void addAdmin(String username, String password, String recoveryQuestion, String recoveryQuestionAnswer) {
        accounts.add(new Admin(username, password, recoveryQuestion, recoveryQuestionAnswer, accounts.size()));
    }
    public void showLocation() {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        }
        System.out.println(Restaurant.getRestaurant(selectedRestaurant).getLocation());
    }
    public void editLocation(int nodeID) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        }
        Restaurant.getRestaurant(selectedRestaurant).setLocation(nodeID);
        System.out.println("The location has been updated successfully.");
    }
    public void showFoodType() {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        }
        for (String s : Restaurant.getRestaurant(selectedRestaurant).getFoodType())
            System.out.println(s);
    }
    public int addFoodType(String type, boolean forSure) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return 1;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getActiveOrders().isEmpty()) {
            System.out.println("The restaurant has active orders!");
            return 2;
        } else if (Restaurant.getRestaurant(selectedRestaurant).getFoodType().contains(type)) {
            System.out.println("The restaurant already contains this food type!");
            return 3;
        } else if (!forSure) {
            System.out.println("ARE YOU SURE YOU WANT TO ADD THIS FOOD TYPE TO YOUR RESTAURANT?");
            return 4;
        } else {
            Restaurant.getRestaurant(selectedRestaurant).addFoodType(type);
            System.out.println("Food type added successfully.");
            return 0;
        }
    }
    public int removeFoodType(String type, boolean forSure) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return 1;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getActiveOrders().isEmpty()) {
            System.out.println("The restaurant has active orders!");
            return 2;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getFoodType().contains(type)) {
            System.out.println("The restaurant does not contain this food type!");
            return 3;
        } else if (!forSure) {
            System.out.println("ARE YOU SURE YOU WANT TO REMOVE THIS FOOD TYPE FROM YOUR RESTAURANT?");
            return 4;
        } else {
            Restaurant.getRestaurant(selectedRestaurant).removeFoodType(type);
            Restaurant.getRestaurant(selectedRestaurant).deleteMenu();
            return 0;
        }
    }
    public int editFoodType(String typeToRemove, String typeToAdd, boolean forSure) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return 1;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getActiveOrders().isEmpty()) {
            System.out.println("The restaurant has active orders!");
            return 2;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getFoodType().contains(typeToRemove)) {
            System.out.println("The restaurant does not contain the given food type!");
            return 3;
        } else if (Restaurant.getRestaurant(selectedRestaurant).getFoodType().contains(typeToAdd)) {
            System.out.println("The restaurant already contains the given food type!");
            return 4;
        } else if (!forSure) {
            System.out.println("ARE YOU SURE YOU WANT TO EDIT THIS FOOD TYPE IN YOUR RESTAURANT?");
            return 5;
        } else {
            Restaurant.getRestaurant(selectedRestaurant).removeFoodType(typeToRemove);
            Restaurant.getRestaurant(selectedRestaurant).addFoodType(typeToAdd);
            Restaurant.getRestaurant(selectedRestaurant).deleteMenu();
            return 0;
        }
    }
    public void showMenu() {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        }
        for (int i : Restaurant.getRestaurant(selectedRestaurant).getMenu())
            System.out.println("ID: " + i + ", NAME: " + Food.getFood(i).getName() + ", PRICE: " + Food.getFood(i).getPrice() + ", DISCOUNT: " + Food.getFood(i).getDiscount() + ", RATING: " + Food.getFood(i).getAverageRating() + ", IS ACTIVE: " + Food.getFood(i).getActive());
    }
    public void editFoodPrice(int foodID, int price) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        } else {
            Food.getFood(foodID).setPrice(price);
            System.out.println("Information updated successfully.");
        }
    }
    public void editFoodName(int foodID, String newName) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        } else {
            Food.getFood(foodID).setName(newName);
            System.out.println("Information updated successfully.");
        }
    }
    public void addFood(String foodName, int foodPrice) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        }
        else if(Restaurant.getRestaurant(selectedRestaurant).getFoodType().contains(foodName))
        {
            System.out.println("The selected restaurant has a food with the given name!");
        }
        else
        {
            int id = 10;//byd ye rvshi bra random krdn id estefade beshe mesln tike avl id resturan bashe tike dovom msln indexsh to list
            Restaurant.getRestaurant(selectedRestaurant).addFoodToMenu(foodName,id,Restaurant.getRestaurant(selectedRestaurant),foodPrice);
            System.out.println("The food added successfully.");
        }
    }
    public void activateFood(int foodID) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        } else {
            Food.getFood(foodID).setActive(true);
            System.out.println("Information updated successfully.");
        }
    }
    public void deactivateFood(int foodID) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        } else {
            Food.getFood(foodID).setActive(false);
            System.out.println("Information updated successfully.");
        }
    }
    public void discountFood(int foodID, int discountPercentage, int timestamp) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
            return;
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getMenu().contains(foodID)) {
            System.out.println("The selected restaurant does not have a food with the given ID!");
        } else {
            Food.getFood(foodID).setDiscount(discountPercentage, timestamp);
            System.out.println("Information updated successfully.");
        }
    }
    public void selectFood(int foodID) {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        } else if (!Restaurant.getRestaurant(selectedRestaurant).getMenu().contains(foodID)) {
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
            System.out.println("AVERAGE RATING: " + Food.getFood(selectedFood).getAverageRating());
        }
    }
    public void displayComments() {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else {
            for (int i : Food.getFood(selectedFood).getComments())
                System.out.println("ID: " + i + ", USER: " + Account.getAccount(Comment.getComment(i).getCommenter()).getUsername() + ", CONTENT: " + Comment.getComment(i).getContent() + ", RESPONSE: " + Comment.getComment(i).getAnswer());
        }
    }
    public void addResponse(int commentID, String message) {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else if (!Food.getFood(selectedFood).getComments().contains(commentID)) {
            System.out.println("The selected food does not have a comment with the given ID!");
        } else if (Comment.getComment(commentID).getAnswer() != null) {
            System.out.println("This comment has already been answered!");
        } else {
            Comment.getComment(commentID).setAnswer(message);
            System.out.println("Response added successfully.");
        }
    }
    public void editResponse(int commentID, String message) {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else if (!Food.getFood(selectedFood).getComments().contains(commentID)) {
            System.out.println("The selected food does not have a comment with the given ID!");
        } else if (Comment.getComment(commentID).getAnswer() == null) {
            System.out.println("This comment has never been answered!");
        } else {
            Comment.getComment(commentID).setAnswer(message);
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
            System.out.println("Restaurant unselected successfully.");
        }
    }
    public void displayActiveOrders() {
        if(loggedInUser == -1)
        {
            System.out.println("No one has logged in yet!!!");
        } else if (User.getUser(loggedInUser).getOrders().isEmpty()) {
            System.out.println("There is no order!!!");
        }else
        {
            int i=0;
            for(Integer orderId : User.getUser(loggedInUser).getOrders())
            {
                i++;
                System.out.println("Order ( "+i+" ) :");
                for(Map.Entry<Integer, Order.FoodData> item : Order.getOrder(orderId).getItems().entrySet())
                {
                    System.out.println("food name: "+Food.getFood(item.getKey()).getName()+" food id: "+
                            item.getKey()+" count: "+item.getValue().getCount()+" discount: %"+
                            item.getValue().getDiscount()+" total-price: "+item.getValue().getTotalPrice());
                }
            }
        }

    }
    public void editOrderStatus(int OrderId, String Status) {

    }
    // time handel shavad
    public void editOrderDeliveryTime(int OrderTime, int time) {

    }
    //TODO : upddate for delivery man
    public void showOrderHistory() {
        if (selectedRestaurant == -1) {
            System.out.println("No restaurant has been selected!");
        } else {
            for (int i : Restaurant.getRestaurant(selectedRestaurant).getOrders())
                System.out.println("ID: " + i + ", STATUS: " + Order.getOrder(i).getStatus() + ", DELIVERYMAN: " + Order.getOrder(i).getDeliveryman() + ", USER: " + Order.getOrder(i).getUser().getUsername() + ", PRICE: " + Order.getOrder(i).getPrice());
        }
    }
    /// modirate sefaresh :::::::
    public void selectRestaurant(int restaurantID) {
        if (loggedInAdmin == -1 && loggedInUser == -1) {
            System.out.println("No admin has logged in!");
        } else {
            selectedRestaurant = restaurantID;
            System.out.println("Restaurant successfully selected.");
        }
    }
    public void searchRestaurantName(String name) {
        List<Account> tmp = new ArrayList<>(accounts);
        if(loggedInAdmin != -1)
        {
            boolean check = false;
            Admin admin = (Admin) tmp.get(loggedInAdmin);
            for(Integer restaurantId : admin.getRestaurants())
            {
                if(Restaurant.getRestaurant(restaurantId).getName().substring(0,name.length()-1).equals(name))
                {
                    System.out.println("Restaurant name : "+ Restaurant.getRestaurant(restaurantId).getName()+ " Restaurant ID: "+ restaurantId);
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
                        if(Restaurant.getRestaurant(restaurantId).getName().substring(0,name.length()-1).equals(name))
                        {
                            System.out.println("Restaurant name : "+ Restaurant.getRestaurant(restaurantId).getName()+ " Restaurant ID: "+ restaurantId);
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
            for(Integer foodId : Restaurant.getRestaurant(selectedRestaurant).getMenu())
            {
                if(name.equals(Food.getFood(foodId).getName().substring(0,name.length())));
                {
                    System.out.println("food name: "+Food.getFood(foodId).getName()+" food price: "+ Food.getFood(foodId).getPrice());
                }
            }
        }
    }
    public void addComment(String content) {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else if (Food.getFood(selectedFood).getRestaurant().getAdmin() == loggedInAdmin) {
            System.out.println("You cannot write comments on your own products!");
        } else {
            Comment c = new Comment();
            c.setCommenter(loggedInAccount);
            c.setContent(content);
            //TODO: set ID
            System.out.println("Comment added successfully.");
        }
    }
    public void editComment(int commentID, String content) {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else if (Comment.getComment(commentID).getCommenter() != loggedInAccount) {
            System.out.println("You can only edit your own comments!");
        } else {
            Comment.getComment(commentID).setContent(content);
            System.out.println("Comment edited successfully.");
        }
    }
    public void submitRating(int rating) {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else if (Food.getFood(selectedFood).getRaters().contains(loggedInAccount)) {
            System.out.println("You have already added a rating for this food!");
        } else {
            Food.getFood(selectedFood).addRating(loggedInAccount, rating);
            System.out.println("Rating submitted successfully.");
        }
    }
    public void editRating(int rating) {
        if (selectedFood == -1) {
            System.out.println("No food has been selected!");
        } else if (!Food.getFood(selectedFood).getRaters().contains(loggedInAccount)) {
            System.out.println("You have not submitted a rating for this food!");
        } else {
            Food.getFood(selectedFood).addRating(loggedInAccount, rating);
            System.out.println("Rating submitted successfully.");
        }
    }
    public void addToCart(int count) {
        if(loggedInUser == -1)
        {
            System.out.println("No one has logged in!!!");
        }
        else if(selectedFood == -1)
        {
            System.out.println("you have not selected any food!!!");
        }
        else
        {
            for(int i=0;i<count;i++)
                User.getUser(loggedInUser).getCart().addItem(Food.getFood(selectedFood));
            System.out.println("added successfully.");
        }
    }
    public void selectOrder(int id) {
        if(loggedInUser == -1)
        {
            System.out.println("No one has logged in!!!");
        }
        else if(User.getUser(loggedInUser).getOrders().size() == 0)
        {
            System.out.println("There is no order!!!");
        }
        else if(!User.getUser(loggedInUser).getOrders().contains(id))
        {
            System.out.println("there is no order with this given id!");
        }
        else
        {
            System.out.println("order id: "+id);
            for(Map.Entry<Integer, Order.FoodData> item : Order.getOrder(id).getItems().entrySet())//inja frz krdm id order hm yektast
            {
                System.out.println("food name: "+Food.getFood(item.getKey()).getName()+ " count: "+ item.getValue().getCount()
                        +" discount: %"+ item.getValue().getDiscount()+" total-price: "+item.getValue().getTotalPrice());
            }
        }
    }
    public void showCart() {
        if(loggedInUser == -1)
        {
            System.out.println("No one has logged in!!!");
        }
        else if(User.getUser(loggedInUser).getCart().getItems().size() == 0)
        {
            System.out.println("There is no order!!!");
        }
        else
        {
            System.out.println("your cart is: ");
            for(Map.Entry<Integer, Order.FoodData> item : User.getUser(loggedInUser).getCart().getItems().entrySet())
            {
                System.out.println("food name: "+Food.getFood(item.getKey()).getName()+ " count: "+ item.getValue().getCount()
                        +" discount: %"+ item.getValue().getDiscount()+" total-price: "+item.getValue().getTotalPrice());
            }
        }
    }
    public void confirmOrder() {
        if(loggedInUser == -1)
        {
            System.out.println("No one has logged in!!!");
        }
        else if(User.getUser(loggedInUser).getCart() == null)
        {
            System.out.println("you have not chosen anything!!!");
        }
        else
        {
            System.out.println("Done!");
            User.getUser(loggedInUser).getCart().setStatus("CONFIRMED");//agr ye enum bznim bra in kara be nzrm bhtare
        }
    }
    public void showEstimatedDeliveryTime() {

    }
    public void chargeAccount(int value) {
        if(loggedInUser == -1)
        {
            System.out.println("No one has logged in!!!");
        }
        else
        {
            User.getUser(loggedInUser).addBalance(value);
            System.out.println("your balance charged successfully.");
        }
    }
    public void displayAccountBalance() {
        if(loggedInUser == -1)
        {
            System.out.println("No one has logged in!!!");
        }
        else
        {
            System.out.println("your balance is: "+ User.getUser(loggedInUser).getBalance());
        }
    }
    public void showPath() {

    }
    public void suggestRestuarant()
    {
        if(loggedInUser == -1)
        {
            System.out.println("No one has logged in!!!");
        }
        else
        {
            ArrayList<Integer> tmp  =new ArrayList<>();
            for(Account account : accounts)
            {
                if(account.getClass() == Admin.class)
                {
                    for(Integer restaurantId : ((Admin) account).getRestaurants())
                    {
                        tmp.add(restaurantId);
                    }
                }
            }
            for(int i = 0;i < tmp.size()-1; i++)
            {
                for(int j = i+1;j < tmp.size(); j++)
                {
                    if(Restaurant.getRestaurant(tmp.get(i)).getAverageRating() > Restaurant.getRestaurant(tmp.get(j)).getAverageRating())
                    {
                        Collections.swap(tmp,i,j);
                    }
                }
            }
            for(int i=0;i<5;i++)
            {
                Restaurant.getRestaurant(tmp.get(tmp.size()-1-i)).showRestaurant();
            }
        }

    }
    public void suggestFood() {
        if(loggedInUser == -1)
        {
            System.out.println("No one has logged in!!!");
        }
        else if(selectedRestaurant == -1)
        {
            System.out.println("No restaurant has been selected!!!");
        }
        else
        {
            ArrayList<Integer> tmp = new ArrayList<>();
            for(Integer foodId : Restaurant.getRestaurant(selectedRestaurant).getMenu())
            {
                tmp.add(foodId);
            }
            for(int i=0;i<tmp.size();i++)
            {
                for(int j=i+1;j<tmp.size();j++)
                {
                    if(Food.getFood(tmp.get(i)).getAverageRating() > Food.getFood(tmp.get(j)).getAverageRating())
                    {
                        Collections.swap(tmp,i,j);
                    }
                }
            }
            for(int i=0;i<5;i++)
            {
                Food.getFood(tmp.get(i)).showFood();
            }
        }
    }
    public void setLocation(int id) {

    }
    public void getPathToRestaurant() {

    }
    public void getPathToUser() {

    }
    public void withdraw() {

    }
    // deliveryh man ordern entekhab mikone
    public void acceptOrder(int id) {

    }
    public void showAvailableOrders() {
        if(loggedInDeliveryman == -1)
        {
            System.out.println("Deliveryman has not logged in!!!");
        }
        else
        {
            ArrayList<Integer> tmp  =new ArrayList<>();
            for(Account account : accounts)
            {
                if(account.getClass() == Admin.class)
                {
                    for(Integer restaurantId : ((Admin) account).getRestaurants())
                    {
                        tmp.add(restaurantId);
                    }
                }
            }
            //TODO Matin khbrt kojaeiiiiii

        }
    }
    public void forgetPasswordPressed(String username)
    {
        for(Account account : accounts)
        {
            if(account.getUsername().equals(username))
            {
                System.out.println(account.getRecoveryQuestion());
            }
        }
    }
    public void checkRecoveruQuestionAnswer(String username,String recoveryQuestionAnswer)
    {
        for(Account account : accounts)
        {
            if(account.getUsername().equals(username))
            {
                if(account.getRecoveryQuestionAnswer().equals(recoveryQuestionAnswer))
                    System.out.println(account.getPassword());
                else
                    System.out.println("Your answer is wrong!!!");
            }
        }
    }
}
