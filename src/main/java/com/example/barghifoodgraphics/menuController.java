package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Arrays;
import java.util.List;

public class menuController {
    @FXML TabPane myTabPane;
    @FXML Button CommentsButton;
    @FXML Label  averageRatingLabel, BackLabel;
    public void initialize()
    {
        List<String> foodTypes = Arrays.asList("Appetizers", "Entrees", "Desserts", "Pizza", "Pasta", "Drinks", "Chicken", "Steak", "Coffee");
        for(String FoodType : foodTypes)
        {
            Tab tab = new Tab(FoodType);
            ListView<String> listView = new ListView<>();
            for(int i=1;i<16;i++)
            {
                listView.getItems().add("Food" + i);
            }
            listView.setPrefSize(400,402);
            tab.setContent(listView);
            myTabPane.getTabs().add(tab);
        }
        averageRatingLabel.setText(Double.toString(4.8));
    }
}
