package com.example.barghifoodgraphics;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class adminController2 {
    @FXML ListView<String> foodTypeListView;
    @FXML TableView<List<Object>> myTable;
    @FXML TableColumn<List<StringProperty>,String> foodNameColumn, foodPriceColumn, averageRatingColumn, discountColumn, discountTimestampColumn;
    @FXML ImageView restaurantPicture;
    @FXML Button EditButton, AddButton, RemoveButton, CommentsButton;
    @FXML Label BackLabel;
    public String selectedFoodType;
    int selectedRow = -1;
    boolean forFirst = true;
    public void initialize()
    {
        foodTypeListView.getItems().clear();
        foodTypeListView.getItems().addAll(MainApplication.core.restaurants.get(MainApplication.core.selectedRestaurant).getFoodType());
        foodNameColumn.setCellValueFactory(data -> data.getValue().get(0));
        foodPriceColumn.setCellValueFactory(data -> data.getValue().get(1));
        averageRatingColumn.setCellValueFactory(data -> data.getValue().get(2));
        discountColumn.setCellValueFactory(data -> data.getValue().get(3));
        discountTimestampColumn.setCellValueFactory(data -> data.getValue().get(4));
        ObservableList<List<Object>> data = FXCollections.observableArrayList();
        for (String type : MainApplication.core.restaurants.get(MainApplication.core.selectedRestaurant).getFoodType()) {
            if (forFirst) {
                selectedFoodType = type;
                forFirst = false;
            }
            for (Integer foodId : MainApplication.core.restaurants.get(MainApplication.core.selectedRestaurant).getMenu()) {
                List<Object>row = new ArrayList<>();
                if (MainApplication.core.foods.get(foodId).getFoodType().equals(selectedFoodType)) {
                    row.add(new SimpleStringProperty(MainApplication.core.foods.get(foodId).getName()));
                    row.add(new SimpleDoubleProperty(MainApplication.core.foods.get(foodId).getPrice()));
                    row.add(new SimpleDoubleProperty(MainApplication.core.foods.get(foodId).getAverageRating()));
                    if (MainApplication.core.foods.get(foodId).getDiscount() == 0) {
                        row.add(new SimpleStringProperty("%" + 0));
                        row.add(new SimpleStringProperty("-"));
                    } else {
                        row.add(new SimpleStringProperty("%" + MainApplication.core.foods.get(foodId).getDiscount()));
                        row.add(new SimpleStringProperty(MainApplication.core.foods.get(foodId).getDiscountTimestamp().toString()));
                    }
                }
                data.add(row);
            }
        }
        myTable.setItems(data);
        String css = this.getClass().getResource("style2.css").toExternalForm();
        myTable.getStylesheets().add(css);
        css = this.getClass().getResource("style.css").toExternalForm();
        foodTypeListView.getStylesheets().add(css);
    }
    public void choosingFoodType() {
        if (foodTypeListView.getSelectionModel().getSelectedItem() != null) {
            selectedFoodType = foodTypeListView.getItems().get(foodTypeListView.getSelectionModel().getSelectedIndex());
            initialize();
        }
    }
    public void goToComments() throws IOException {
        MainApplication.fxmlLoaderComment = new FXMLLoader(MainApplication.class.getResource("comment.fxml"));
        MainApplication.comment = new Scene(MainApplication.fxmlLoaderComment.load(), 400, 600);
        ((commentController)MainApplication.fxmlLoaderComment.getController()).isAdmin = true;
        ((commentController)MainApplication.fxmlLoaderComment.getController()).isFood = true;
        ((commentController)MainApplication.fxmlLoaderComment.getController()).objectID = MainApplication.core.selectedRestaurant;
        ((commentController)MainApplication.fxmlLoaderComment.getController()).previousScene = MainApplication.adminPageTwo;
        ((commentController)MainApplication.fxmlLoaderComment.getController()).initialize();
        MainApplication.stage.setScene(MainApplication.comment);
    }
    public void back()
    {
        MainApplication.stage.setScene(MainApplication.adminPageOne);
    }
    public void myTableClicked()
    {
        if(myTable.getSelectionModel().getSelectedItem() == null)
            selectedRow = -1;
        else
            selectedRow = myTable.getSelectionModel().getSelectedIndex();
    }
    public void Remove()
    {
        if(selectedRow == -1)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No row selected");
            a.setContentText("You must select a row from the table.");
            a.show();
        }
        else
        {
            for(Map.Entry<Integer, Food> food : MainApplication.core.foods.entrySet())
            {
                if(myTable.getColumns().get(0).getCellData(selectedRow).equals(food.getValue().getName()))
                    MainApplication.core.removeFood(food.getKey());
            }
            initialize();
        }
    }
    public void goEdit() throws IOException {
        if(selectedRow == -1)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No row selected");
            a.setContentText("You must select a row from the table.");
            a.show();
        }
        else
        {
            for(Map.Entry<Integer, Food> food : MainApplication.core.foods.entrySet())
            {
                if(myTable.getColumns().get(0).getCellData(selectedRow).equals(food.getValue().getName()))
                    MainApplication.core.selectedFood = food.getKey();
            }
            MainApplication.fxmlLoaderEdit = new FXMLLoader(MainApplication.class.getResource("edit.fxml"));
            MainApplication.edit = new Scene(MainApplication.fxmlLoaderEdit.load(), 400, 250);
            MainApplication.stage.setScene(MainApplication.edit);
        }
    }
    public void goAdd()
    {
        MainApplication.stage.setScene(MainApplication.add);
    }
    public void addFoodType()
    {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setContentText("Please add the new type");
        inputDialog.setTitle("Add");
        inputDialog.show();
        inputDialog.setOnHidden(event -> {
            MainApplication.core.addFoodType(inputDialog.getResult(),true);
        });
    }
    public void Discount() throws IOException {
        if(selectedRow == -1)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No row selected");
            a.setContentText("You must select a row from the table.");
            a.show();
        }
        else
        {
            for(Map.Entry<Integer, Food> food : MainApplication.core.foods.entrySet())
            {
                if(myTable.getColumns().get(0).getCellData(selectedRow).equals(food.getValue().getName()))
                    MainApplication.core.selectedFood = food.getKey();
            }
            MainApplication.fxmlLoaderAddDiscount = new FXMLLoader(MainApplication.class.getResource("addDiscount.fxml"));
            MainApplication.addDiscount = new Scene(MainApplication.fxmlLoaderAddDiscount.load(), 400, 600);
            MainApplication.stage.setScene(MainApplication.addDiscount);
        }
    }
}
