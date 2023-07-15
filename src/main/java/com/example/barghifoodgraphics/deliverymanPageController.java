package com.example.barghifoodgraphics;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

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
    @FXML
    private Canvas mapCanvas;
    GraphicsContext graphicsContext;
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
    public void drawLine(int aX, int aY, int bX, int bY, Color color, int width) {
        graphicsContext.setStroke(color);
        graphicsContext.setLineWidth(width);
        graphicsContext.beginPath();
        graphicsContext.moveTo(aX, aY);
        graphicsContext.lineTo(bX, bY);
        graphicsContext.stroke();
    }
    public Color getColor(int x, int y) {
        return MainApplication.core.getTraffic(x, y) == 0? Color.DODGERBLUE : MainApplication.core.getTraffic(x, y) == 1? Color.ORANGE : Color.RED;
    }
    public void refreshCanvas() {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, 360, 360);
        drawLine(25, 25, 75, 25, getColor(1, 2), 1);
        drawLine(235, 25, 285, 25, getColor(3, 4), 1);
        drawLine(285, 25, 335, 25, getColor(4, 5), 1);
        drawLine(25, 25, 25, 75, getColor(1, 6), 1);
        drawLine(25, 75, 75, 75, getColor(6, 7), 1);
        drawLine(75, 25, 75, 75, getColor(2, 7), 1);
        drawLine(75, 75, 155, 125, getColor(7, 9), 1);
        drawLine(155, 125, 235, 75, getColor(9, 8), 1);
        drawLine(235, 25, 235, 75, getColor(3, 8), 1);
        drawLine(235, 75, 285, 125, getColor(8, 10), 1);
        drawLine(75, 155, 155, 125, getColor(11, 9), 1);
        drawLine(155, 125, 235, 155, getColor(9, 12), 1);
        drawLine(285, 25, 285, 125, getColor(4, 10), 1);
        drawLine(75, 155, 75, 255, getColor(11, 14), 1);
        drawLine(235, 155, 235, 255, getColor(12, 15), 1);
        drawLine(75, 255, 235, 255, getColor(14, 15), 1);
        drawLine(235, 255, 285, 255, getColor(15, 16), 1);
        drawLine(75, 255, 25, 335, getColor(14, 17), 1);
        drawLine(25, 335, 155, 335, getColor(17, 18), 1);
        drawLine(155, 335, 335, 335, getColor(18, 19), 1);
        drawLine(285, 255, 335, 335, getColor(16, 19), 1);
        drawLine(335, 205, 335, 335, getColor(13, 19), 1);
        drawLine(335, 205, 335, 25, getColor(13, 5), 1);
        drawLine(335, 205, 285, 255, getColor(13, 16), 1);
        drawLine(25, 75, 25, 335, getColor(6, 17), 1);
        drawLine(155, 335, 75, 255, getColor(18, 14), 1);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillOval(20, 20, 10, 10);
        graphicsContext.fillOval(70, 20, 10, 10);
        graphicsContext.fillOval(20, 70, 10, 10);
        graphicsContext.fillOval(70, 70, 10, 10);
        graphicsContext.fillOval(230, 20, 10, 10);
        graphicsContext.fillOval(230, 70, 10, 10);
        graphicsContext.fillOval(70, 150, 10, 10);
        graphicsContext.fillOval(150, 120, 10, 10);
        graphicsContext.fillOval(280, 20, 10, 10);
        graphicsContext.fillOval(330, 20, 10, 10);
        graphicsContext.fillOval(230, 150, 10, 10);
        graphicsContext.fillOval(280, 120, 10, 10);
        graphicsContext.fillOval(330, 200, 10, 10);
        graphicsContext.fillOval(70, 250, 10, 10);
        graphicsContext.fillOval(230, 250, 10, 10);
        graphicsContext.fillOval(280, 250, 10, 10);
        graphicsContext.fillOval(20, 330, 10, 10);
        graphicsContext.fillOval(150, 330, 10, 10);
        graphicsContext.fillOval(330, 330, 10, 10);
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
        graphicsContext = mapCanvas.getGraphicsContext2D();
        refreshCanvas();
    }
}