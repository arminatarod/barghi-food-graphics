package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class userPageController {
    ArrayList<String> locations = new ArrayList<>();
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
            //TODO: update balance
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
                //TODO: add location
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Invalid ID");
                a.setContentText("Please enter a valid location id.");
                a.show();
            }
        }
    }
    public void logout() {
        //TODO: logout
    }
    public void deleteAccount() {
        //TODO: delete account
    }
    public void changeToRestaurant() {
        //MainApplication.stage.setScene(MainApplication.restaurant);
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
        //results.addAll(Arrays.asList(Main.core.searchFoodName().split("\n")));
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
    public void initialize() {
        //locationBox.getItems().addAll(User.getUser(Main.core.loggedInUser).getLocations());
        for (int i = 1; i <= 20; i++)
            locations.add(String.valueOf(i));
        locationBox.getItems().addAll(locations);
        locationBox.getSelectionModel().selectedItemProperty().addListener((observableValue, o, selection) -> {
            //User.getUser(Main.core.loggedInUser).setSelectedLocation(Integer.parseInt(selection.toString()));
            System.out.println(Integer.parseInt(selection));
        });
        locationsList.getItems().addAll(locations);
        //TODO: insert data
        for (int i = 0; i < 10; i++) {
            VBox newOrder = new VBox();
            newOrder.setStyle("-fx-background-color: rgb(100,100,100); -fx-background-radius: 10;");
            newOrder.setAlignment(Pos.TOP_CENTER);
            newOrder.setPadding(new Insets(20, 20, 20, 20));
            newOrder.setPrefSize(10000, Region.USE_COMPUTED_SIZE);
            VBox.setMargin(newOrder, new Insets((i == 0? 0 : 20), 0, 0, 0));
            Label newLabel = new Label("Order id: 1234");
            newLabel.setTextFill(Color.WHITE);
            newOrder.getChildren().add(newLabel);
            newLabel = new Label("Origin: Kassab");
            newLabel.setTextFill(Color.WHITE);
            newOrder.getChildren().add(newLabel);
            newLabel = new Label("Cost: 1234");
            newLabel.setTextFill(Color.WHITE);
            newOrder.getChildren().add(newLabel);
            newLabel = new Label("Deliveryman: Mojtaba");
            newLabel.setTextFill(Color.WHITE);
            newOrder.getChildren().add(newLabel);
            newLabel = new Label("Status: Delivered");
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