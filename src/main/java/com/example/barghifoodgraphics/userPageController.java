package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class userPageController {
    @FXML
    private TextField searchBox, balanceTextField, locationTextField;
    @FXML
    private ListView<String> searchResultsRestaurant, searchResultsFood, locationsList;
    @FXML
    private VBox searchVbox, ordersBox;
    @FXML
    private ComboBox<String> locationBox;
    @FXML
    private Label idLabel, usernameLabel, passwordLabel, recoveryQuestionLabel, recoveryAnswerLabel, balanceLabel;
    public void setBalance() {
        if (!balanceTextField.getText().matches("^([0-9]+)$")) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Invalid number");
            a.setContentText("Please enter a valid, non-negative, integer number.");
            a.show();
        } else {
            //((User)Main.core.accounts.get(Main.core.loggedInUser)).setBalance(Integer.parseInt(balanceTextField.getText()));
        }
    }
    public void addLocation() {
        if (!locationTextField.getText().matches("^([0-9]+)$")) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Invalid input");
            a.setContentText("Please enter a valid location id.");
            a.show();
        } else {
            int input = Integer.parseInt(locationTextField.getText());
            if (false/*TODO: check existence*/) {
                //((User)Main.core.accounts.get(Main.core.loggedInUser)).addLocation(input);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Invalid ID");
                a.setContentText("Please enter a valid location id.");
                a.show();
            }
        }
    }
    public void logout() {
        /*Main.core.logout();
        MainApplication.stage.setScene(MainApplication.login);*/
    }
    public void changeToRestaurant() {
        MainApplication.stage.setScene(MainApplication.restaurant);
    }
    public void changeToSupermarket() {
        MainApplication.stage.setScene(MainApplication.supermarket);
    }
    public void changeToCart() {
        MainApplication.stage.setScene(MainApplication.cart);
    }
    public void refreshSearch() {
        ArrayList<String> results = new ArrayList<>();
        //results.addAll(Arrays.asList(Main.core.searchRestaurantName(searchBox.getText()).split("\n")));
        results.add("Restaurant1");
        results.add("Restaurant2");
        results.add("Restaurant3");
        results.add("Restaurant4");
        results.add("Restaurant5");
        results.add("Restaurant6");
        results.add("Restaurant7");
        results.add("Restaurant8");
        results.add("Restaurant9");
        results.add("Restaurant10");
        searchResultsRestaurant.getItems().clear();
        searchResultsRestaurant.getItems().addAll(results);
        results.clear();
        //results.addAll(Arrays.asList(Main.core.searchFoodName(searchBox.getText()).split("\n")));
        results.add("Food1");
        results.add("Food2");
        results.add("Food3");
        results.add("Food4");
        results.add("Food5");
        results.add("Food6");
        results.add("Food7");
        results.add("Food8");
        results.add("Food9");
        results.add("Food10");
        searchResultsFood.getItems().clear();
        searchResultsFood.getItems().addAll(results);
        searchVbox.setVisible(!searchBox.getText().isEmpty());
    }
    public void comingSoon() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("Coming soon!");
        a.setContentText("This section will soon be available for use.");
        a.show();
    }
    public void initialize() {
        /*idLabel.setText(String.valueOf(Main.core.loggedInUser));
        usernameLabel.setText(Main.core.accounts.get(Main.core.loggedInUser).getUsername());
        passwordLabel.setText(Main.core.accounts.get(Main.core.loggedInUser).getPassword());
        recoveryQuestionLabel.setText(Main.core.accounts.get(Main.core.loggedInUser).getRecoveryQuestion());
        recoveryAnswerLabel.setText(Main.core.accounts.get(Main.core.loggedInUser).getRecoveryQuestionAnswer());
        balanceLabel.setText(String.valueOf(((User)Main.core.accounts.get(Main.core.loggedInUser)).getBalance()));*/
        /*for (int i : ((User)Main.core.accounts.get(Main.core.loggedInUser)).getLocations())
            locationBox.getItems().add(String.valueOf(i));*/
        locationBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15");
        locationBox.getSelectionModel().selectedItemProperty().addListener((observableValue, o, selection) -> {
            //((User)Main.core.accounts.get(Main.core.loggedInUser)).setSelectedLocation(Integer.parseInt(selection));
        });
        locationsList.getItems().addAll(locationBox.getItems());
        //TODO: insert data
        //for (int i : ((User)Main.core.accounts.get(Main.core.loggedInUser)).getOrders()) {
        for (int i = 0; i < 10; i++) {
            VBox newOrder = new VBox();
            newOrder.setStyle("-fx-background-color: rgb(100,100,100); -fx-background-radius: 10;");
            newOrder.setAlignment(Pos.TOP_CENTER);
            newOrder.setPadding(new Insets(20, 20, 20, 20));
            newOrder.setPrefSize(10000, Region.USE_COMPUTED_SIZE);
            VBox.setMargin(newOrder, new Insets((i == 0? 0 : 20), 0, 0, 0));
            Label newLabel = new Label("Order id: " + i);
            newLabel.setTextFill(Color.WHITE);
            newOrder.getChildren().add(newLabel);
            newLabel = new Label("Origin: "/* + Main.core.orders.get(i).getRestaurant()*/);
            newLabel.setTextFill(Color.WHITE);
            newOrder.getChildren().add(newLabel);
            newLabel = new Label("Cost: "/*TODO: get cost of order*/);
            newLabel.setTextFill(Color.WHITE);
            newOrder.getChildren().add(newLabel);
            newLabel = new Label("Deliveryman: "/* + Main.core.accounts.get(Main.core.orders.get(i).getDeliveryman()).getUsername()*/);
            newLabel.setTextFill(Color.WHITE);
            newOrder.getChildren().add(newLabel);
            newLabel = new Label("Status: "/* + Main.core.orders.get(i).getStatus()*/);
            newLabel.setTextFill(Color.WHITE);
            newOrder.getChildren().add(newLabel);
            Button viewDetailsButton = new Button("View Details");
            VBox.setMargin(viewDetailsButton, new Insets(20, 0, 0, 0));
            viewDetailsButton.setFocusTraversable(false);
            viewDetailsButton.setStyle("-fx-cursor: hand;");
            newOrder.getChildren().add(viewDetailsButton);
            ordersBox.getChildren().add(newOrder);
        }
    }
}