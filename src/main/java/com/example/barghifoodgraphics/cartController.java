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
    int selectedRow = -1;
    @FXML
    private TableColumn<List<StringProperty>, String> number, item, unitPrice, quantity, totalPrice, discount, finalPrice;
    @FXML
    private TableView<List<Object>> cartTable;
    public void changeToUserPage() {
        MainApplication.stage.setScene(MainApplication.userPage);
    }
    public void tableClicked() {
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
        //TODO: update data
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
        //TODO: update data
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
        //TODO: update data
        initialize();
    }
    public void initialize() {
        number.setCellValueFactory(data -> data.getValue().get(0));
        item.setCellValueFactory(data -> data.getValue().get(1));
        unitPrice.setCellValueFactory(data -> data.getValue().get(2));
        quantity.setCellValueFactory(data -> data.getValue().get(3));
        totalPrice.setCellValueFactory(data -> data.getValue().get(4));
        discount.setCellValueFactory(data -> data.getValue().get(5));
        finalPrice.setCellValueFactory(data -> data.getValue().get(6));
        ObservableList<List<Object>> data = FXCollections.observableArrayList();
        //TODO: get data
        for (int i = 1; i <= 30; i++) {
            List<Object> row = new ArrayList<>();
            row.add(new SimpleIntegerProperty(i));
            row.add(new SimpleStringProperty("Food" + i));
            row.add(new SimpleIntegerProperty(1000 * i));
            row.add(new SimpleIntegerProperty(i));
            row.add(new SimpleIntegerProperty(1000 * i * i));
            row.add(new SimpleIntegerProperty(100 * i));
            row.add(new SimpleIntegerProperty(1000 * i * i - 100 * i));
            data.add(row);
        }
        cartTable.setItems(data);
    }
}