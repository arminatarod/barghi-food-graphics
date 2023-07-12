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
    public List<TableView<List<StringProperty>>> tableViews = new ArrayList<>();
    public void initialize()
    {
        int available = 5;
        for(int i=0;i<available;i++)
            quantity.getItems().add(i+1);
        List<String> foodTypes = Arrays.asList("Appetizers", "Entrees", "Desserts", "Pizza", "Pasta", "Drinks", "Chicken", "Steak", "Coffee");
        for(String FoodType : foodTypes)
        {
            TableView<List<StringProperty>> tableView = new TableView<>();
            TableColumn<List<StringProperty>,String> FoodNameColumn = new TableColumn<>("Name");
            TableColumn<List<StringProperty>,String> FoodPriceColumn = new TableColumn<>("Price");
            TableColumn<List<StringProperty>,String> FoodAverageRatingColumn = new TableColumn<>("Rating");
            TableColumn<List<StringProperty>,String> DiscountColumn = new TableColumn<>("Discount");
            tableView.getColumns().add(FoodNameColumn);
            tableView.getColumns().add(FoodPriceColumn);
            tableView.getColumns().add(FoodAverageRatingColumn);
            tableView.getColumns().add(DiscountColumn);
            FoodNameColumn.setCellValueFactory(data -> data.getValue().get(0));
            FoodPriceColumn.setCellValueFactory(data -> data.getValue().get(1));
            FoodAverageRatingColumn.setCellValueFactory(data -> data.getValue().get(2));
            DiscountColumn.setCellValueFactory(data -> data.getValue().get(3));
            ObservableList<List<StringProperty>> data = FXCollections.observableArrayList();
            for(int i = 1;i < 7;i++)
            {
                Random random = new Random();
                List<StringProperty>row = new ArrayList<>();
                row.add(new SimpleStringProperty("Food" + i));
                row.add(new SimpleStringProperty(String. valueOf(100 * i + 230)));
                row.add(new SimpleStringProperty(String.valueOf(random.nextInt(5)+1)));
                row.add(new SimpleStringProperty("%" + random.nextInt(20)));
                data.add(row);
            }
            tableView.setItems(data);
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
            Tab tab = new Tab(FoodType, tableView);
            myTabPane.getTabs().add(tab);
            tableViews.add(tableView);
        }
        averageRatingLabel.setText(Double.toString(4.8));
        quantity.setValue(1);
    }
    public void addToCart()
    {
        TableView<List<StringProperty>> tmp = tableViews.get(myTabPane.getSelectionModel().getSelectedIndex());
        if(tmp.getSelectionModel().getSelectedItem() == null)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("You should choose an item");
            a.show();
        }
        else
        {
            int j = tmp.getSelectionModel().getSelectedIndex();
            System.out.println(tmp.getColumns().get(0).getCellData(j));
            System.out.println(tmp.getColumns().get(1).getCellData(j));
            //TODO add krdn be cart
        }
    }
}
