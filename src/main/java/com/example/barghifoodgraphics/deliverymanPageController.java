package com.example.barghifoodgraphics;

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
        MainApplication.core.acceptOrder(availableOrders.get(selectedRow));
        initialize();
    }
    public void setBalance() {
        if (!balanceTextField.getText().matches("^([0-9]+)$")) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Invalid number");
            a.setContentText("Please enter a valid, non-negative, integer number.");
            a.show();
        } else
            ((Deliveryman)MainApplication.core.accounts.get(MainApplication.core.loggedInDeliveryman)).setBalance(Integer.parseInt(balanceTextField.getText()));
        initialize();
    }
    public void withdraw() {
        MainApplication.core.withdraw();
        initialize();
    }
    public void logout() {
        MainApplication.core.logout();
        MainApplication.stage.setScene(MainApplication.login);
    }
    public void initialize() {
        selectedRow = -1;
        idLabel.setText(String.valueOf(MainApplication.core.loggedInDeliveryman));
        usernameLabel.setText(MainApplication.core.accounts.get(MainApplication.core.loggedInDeliveryman).getUsername());
        passwordLabel.setText(MainApplication.core.accounts.get(MainApplication.core.loggedInDeliveryman).getPassword());
        recoveryQuestionLabel.setText(MainApplication.core.accounts.get(MainApplication.core.loggedInDeliveryman).getRecoveryQuestion());
        recoveryAnswerLabel.setText(MainApplication.core.accounts.get(MainApplication.core.loggedInDeliveryman).getRecoveryQuestionAnswer());
        balanceLabel.setText(String.valueOf(((Deliveryman)MainApplication.core.accounts.get(MainApplication.core.loggedInDeliveryman)).getBalance()));
        currentDelivery = ((Deliveryman)MainApplication.core.accounts.get(MainApplication.core.loggedInDeliveryman)).getActiveOrder();
        if (currentDelivery == -1) {
            currentDeliveryLabel.setText("No current delivery");
            startLabel.setText("Starting point: -");
            destinationLabel.setText("Destination point: -");
            estimationLabel.setText("Estimated time left: -");
        } else {
            currentDeliveryLabel.setText("Current delivery ID: " + currentDelivery);
            startLabel.setText("Starting point: " + MainApplication.core.restaurants.get(MainApplication.core.orders.get(currentDelivery).getRestaurant()).getLocation());
            destinationLabel.setText("Destination point: " + MainApplication.core.restaurants.get(MainApplication.core.orders.get(currentDelivery).getUserLocation()));
            estimationLabel.setText("Estimated time left: " + MainApplication.core.showEstimatedDeliveryTime());
        }
        startingPoint.setCellValueFactory(data -> data.getValue().get(0));
        destinationPoint.setCellValueFactory(data -> data.getValue().get(1));
        estimatedDuration.setCellValueFactory(data -> data.getValue().get(2));
        ObservableList<List<Object>> data = FXCollections.observableArrayList();
        availableOrders = MainApplication.core.showAvailableOrders();
        int deliverymanLocation = ((Deliveryman)MainApplication.core.accounts.get(MainApplication.core.loggedInDeliveryman)).getLocation();
        for (int i : availableOrders) {
            int userLocation = MainApplication.core.orders.get(i).getUserLocation();
            int restaurantLocation = MainApplication.core.restaurants.get(MainApplication.core.orders.get(i).getRestaurant()).getLocation();
            int toRestaurantTime = MainApplication.core.map.getShortestPath(deliverymanLocation, restaurantLocation).getTime();
            int toUserTime = MainApplication.core.map.getShortestPath(restaurantLocation, userLocation).getTime();
            List<Object> row = new ArrayList<>();
            row.add(new SimpleIntegerProperty(restaurantLocation));
            row.add(new SimpleIntegerProperty(userLocation));
            row.add(new SimpleIntegerProperty(toRestaurantTime + toUserTime));
            data.add(row);
        }
        availableOrdersTable.setItems(data);
    }
}