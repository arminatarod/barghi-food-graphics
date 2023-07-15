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

public class orderDetailsController {
    int orderID = -1;
    @FXML
    private TableColumn<List<StringProperty>, String> number, itemID, itemName, unitPrice, quantity, totalPrice, discount, finalPrice;
    @FXML
    private TableView<List<StringProperty>> orderTable;
    public void changeToUserPage() {
        MainApplication.stage.setScene(MainApplication.userPage);
    }
    public void initialize() {
        number.setCellValueFactory(data -> data.getValue().get(0));
        itemID.setCellValueFactory(data -> data.getValue().get(1));
        itemName.setCellValueFactory(data -> data.getValue().get(2));
        unitPrice.setCellValueFactory(data -> data.getValue().get(3));
        quantity.setCellValueFactory(data -> data.getValue().get(4));
        totalPrice.setCellValueFactory(data -> data.getValue().get(5));
        discount.setCellValueFactory(data -> data.getValue().get(6));
        finalPrice.setCellValueFactory(data -> data.getValue().get(7));
        ObservableList<List<StringProperty>> data = FXCollections.observableArrayList();
        int index = 1;
        if (orderID != -1)
            for (int i : MainApplication.core.orders.get(orderID).getItems().keySet()) {
                List<StringProperty> row = new ArrayList<>();
                row.add(new SimpleStringProperty(String.valueOf(index++)));
                row.add(new SimpleStringProperty(String.valueOf(i)));
                row.add(new SimpleStringProperty(MainApplication.core.foods.get(i).getName()));
                row.add(new SimpleStringProperty(String.valueOf(MainApplication.core.foods.get(i).getPrice())));
                row.add(new SimpleStringProperty(String.valueOf(MainApplication.core.orders.get(orderID).getItems().get(i))));
                row.add(new SimpleStringProperty(String.valueOf((int) (MainApplication.core.foods.get(i).getPrice() * (double) MainApplication.core.orders.get(orderID).getItems().get(i) * (1.0 / (1.0 - MainApplication.core.foods.get(i).getDiscount()))))));
                row.add(new SimpleStringProperty(String.valueOf((int) (MainApplication.core.foods.get(i).getDiscount() * 100.0))));
                row.add(new SimpleStringProperty(String.valueOf((int) (MainApplication.core.foods.get(i).getPrice() * (double) MainApplication.core.orders.get(orderID).getItems().get(i)))));
                data.add(row);
            }
        orderTable.setItems(data);
    }
}