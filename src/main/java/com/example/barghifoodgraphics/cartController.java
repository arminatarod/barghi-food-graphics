package com.example.barghifoodgraphics;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    private TableView<List<Object>> cartTable;
    public void changeToUserPage() {
        MainApplication.stage.setScene(MainApplication.userPage);
    }
    public void tableClicked() {
        if (cartTable.getSelectionModel().getSelectedItem() == null)
            selectedRow = -1;
        else
            selectedRow = ((IntegerProperty)cartTable.getSelectionModel().getSelectedItem().get(0)).getValue();
    }
    public void increaseRow() {
        if (selectedRow == -1) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No row selected");
            a.setContentText("You must select a row from the table.");
            a.show();
            return;
        }
        int foodID = ((IntegerProperty)cartTable.getItems().get(selectedRow - 1).get(1)).getValue();
        //((User)Main.core.accounts.get(Main.core.loggedInUser)).getCart().addItem(foodID, 1);*/
        initialize();
    }
    public void decreaseRow() {
        if (selectedRow == -1) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No row selected");
            a.setContentText("You must select a row from the table.");
            a.show();
            return;
        }
        int foodID = ((IntegerProperty)cartTable.getItems().get(selectedRow - 1).get(1)).getValue();
        //((User)Main.core.accounts.get(Main.core.loggedInUser)).getCart().removeItem(foodID, 1);*/
        initialize();
    }
    public void deleteRow() {
        if (selectedRow == -1) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No row selected");
            a.setContentText("You must select a row from the table.");
            a.show();
            return;
        }
        int foodID = ((IntegerProperty)cartTable.getItems().get(selectedRow - 1).get(1)).getValue();
        //((User)Main.core.accounts.get(Main.core.loggedInUser)).getCart().removeItem(foodID, ((User)Main.core.accounts.get(Main.core.loggedInUser)).getCart().getItems().get(foodID));
        initialize();
    }
    public void addOrder() {
        int done = /*Main.core.confirmOrder()*/0;
        if (done != 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Order wasn't submitted");
            a.setContentText("A problem occurred while finalizing this order.");
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
        ObservableList<List<Object>> data = FXCollections.observableArrayList();
        int index = 1;
        //for (int i : ((User)Main.core.accounts.get(Main.core.loggedInUser)).getCart().getItems().keySet()) {
        for (int i = 1; i <= 30; i++) {
            List<Object> row = new ArrayList<>();
            row.add(new SimpleIntegerProperty(index++));
            row.add(new SimpleIntegerProperty(i));
            row.add(new SimpleStringProperty(/*Main.core.foods.get(i).getName()*/));
            row.add(new SimpleIntegerProperty(/*(int)Main.core.foods.get(i).getPrice()*/));
            row.add(new SimpleIntegerProperty(/*((User)Main.core.accounts.get(Main.core.loggedInUser)).getCart().getItems().get(i)*/));
            row.add(new SimpleIntegerProperty(/*(int)(Main.core.foods.get(i).getPrice() * (double)((User)Main.core.accounts.get(Main.core.loggedInUser)).getCart().getItems().get(i) * (1.0 / (1.0 - Main.core.foods.get(i).getDiscount())))*/));
            row.add(new SimpleIntegerProperty(/*Main.core.foods.get(i).getDiscount()*/));
            row.add(new SimpleIntegerProperty(/*(int)(Main.core.foods.get(i).getPrice() * (double)((User)Main.core.accounts.get(Main.core.loggedInUser)).getCart().getItems().get(i))*/));
            data.add(row);
        }
        cartTable.setItems(data);
    }
}