package com.example.barghifoodgraphics;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class cartController {
    int selectedRow;
    @FXML
    private TableColumn<List<StringProperty>, String> number, itemID, itemName, unitPrice, quantity, totalPrice, discount, finalPrice;
    @FXML
    private TableView<List<StringProperty>> cartTable;
    public void changeToUserPage() {
        MainApplication.stage.setScene(MainApplication.userPage);
    }
    public void tableClicked() {
        if (cartTable.getSelectionModel().getSelectedItem() == null)
            selectedRow = -1;
        else
            selectedRow = Integer.parseInt((cartTable.getSelectionModel().getSelectedItem().get(0)).getValue());
    }
    public boolean checkSelection() {
        if (selectedRow == -1) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No row selected");
            a.setContentText("You must select a row from the table.");
            a.show();
            return false;
        }
        return true;
    }
    public void increaseRow() {
        if (checkSelection()) {
            int foodID = Integer.parseInt((cartTable.getItems().get(selectedRow - 1).get(1)).getValue());
            ((User) MainApplication.core.accounts.get(MainApplication.core.loggedInUser)).getCart().addItem(foodID, 1);
        }
        initialize();
    }
    public void decreaseRow() {
        if (checkSelection()) {
            int foodID = Integer.parseInt((cartTable.getItems().get(selectedRow - 1).get(1)).getValue());
            ((User) MainApplication.core.accounts.get(MainApplication.core.loggedInUser)).getCart().removeItem(foodID, 1);
        }
        initialize();
    }
    public void deleteRow() {
        if (checkSelection()) {
            int foodID = Integer.parseInt((cartTable.getItems().get(selectedRow - 1).get(1)).getValue());
            ((User) MainApplication.core.accounts.get(MainApplication.core.loggedInUser)).getCart().removeItem(foodID, ((User) MainApplication.core.accounts.get(MainApplication.core.loggedInUser)).getCart().getItems().get(foodID));
        }
        initialize();
    }
    public void addOrder() {
        int done = MainApplication.core.confirmOrder();
        if (done == 1) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No one has logged in");
            a.setContentText("You must first log in.");
            a.show();
        } else if (done == 2) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Empty cart");
            a.setContentText("You cannot submit an empty order.");
            a.show();
        } else if (done == 3) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Insufficient balance");
            a.setContentText("The order exceeds your balance amount.");
            a.show();
        } else if (done == 4) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Inactive food");
            a.setContentText("There are items in your cart that are no longer active.");
            a.show();
        } else {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Order added successfully.");
            a.setContentText("The order has been confirmed and sent to the restaurant");
            a.show();
        }
        initialize();
    }
    public void initialize() {
        selectedRow = -1;
        number.setCellValueFactory(data -> data.getValue().get(0));
        itemID.setCellValueFactory(data -> data.getValue().get(1));
        itemName.setCellValueFactory(data -> data.getValue().get(2));
        unitPrice.setCellValueFactory(data -> data.getValue().get(3));
        quantity.setCellValueFactory(data -> data.getValue().get(4));
        totalPrice.setCellValueFactory(data -> data.getValue().get(5));
        discount.setCellValueFactory(data -> data.getValue().get(6));
        finalPrice.setCellValueFactory(data -> data.getValue().get(7));
        if (((User)MainApplication.core.accounts.get(MainApplication.core.loggedInUser)).getCart().getItems() != null) {
            ObservableList<List<StringProperty>> data = FXCollections.observableArrayList();
            int index = 1;
            for (int i : ((User) MainApplication.core.accounts.get(MainApplication.core.loggedInUser)).getCart().getItems().keySet()) {
                List<StringProperty> row = new ArrayList<>();
                row.add(new SimpleStringProperty(String.valueOf(index++)));
                row.add(new SimpleStringProperty(String.valueOf(i)));
                row.add(new SimpleStringProperty(MainApplication.core.foods.get(i).getName()));
                row.add(new SimpleStringProperty(String.valueOf(MainApplication.core.foods.get(i).getFoodPrice())));
                row.add(new SimpleStringProperty(String.valueOf(((User) MainApplication.core.accounts.get(MainApplication.core.loggedInUser)).getCart().getItems().get(i))));
                row.add(new SimpleStringProperty(String.valueOf((int) (MainApplication.core.foods.get(i).getFoodPrice() * (double) ((User) MainApplication.core.accounts.get(MainApplication.core.loggedInUser)).getCart().getItems().get(i) * (1.0 / (1.0 - MainApplication.core.foods.get(i).getFoodDiscount()))))));
                row.add(new SimpleStringProperty(String.valueOf((int) (MainApplication.core.foods.get(i).getFoodDiscount() * 100.0))));
                row.add(new SimpleStringProperty(String.valueOf((int) (MainApplication.core.foods.get(i).getFoodPrice() * (double) ((User) MainApplication.core.accounts.get(MainApplication.core.loggedInUser)).getCart().getItems().get(i)))));
                data.add(row);
            }
            cartTable.setItems(data);
        }
    }
}