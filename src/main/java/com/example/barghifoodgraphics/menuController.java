package com.example.barghifoodgraphics;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class menuController {
    @FXML TabPane myTabPane;
    @FXML Button CommentsButton, addButton;
    @FXML Label  averageRatingLabel, BackLabel;
    @FXML ChoiceBox<Integer> quantity;
    public List<TableView<List<Object>>> tableViews = new ArrayList<>();
    public void initialize()
    {
        int available = 5;
        for(int i=0;i<available;i++)
            quantity.getItems().add(i+1);
        List<String> foodTypes = Arrays.asList("Appetizers", "Entrees", "Desserts", "Pizza", "Pasta", "Drinks", "Chicken", "Steak", "Coffee");
        for(String FoodType : foodTypes)
        {
            TableView<List<Object>> tableView = new TableView<>();
            TableColumn<List<StringProperty>,String> FoodNameColumn = new TableColumn<>("Name");
            TableColumn<List<StringProperty>,String> FoodPriceColumn = new TableColumn<>("Price");
            TableColumn<List<StringProperty>,String> FoodAverageRatingColumn = new TableColumn<>("Rating");
            TableColumn<List<StringProperty>,String> DiscountColumn = new TableColumn<>("Discount");
            FoodNameColumn.setCellValueFactory(data -> data.getValue().get(0));
            FoodPriceColumn.setCellValueFactory(data -> data.getValue().get(1));
            FoodAverageRatingColumn.setCellValueFactory(data -> data.getValue().get(2));
            DiscountColumn.setCellValueFactory(data -> data.getValue().get(3));
            ObservableList<List<Object>> data = FXCollections.observableArrayList();
            for(int i = 1;i < 7;i++)
            {
                Random random = new Random();
                List<Object>row = new ArrayList<>();
                row.add(new SimpleStringProperty("Food" + i));
                row.add(new SimpleIntegerProperty(100 * i + 230));
                row.add(new SimpleIntegerProperty(random.nextInt(5)+1));
                row.add(new SimpleStringProperty("%" + random.nextInt(20)));
                data.add(row);
            }
            tableView.setItems(data);
            Tab tab = new Tab(FoodType);
            tab.setContent(tableView);
            myTabPane.getTabs().add(tab);
            tableViews.add(tableView);
        }
        averageRatingLabel.setText(Double.toString(4.8));
    }
    public void addToCart()
    {
        TableView<List<Object>> tmp = tableViews.get(myTabPane.getSelectionModel().getSelectedIndex());
        if(tmp.getSelectionModel().getSelectedItem() == null)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("You should choose an item");
            a.show();
        }
        else if(quantity.getValue() == null)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("You should at least picking one of the item");
            a.show();
        }
        else
        {


        }
    }
}
