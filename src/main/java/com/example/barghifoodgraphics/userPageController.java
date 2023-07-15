package com.example.barghifoodgraphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;

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
    public void setBalance() throws IOException {
        if (!balanceTextField.getText().matches("^([0-9]+)$")) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Invalid number");
            a.setContentText("Please enter a valid, non-negative, integer number.");
            a.show();
        } else
            ((User)MainApplication.core.accounts.get(MainApplication.core.loggedInUser)).setBalance(Integer.parseInt(balanceTextField.getText()));
        initialize();
    }
    public void addLocation() throws IOException {
        if (!locationTextField.getText().matches("^([0-9]+)$")) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Invalid input");
            a.setContentText("Please enter a valid location id.");
            a.show();
        } else {
            int input = Integer.parseInt(locationTextField.getText());
            if (1 <= input && input <= 1000) {
                ((User)MainApplication.core.accounts.get(MainApplication.core.loggedInUser)).addLocation(input);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Invalid ID");
                a.setContentText("Please enter a valid location id.");
                a.show();
            }
        }
        initialize();
    }
    public void logout() {
        MainApplication.core.logout();
        MainApplication.stage.setScene(MainApplication.login);
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
    public void changeToOrderDetails(int orderID) throws IOException {
        MainApplication.fxmlLoaderOrderDetails = new FXMLLoader(MainApplication.class.getResource("orderDetails.fxml"));
        MainApplication.orderDetails = new Scene(MainApplication.fxmlLoaderOrderDetails.load(), 400, 600);
        ((orderDetailsController)MainApplication.fxmlLoaderOrderDetails.getController()).orderID = orderID;
        ((orderDetailsController)MainApplication.fxmlLoaderOrderDetails.getController()).initialize();
        MainApplication.stage.setScene(MainApplication.orderDetails);
    }
    public void refreshSearch() {
        ArrayList<String> results = new ArrayList<>(MainApplication.core.searchRestaurantName(searchBox.getText()));
        searchResultsRestaurant.getItems().clear();
        searchResultsRestaurant.getItems().addAll(results);
        results.clear();
        results.addAll(MainApplication.core.searchFoodName(searchBox.getText()));
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
    public void initialize() throws IOException {
        MainApplication.fxmlLoaderCart = new FXMLLoader(MainApplication.class.getResource("cart.fxml"));
        MainApplication.cart = new Scene(MainApplication.fxmlLoaderCart.load(), 600, 600);
        idLabel.setText(String.valueOf(MainApplication.core.loggedInUser));
        usernameLabel.setText(MainApplication.core.accounts.get(MainApplication.core.loggedInUser).getUsername());
        passwordLabel.setText(MainApplication.core.accounts.get(MainApplication.core.loggedInUser).getPassword());
        recoveryQuestionLabel.setText(MainApplication.core.accounts.get(MainApplication.core.loggedInUser).getRecoveryQuestion());
        recoveryAnswerLabel.setText(MainApplication.core.accounts.get(MainApplication.core.loggedInUser).getRecoveryQuestionAnswer());
        balanceLabel.setText(String.valueOf((int)((User)MainApplication.core.accounts.get(MainApplication.core.loggedInUser)).getBalance()));
        locationsList.getItems().clear();
        locationBox.getItems().clear();
        for (int i : ((User)MainApplication.core.accounts.get(MainApplication.core.loggedInUser)).getLocations())
            locationBox.getItems().add(String.valueOf(i));
        locationBox.getSelectionModel().selectedItemProperty().addListener((observableValue, o, selection) ->
                ((User)MainApplication.core.accounts.get(MainApplication.core.loggedInUser)).setSelectedLocation(Integer.parseInt(selection)));
        locationsList.getItems().addAll(locationBox.getItems());
        for (int i : ((User)MainApplication.core.accounts.get(MainApplication.core.loggedInUser)).getOrders()) {
            VBox newOrder = new VBox();
            newOrder.setStyle("-fx-background-color: rgb(100,100,100); -fx-background-radius: 10;");
            newOrder.setAlignment(Pos.TOP_CENTER);
            newOrder.setPadding(new Insets(20, 20, 20, 20));
            newOrder.setPrefSize(10000, Region.USE_COMPUTED_SIZE);
            VBox.setMargin(newOrder, new Insets((i == 0? 0 : 20), 0, 0, 0));
            Label newLabel = new Label("Order id: " + i);
            newLabel.setTextFill(Color.WHITE);
            newOrder.getChildren().add(newLabel);
            newLabel = new Label("Origin: " + MainApplication.core.orders.get(i).getRestaurant());
            newLabel.setTextFill(Color.WHITE);
            newOrder.getChildren().add(newLabel);
            newLabel = new Label("Delivery cost: " + MainApplication.core.orders.get(i).getDeliveryPrice());
            newLabel.setTextFill(Color.WHITE);
            newOrder.getChildren().add(newLabel);
            newLabel = new Label("Deliveryman: " + MainApplication.core.accounts.get(MainApplication.core.orders.get(i).getDeliveryman()).getUsername());
            newLabel.setTextFill(Color.WHITE);
            newOrder.getChildren().add(newLabel);
            newLabel = new Label("Status: " + MainApplication.core.orders.get(i).getStatus());
            newLabel.setTextFill(Color.WHITE);
            newOrder.getChildren().add(newLabel);
            Button viewDetailsButton = new Button("View Details");
            VBox.setMargin(viewDetailsButton, new Insets(20, 0, 0, 0));
            viewDetailsButton.setFocusTraversable(false);
            viewDetailsButton.setStyle("-fx-cursor: hand;");
            viewDetailsButton.setOnAction(actionEvent -> {
                try {
                    changeToOrderDetails(i);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            newOrder.getChildren().add(viewDetailsButton);
            ordersBox.getChildren().add(newOrder);
        }
    }
}