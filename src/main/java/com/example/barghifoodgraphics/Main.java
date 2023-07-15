package com.example.barghifoodgraphics;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main {
    static Core core;
    public static Matcher getMatcher(String command, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner cin = new Scanner(System.in);
        core = new Core();
        String command;
        Matcher matcher;
        while (true) {
            command = cin.nextLine();
            if ((matcher = getMatcher(command, "^-end *")) != null) {
                break;
            }
            if ((matcher = getMatcher(command, "^-REGISTER ADMIN (?<name>[a-zA-Z]+) (?<password>\\S+) (?<recoveryQuestion>\\S+) (?<recoveryQuestionAnswer>\\S+) *")) != null) {
                String name = matcher.group("name");
                String password = matcher.group("password");
                String recoveryQuestion = matcher.group("recoveryQuestion");
                String recoveryQuestionAnswer = matcher.group("recoveryQuestionAnswer");
                core.addAdmin(name, password, recoveryQuestion, recoveryQuestionAnswer);
            }
            else if ((matcher = getMatcher(command, "^-LOGIN (?<name>[a-zA-Z]+) (?<password>\\S+) *")) != null) {
                String name = matcher.group("name");
                String password = matcher.group("password");
                core.login(name, password);
            }
            else if ((matcher = getMatcher(command, "^-REGISTER USER (?<name>[a-zA-Z]+) (?<password>\\S+) (?<recoveryQuestion>\\S+) (?<recoveryQuestionAnswer>\\S+) *")) != null) {
                String name = matcher.group("name");
                String password = matcher.group("password");
                String recoveryQuestion = matcher.group("recoveryQuestion");
                String recoveryQuestionAnswer = matcher.group("recoveryQuestionAnswer");
                core.addUser(name, password, recoveryQuestion, recoveryQuestionAnswer);
            }
            else if ((matcher = getMatcher(command, "^-REGISTER DELIVERY (?<name>[a-zA-Z]+) (?<password>\\S+) (?<recoveryQuestion>\\S+) (?<recoveryQuestionAnswer>\\S+) *")) != null) {
                String name = matcher.group("name");
                String password = matcher.group("password");
                String recoveryQuestion = matcher.group("recoveryQuestion");
                String recoveryQuestionAnswer = matcher.group("recoveryQuestionAnswer");
                core.addDeliveryman(name, password, recoveryQuestion, recoveryQuestionAnswer);
            }
            else if ((matcher = getMatcher(command, "^-REGISTER DELIVERY (?<name>[a-zA-Z]+) (?<password>\\S+) *")) != null) {
                String name = matcher.group("name");
                String password = matcher.group("password");
                core.login(name, password);
            }
            else if ((matcher = getMatcher(command, "^-SHOW ORDER HISTORY *")) != null) {
                core.showOrderHistory();
            }
            else if ((matcher = getMatcher(command, "^-SEARCH FOOD NAME (?<name>[a-zA-Z]+) *")) != null) {
                String name = matcher.group("name");
                core.searchFoodName(core.selectedRestaurant, name);
            }
            else if ((matcher = getMatcher(command, "^-UNSELECT FOOD *")) != null) {
                core.unselectFood();
            }
            else if ((matcher = getMatcher(command, "^-SEARCH RESTAURANT NAME (?<name>[a-zA-Z]+) *")) != null) {
                String name = matcher.group("name");
                core.searchRestaurantName(name);
            }
            else if ((matcher = getMatcher(command, "^-UNSELECT RESTAURANT *")) != null) {
                core.unselectRestaurant();
            }
            else if ((matcher = getMatcher(command, "^-DISPLAY ACTIVE ORDERS *")) != null) {
                core.displayActiveOrders();
            }
            else if ((matcher = getMatcher(command, "^-SELECT RESTAURANT (?<id>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                core.selectRestaurant(id);
            }
            else if ((matcher = getMatcher(command, "^-SELECT LOCATION (?<id>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                core.selectLocation(id);
            }
            else if ((matcher = getMatcher(command, "^-SHOW SELECTED LOCATION *")) != null) {
                core.showSelectedLocation();
            }
            else if ((matcher = getMatcher(command, "^-SELECT FOOD (?<id>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                core.selectFood(id);
            }
            else if ((matcher = getMatcher(command, "^-DISCOUNT FOOD (?<id>[0-9]+) (?<percentage>[0-9]+) (?<time>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                int percentage = Integer.parseInt(matcher.group("percentage"));
                int time = Integer.parseInt(matcher.group("time"));
                core.discountFood(id, percentage, time);
            }
            else if ((matcher = getMatcher(command, "^-SELECT ORDER (?<id>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                core.selectOrder(id);
            }
            else if ((matcher = getMatcher(command, "^-UNSELECT ORDER *")) != null) {
                core.unselectOrder();
            }
            else if ((matcher = getMatcher(command, "^-EDIT FOOD TYPE (?<toAdd>[a-zA-Z,]+) *")) != null) {
                System.out.println("are you sure about that ? (type yes/no)");
                boolean fs = false;
                command = cin.nextLine();
                if ((matcher = getMatcher(command, "^yes *")) != null)
                    fs = true;
                String toAdd = matcher.group("toAdd");
                String toRemove = matcher.group("toRemove");
                core.editFoodType(toAdd, fs);
            }
            else if ((matcher = getMatcher(command, "^-ADD RESTAURANT (?<name>[a-zA-Z]+) (?<type>[a-zA-Z,]+) (?<location>[0-9]+) *")) != null) {
                int location = Integer.parseInt(matcher.group("location"));
                String name = matcher.group("name");
                String type = matcher.group("type");
                core.addRestaurant(name, type, location);
            }
            else if ((matcher = getMatcher(command, "^-REMOVE FOOD TYPE (?<toRemove>[a-zA-Z,]+) *")) != null) {
                System.out.println("are you sure about that ? (type yes/no)");
                boolean fs = false;
                command = cin.nextLine();
                if ((matcher = getMatcher(command, "^yes *")) != null)
                    fs = true;
                String toRemove = matcher.group("toRemove");
                core.removeFoodType(toRemove, fs);
            }
            else if ((matcher = getMatcher(command, "^-ADD FOOD TYPE (?<toAdd>[a-zA-Z,]+) *")) != null) {
                System.out.println("are you sure about that ? (type yes/no)");
                boolean fs = false;
                command = cin.nextLine();
                if ((matcher = getMatcher(command, "^yes *")) != null)
                    fs = true;
                String toAdd = matcher.group("toAdd");
                core.addFoodType(toAdd, fs);
            }
            else if ((matcher = getMatcher(command, "^-EDIT RATING (?<newRating>[0-9]+) *")) != null) {
                int newRating = Integer.parseInt(matcher.group("newRating"));
                core.editRating(newRating);
            }
            else if ((matcher = getMatcher(command, "^-LOGOUT *")) != null) {
                core.logout();
            }
            else if ((matcher = getMatcher(command, "^-ADD COMMENT (?<content>[a-zA-Z]+) *")) != null) {
                String content = matcher.group("content");
                core.addComment(content);
            }
            else if ((matcher = getMatcher(command, "^-ADD COMMENT RESTAURANT (?<content>[a-zA-Z]+) *")) != null) {
                String content = matcher.group("content");
                core.addCommentRestaurant(content);
            }
            else if ((matcher = getMatcher(command, "^-EDIT LOCATION (?<id>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                core.editLocation(id);
            }
            else if ((matcher = getMatcher(command, "^-SHOW PATH *")) != null) {
                core.showPath();
            }
            else if ((matcher = getMatcher(command, "^-SHOW LOCATION *")) != null) {
                core.showLocation();
            }
            else if ((matcher = getMatcher(command, "^-EDIT COMMENT (?<id>[0-9]+) (?<content>[a-zA-Z]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                String content = matcher.group("content");
                core.editComment(id, content);
            }
            else if ((matcher = getMatcher(command, "^-SHOW FOOD TYPE *")) != null) {
                core.showFoodType();
            }
            else if ((matcher = getMatcher(command, "^-SHOW MENU *")) != null) {
                core.showMenu();
            }
            else if ((matcher = getMatcher(command, "^-ACTIVE FOOD (?<id>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                core.activateFood(id);
            }
            else if ((matcher = getMatcher(command, "^-EDIT ORDER STATUS(?<status>[a-zA-Z]+) *")) != null) {
                String status = matcher.group("status");
                core.editOrderStatus(status);
            }
            else if ((matcher = getMatcher(command, "^-ADD FOOD (?<name>[a-zA-Z]+) (?<price>[0-9]+) *")) != null) {
                String name = matcher.group("name");
                int price = Integer.parseInt(matcher.group("price"));
                core.addFood(name, price);
            }
            else if ((matcher = getMatcher(command, "^-SHOW ESTIMATED DELIVERY TIME *")) != null) {
                core.showEstimatedDeliveryTime();
            }
            else if ((matcher = getMatcher(command, "^-ADD TO CART (?<count>[0-9]+) *")) != null) {
                int count = Integer.parseInt(matcher.group("count"));
                core.addToCart(count);
            }
            else if ((matcher = getMatcher(command, "^-ADD TO CART *")) != null) {
                int count = 1;
                core.addToCart(count);
            }
            else if ((matcher = getMatcher(command, "^-REMOVE FROM CART (?<count>[0-9]+) *")) != null) {
                int count = Integer.parseInt(matcher.group("count"));
                core.removeFromCart(count);
            }
            else if ((matcher = getMatcher(command, "^-REMOVE FROM CART *")) != null) {
                int count = 1;
                core.removeFromCart(count);
            }
            else if ((matcher = getMatcher(command, "^-SUBMIT RATING (?<rating>[0-9]+) *")) != null) {
                int rating = Integer.parseInt(matcher.group("rating"));
                core.submitRating(rating);
            }
            else if ((matcher = getMatcher(command, "^-DISPLAY ACCOUNT BALANCE *")) != null) {
                core.displayAccountBalance();
            }
            else if ((matcher = getMatcher(command, "^-EDIT FOOD NAME (?<id>[0-9]+) (?<name>[a-zA-Z]+) *")) != null) {
                String name = matcher.group("name");
                int id = Integer.parseInt(matcher.group("id"));
                core.editFoodName(id, name);
            }
            else if ((matcher = getMatcher(command, "^-DEACTIVE FOOD (?<id>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                core.deactivateFood(id);
            }
            else if ((matcher = getMatcher(command, "^-CONFIRM ORDER *")) != null) {
                core.confirmOrder();
            }
            else if ((matcher = getMatcher(command, "^-EDIT FOOD PRICE (?<id>[0-9]+) (?<price>[0-9]+) *")) != null) {
                int price = Integer.parseInt(matcher.group("price"));
                int id = Integer.parseInt(matcher.group("id"));
                core.editFoodPrice(id, price);
            }
            else if ((matcher = getMatcher(command, "^-ADD RESPONSE (?<id>[0-9]+) (?<content>[a-zA-Z]+) *")) != null) {
                String content = matcher.group("content");
                int id = Integer.parseInt(matcher.group("id"));
                core.addResponse(id, content);
            }
            else if ((matcher = getMatcher(command, "^-FORGOT PASSWORD (?<name>[a-zA-Z]+) *")) != null) {
                String name = matcher.group("content");
                core.forgotPassword(name);
                if (core.tmpPassForgot != -1) {
                    command = cin.nextLine();
                    String[] koft = command.split(" ");
                    core.resetPassword(koft[0], koft[1]);
                }
            }
            else if ((matcher = getMatcher(command, "^-EDIT RESPONSE (?<id>[0-9]+) (?<content>[a-zA-Z]+) *")) != null) {
                String content = matcher.group("content");
                int id = Integer.parseInt(matcher.group("id"));
                core.editResponse(id, content);
            }
            else if ((matcher = getMatcher(command, "^-SET FOOD FOOD TYPE (?<type>[a-zA-Z]+) *")) != null) {
                String type = matcher.group("type");
                core.setFoodFoodType(type);
            }
            else if ((matcher = getMatcher(command, "^-SHOW CART *")) != null) {
                core.showCart();
            }
            else if ((matcher = getMatcher(command, "^-CHARGE ACCOUNT (?<value>[0-9]+) *")) != null) {
                int value = Integer.parseInt(matcher.group("value"));
                core.chargeAccount(value);
            }
            else if ((matcher = getMatcher(command, "^-DISPLAY COMMENTS *")) != null) {
                core.displayComments();
            }
            else if ((matcher = getMatcher(command, "^-DISPLAY RATING *")) != null) {
                core.displayRating();
            }
            else if ((matcher = getMatcher(command, "^-SET LOCATION (?<id>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                core.setLocation(id);
            }
            else if ((matcher = getMatcher(command, "^-ADD LOCATION (?<id>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                core.addLocation(id);
            }
            else if ((matcher = getMatcher(command, "^-REMOVE LOCATION (?<id>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                core.removeLocation(id);
            }
            else if ((matcher = getMatcher(command, "^-SHOW AVAILABLE ORDERS *")) != null) {
                core.showAvailableOrders();
            }
            else if ((matcher = getMatcher(command, "^-WITHDRAW *")) != null) {
                core.withdraw();
            }
            else if ((matcher = getMatcher(command, "^-ACCEPT ORDER (?<id>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                core.acceptOrder(id);
            }
            else if ((matcher = getMatcher(command, "^-SHOW FOOD FOOD TYPE (?<id>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                core.showFoodFoodType(id);
            }
            else if ((matcher = getMatcher(command, "^-REMOVE RESPONSE (?<id>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                core.removeResponse(id);
            }
            else if ((matcher = getMatcher(command, "^-REMOVE COMMENT (?<id>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                core.removeComment(id);
            }
            else if ((matcher = getMatcher(command, "^-REMOVE FOOD (?<id>[0-9]+) *")) != null) {
                int id = Integer.parseInt(matcher.group("id"));
                core.removeFood(id);
            }
            else if ((matcher = getMatcher(command, "^-GET FOOD *")) != null) {
                core.getFood();
            }
            else if ((matcher = getMatcher(command, "^-GIVE FOOD *")) != null) {
                core.giveFood();
            }
            else if ((matcher = getMatcher(command, "^-GET PATH TO USER *")) != null) {
                core.getPathToUser();
            }
            else if ((matcher = getMatcher(command, "^-GET PATH TO RESTAURANT *")) != null) {
                core.getPathToRestaurant();
            }
            else if ((matcher = getMatcher(command, "^-SUGGEST ORDER *")) != null) {
                core.suggestFood();
            }
            else if ((matcher = getMatcher(command, "^-NEAREST RESTAURANT *")) != null) {
                core.nearRestaurant();
            }
            else if ((matcher = getMatcher(command, "^-SHOW RESTAURANTS *")) != null) {
                core.showRestaurants();
            }
            else if ((matcher = getMatcher(command, "^-SHOW DELIVERY PRICE *")) != null) {
                core.showDeliveryPrice(core.selectedRestaurant);
            }
            else if ((matcher = getMatcher(command, "^-GET TRAFFIC (?<idu>[0-9]+) (?<idv>[0-9]+) *")) != null) {
                int idu = Integer.parseInt(matcher.group("idu"));
                int idv = Integer.parseInt(matcher.group("idv"));
                core.getTraffic(idu, idv);
            }
            else {
                System.out.println("Wrong command format !");
            }
        }

    }
}