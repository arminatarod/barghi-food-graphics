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
import java.util.HashMap;
import java.util.List;

public class deliverymanPageController {
    ArrayList<Integer> availableOrders;
    int currentDelivery, selectedRow;
    HashMap<Integer, ArrayList<Integer>> coordinatesOf = new HashMap<>();
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
        return MainApplication.core.getTraffic(x, y) < 1.4? Color.DODGERBLUE : MainApplication.core.getTraffic(x, y) < 1.8? Color.ORANGE : Color.RED;
    }
    public void refreshCanvas() {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, 360, 360);
        for (ArrayList<MapG.edge> nodeEdges : MainApplication.core.map.graph)
            for (MapG.edge edge : nodeEdges)
                drawLine(coordinatesOf.get(edge.getU()).get(0), coordinatesOf.get(edge.getU()).get(1), coordinatesOf.get(edge.getV()).get(0), coordinatesOf.get(edge.getV()).get(1), getColor(edge.getU(), edge.getV()), 1);
        graphicsContext.setFill(Color.BLACK);
        for (int i = 1; i <= 19; i++)
            graphicsContext.fillOval(coordinatesOf.get(i).get(0) - 5, coordinatesOf.get(i).get(1) - 5, 10, 10);
    }
    public void initialize() {
        for (int i = 1; i <= 19; i++)
            coordinatesOf.put(i, new ArrayList<>());
        coordinatesOf.get(1).add(25); coordinatesOf.get(1).add(25);
        coordinatesOf.get(2).add(75); coordinatesOf.get(2).add(25);
        coordinatesOf.get(3).add(235); coordinatesOf.get(3).add(25);
        coordinatesOf.get(4).add(285); coordinatesOf.get(4).add(25);
        coordinatesOf.get(5).add(335); coordinatesOf.get(5).add(25);
        coordinatesOf.get(6).add(25); coordinatesOf.get(6).add(75);
        coordinatesOf.get(7).add(75); coordinatesOf.get(7).add(75);
        coordinatesOf.get(8).add(235); coordinatesOf.get(8).add(75);
        coordinatesOf.get(9).add(155); coordinatesOf.get(9).add(125);
        coordinatesOf.get(10).add(285); coordinatesOf.get(10).add(125);
        coordinatesOf.get(11).add(75); coordinatesOf.get(11).add(155);
        coordinatesOf.get(12).add(235); coordinatesOf.get(12).add(155);
        coordinatesOf.get(13).add(335); coordinatesOf.get(13).add(205);
        coordinatesOf.get(14).add(75); coordinatesOf.get(14).add(255);
        coordinatesOf.get(15).add(235); coordinatesOf.get(15).add(255);
        coordinatesOf.get(16).add(285); coordinatesOf.get(16).add(255);
        coordinatesOf.get(17).add(25); coordinatesOf.get(17).add(335);
        coordinatesOf.get(18).add(155); coordinatesOf.get(18).add(335);
        coordinatesOf.get(19).add(335); coordinatesOf.get(19).add(335);
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