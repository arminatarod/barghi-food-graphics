package com.example.barghifoodgraphics;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.*;

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
        if (MainApplication.core.restaurants.get(MainApplication.core.selectedRestaurant) == null)
            return;
        for(String FoodType : MainApplication.core.restaurants.get(MainApplication.core.selectedRestaurant).getFoodType())
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
        averageRatingLabel.setText(Double.toString(MainApplication.core.restaurants.get(MainApplication.core.selectedRestaurant).getAverageRating()));
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
            String foodName = (String) tmp.getColumns().get(0).getCellData(tmp.getSelectionModel().getSelectedIndex());
            for(Map.Entry<Integer, Food> food : MainApplication.core.foods.entrySet())
            {
                if(food.getValue().getName().equals(foodName))
                {
                    MainApplication.core.selectedFood = food.getKey();
                    MainApplication.core.addToCart(quantity.getValue());
                    break;
                }
            }
        }
    }
}
