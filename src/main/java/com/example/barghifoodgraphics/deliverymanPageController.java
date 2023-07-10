package com.example.barghifoodgraphics;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class deliverymanPageController {
    int selectedRow = -1;
    @FXML
    private TableColumn<List<StringProperty>, String> startingPoint, destinationPoint, estimatedDuration;
    @FXML
    private TableView<List<Object>> availableOrdersTable;
    @FXML
    private Label startLabel, destinationLabel, estimationLabel;
    public void tableClicked() {
        if (availableOrdersTable.getSelectionModel().getSelectedItem() == null)
            selectedRow = -1;
        else
            selectedRow = ((IntegerProperty)availableOrdersTable.getSelectionModel().getSelectedItem().get(0)).getValue();
    }
    public void accept() {
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
        //int currentDelivery = Deliveryman.getDeliveryman(Main.core.loggedInDeliveryman).getActiveOrder();
        int currentDelivery = -1;
        if (currentDelivery == -1) {
            startLabel.setText("Starting point: -");
            destinationLabel.setText("Destination point: -");
            estimationLabel.setText("Estimated time left: -");

        }
        startingPoint.setCellValueFactory(data -> data.getValue().get(0));
        destinationPoint.setCellValueFactory(data -> data.getValue().get(1));
        estimatedDuration.setCellValueFactory(data -> data.getValue().get(2));
        ObservableList<List<Object>> data = FXCollections.observableArrayList();
        //TODO: get data
        for (int i = 1; i <= 30; i++) {
            List<Object> row = new ArrayList<>();
            row.add(new SimpleIntegerProperty(i));
            row.add(new SimpleIntegerProperty(1000 * i));
            row.add(new SimpleIntegerProperty(i * 10));
            data.add(row);
        }
        availableOrdersTable.setItems(data);
    }
}