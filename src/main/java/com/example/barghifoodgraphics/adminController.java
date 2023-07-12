package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.util.ArrayList;

public class adminController {
    @FXML
    ListView restaurantListView;
    @FXML Button AddNewRestaurantButton;
    public void initialize()
    {
        ArrayList<String> restaurants= new ArrayList<>();
        for(int i=0;i<10;i++)
            restaurants.add("Restaurant"+i);
        restaurantListView.getItems().addAll(restaurants);
        String css = this.getClass().getResource("style.css").toExternalForm();
        restaurantListView.getStylesheets().add(css);
    }
    public void goToRestaurantPage()
    {
        if(restaurantListView.getSelectionModel().getSelectedItem() == null)
            return;
        else
        {
            //TODO inja index be admincontroller2 dade mishe ke restauran moshakhs beshe
            System.out.println(restaurantListView.getSelectionModel().getSelectedIndex());
            MainApplication.stage.setScene(MainApplication.adminPageTwo);
        }
    }
    public void addNewRestaurant()
    {
        //TODO add new restaurant
    }
}
