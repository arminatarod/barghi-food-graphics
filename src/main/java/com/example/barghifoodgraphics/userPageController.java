package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class userPageController {
    @FXML
    private TextField searchBox;
    @FXML
    private ListView searchResultsRestaurant, searchResultsFood;
    @FXML
    private VBox searchVbox;
    public void refreshSearch() {
        ArrayList<String> results = new ArrayList<>();
        //results.addAll(Arrays.asList(Main.core.searchRestaurantName().split("\n")));
        results.add("Restaurant1");
        results.add("Restaurant2");
        results.add("Restaurant3");
        results.add("Restaurant4");
        results.add("Restaurant5");
        results.add("Restaurant6");
        results.add("Restaurant7");
        results.add("Restaurant8");
        results.add("Restaurant9");
        results.add("Restaurant10");
        searchResultsRestaurant.getItems().clear();
        searchResultsRestaurant.getItems().addAll(results);
        results.clear();
        //results.addAll(Arrays.asList(Main.core.searchFoodName().split("\n")));
        results.add("Food1");
        results.add("Food2");
        results.add("Food3");
        results.add("Food4");
        results.add("Food5");
        results.add("Food6");
        results.add("Food7");
        results.add("Food8");
        results.add("Food9");
        results.add("Food10");
        searchResultsFood.getItems().clear();
        searchResultsFood.getItems().addAll(results);
        searchVbox.setVisible(!searchBox.getText().isEmpty());
    }
}