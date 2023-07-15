package com.example.barghifoodgraphics;

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
import java.util.Random;

public class adminController2 {
    @FXML ListView<String> foodTypeListView;
    @FXML TableView<List<Object>> myTable;
    @FXML TableColumn<List<StringProperty>,String> FoodNameColumn;
    @FXML TableColumn<List<StringProperty>,String> FoodPriceColumn;
    @FXML TableColumn<List<StringProperty>,String> FoodAverageRatingColumn;
    @FXML TableColumn<List<StringProperty>,String> DiscountColumn;
    @FXML ImageView RestaurantPic;
    @FXML Button EditButton, AddButton, RemoveButton, CommentsButton;
    @FXML Label BackLabel;
    public String selectedFoodType;
    int selectedRow = -1;
    public void initialize()
    {
        foodTypeListView.getItems().addAll(MainApplication.core.restaurants.get(MainApplication.core.selectedRestaurant).getFoodType());
        FoodNameColumn.setCellValueFactory(data -> data.getValue().get(0));
        FoodPriceColumn.setCellValueFactory(data -> data.getValue().get(1));
        FoodAverageRatingColumn.setCellValueFactory(data -> data.getValue().get(2));
        DiscountColumn.setCellValueFactory(data -> data.getValue().get(3));
        ObservableList<List<Object>> data = FXCollections.observableArrayList();
        for(String type : MainApplication.core.restaurants.get(MainApplication.core.selectedRestaurant).getFoodType())
        {
            List<Object>row = new ArrayList<>();
            for(Integer foodId : MainApplication.core.restaurants.get(MainApplication.core.selectedRestaurant).getMenu())
            {

            }
            data.add(row);
            break;
        }
        myTable.setItems(data);
        String css = this.getClass().getResource("style2.css").toExternalForm();
        myTable.getStylesheets().add(css);
        css = this.getClass().getResource("style.css").toExternalForm();
        foodTypeListView.getStylesheets().add(css);
    }
    public void choosingFoodType()
    {
        if(foodTypeListView.getSelectionModel().getSelectedItem() == null)
            return;
        else
        {
            //TODO miad liste un ghzhro mide be data ke nmysh bede
            selectedFoodType = foodTypeListView.getItems().get(foodTypeListView.getSelectionModel().getSelectedIndex());
        }
    }
    public void goToComments() throws IOException {
        MainApplication.fxmlLoaderComment = new FXMLLoader(MainApplication.class.getResource("comment.fxml"));
        MainApplication.comment = new Scene(MainApplication.fxmlLoaderComment.load(), 400, 600);
        MainApplication.stage.setScene(MainApplication.comment);
        //TODO inja btye inke bug nkhore zdm login vli byd bere scene comment
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

        }
    }
    public void goEdit()
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
            MainApplication.stage.setScene(MainApplication.edit);
        }
    }
    public void goAdd()
    {
        MainApplication.stage.setScene(MainApplication.add);
    }
}
