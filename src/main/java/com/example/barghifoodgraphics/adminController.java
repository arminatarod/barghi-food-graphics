package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class adminController {
    @FXML
    ListView restaurantListView;
    @FXML Button AddNewRestaurantButton;
    public void initialize()
    {
        System.out.println(1);
        ArrayList<String> restaurants = new ArrayList<>();
        for(Integer i : ((Admin)MainApplication.core.accounts.get(MainApplication.core.loggedInAdmin)).getRestaurants())
            restaurants.add(MainApplication.core.restaurants.get(i).getName());
        restaurantListView.getItems().addAll(restaurants);
        String css = this.getClass().getResource("style.css").toExternalForm();
        restaurantListView.getStylesheets().add(css);
    }
    public void goToRestaurantPage() throws IOException {
        if(restaurantListView.getSelectionModel().getSelectedItem() == null)
            return;
        else
        {
            String target = (String) restaurantListView.getItems().get(restaurantListView.getSelectionModel().getSelectedIndex());
            for(Map.Entry<Integer, Restaurant> tmp : MainApplication.core.restaurants.entrySet())
            {
                if(tmp.getValue().getName().equals(target))
                    MainApplication.core.selectedRestaurant = tmp.getKey();
            }
            MainApplication.fxmlLoaderAdminPageTwo = new FXMLLoader(MainApplication.class.getResource("adminPage2.fxml"));
            MainApplication.adminPageTwo = new Scene(MainApplication.fxmlLoaderAdminPageTwo.load(), 700, 600);
            MainApplication.stage.setScene(MainApplication.adminPageTwo);
        }
    }
    public void addNewRestaurant() throws IOException {
        MainApplication.fxmlLoaderAddRestaurant = new FXMLLoader(MainApplication.class.getResource("addRestaurant.fxml"));
        MainApplication.addRestaurant = new Scene(MainApplication.fxmlLoaderAddRestaurant.load(), 400, 300);
        MainApplication.stage.setScene(MainApplication.addRestaurant);
    }
}
