package com.example.barghifoodgraphics;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class deliverymanPageController {
    ArrayList<Integer> availableOrders;
    int currentDelivery, selectedRow;
    @FXML
    private TableColumn<List<StringProperty>, String> startingPoint, destinationPoint, estimatedDuration;
    @FXML
    private TableView<List<Object>> availableOrdersTable;
    @FXML
    private Label currentDeliveryLabel, startLabel, destinationLabel, estimationLabel;
    @FXML
    private Label idLabel, usernameLabel, passwordLabel, recoveryQuestionLabel, recoveryAnswerLabel, balanceLabel;
    @FXML
    private TextField balanceTextField;
    public void tableClicked() {
        if (availableOrdersTable.getSelectionModel().getSelectedItem() == null)
            selectedRow = -1;
        else
            selectedRow = availableOrdersTable.getSelectionModel().selectedIndexProperty().get();
        System.out.println(selectedRow);
    }
    public void accept() {
        if (selectedRow == -1) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No row selected");
            a.setContentText("You must select a row from the table.");
            a.show();
            return;
        } else if (currentDelivery != -1) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Currently delivering");
            a.setContentText("You must finish your current delivery first.");
            a.show();
            return;
        }
        //Main.core.acceptOrder(availableOrders.get(selectedRow));
        initialize();
    }
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
    public void withdraw() {
        //Main.core.withdraw();
    }
    public void logout() {
        /*Main.core.logout();
        MainApplication.stage.setScene(MainApplication.login);*/
    }
    public void initialize() {
        selectedRow = -1;
        /*idLabel.setText(String.valueOf(Main.core.loggedInDeliveryman));
        usernameLabel.setText(Main.core.accounts.get(Main.core.loggedInDeliveryman).getUsername());
        passwordLabel.setText(Main.core.accounts.get(Main.core.loggedInDeliveryman).getPassword());
        recoveryQuestionLabel.setText(Main.core.accounts.get(Main.core.loggedInDeliveryman).getRecoveryQuestion());
        recoveryAnswerLabel.setText(Main.core.accounts.get(Main.core.loggedInDeliveryman).getRecoveryQuestionAnswer());
        balanceLabel.setText(String.valueOf(((User)Main.core.accounts.get(Main.core.loggedInDeliveryman)).getBalance()));*/
        currentDelivery = -1/*((Deliveryman)Main.core.accounts.get(Main.core.loggedInDeliveryman)).getActiveOrder()*/;
        if (currentDelivery == -1) {
            currentDeliveryLabel.setText("No current delivery");
            startLabel.setText("Starting point: -");
            destinationLabel.setText("Destination point: -");
            estimationLabel.setText("Estimated time left: -");
        } else {
            currentDeliveryLabel.setText("Current delivery ID: " + currentDelivery);
            startLabel.setText("Starting point: "/* + Main.core.restaurants.get(Main.core.orders.get(currentDelivery).getRestaurant()).getLocation()*/);
            destinationLabel.setText("Destination point: "/* + Main.core.restaurants.get(Main.core.orders.get(currentDelivery).getUserLocation())*/);
            estimationLabel.setText("Estimated time left: "/* + Main.core.showEstimatedDeliveryTime()*/);
        }
        startingPoint.setCellValueFactory(data -> data.getValue().get(0));
        destinationPoint.setCellValueFactory(data -> data.getValue().get(1));
        estimatedDuration.setCellValueFactory(data -> data.getValue().get(2));
        ObservableList<List<Object>> data = FXCollections.observableArrayList();
        //TODO: get data
        availableOrders = /*Main.core.showAvailableOrders()*/null;
        int deliverymanLocation = 0/*((Deliveryman)Main.core.accounts.get(Main.core.loggedInDeliveryman)).getLocation()*/;
        //for (int i : availableOrders) {
        for (int i = 1; i <= 30; i++) {
            int userLocation = i/*Main.core.orders.get(i).getUserLocation()*/;
            int restaurantLocation = i/*Main.core.restaurants.get(Main.core.orders.get(i).getRestaurant()).getLocation()*/;
            int toRestaurantTime = i/*Main.core.map.getShortestPath(deliverymanLocation, restaurantLocation).getTime()*/;
            int toUserTime = i/*Main.core.map.getShortestPath(restaurantLocation, userLocation).getTime()*/;
            List<Object> row = new ArrayList<>();
            row.add(new SimpleIntegerProperty(restaurantLocation));
            row.add(new SimpleIntegerProperty(userLocation));
            row.add(new SimpleIntegerProperty(toRestaurantTime + toUserTime));
            data.add(row);
        }
        availableOrdersTable.setItems(data);
    }
}