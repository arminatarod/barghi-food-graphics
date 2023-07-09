package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class userPageController {
    ArrayList<String> locations = new ArrayList<>();
    @FXML
    private TextField searchBox;
    @FXML
    private ListView<String> searchResultsRestaurant, searchResultsFood;
    @FXML
    private VBox searchVbox;
    @FXML
    private ComboBox<String> locationBox;
    public void changeToRestaurant() {
        MainApplication.stage.setScene(MainApplication.restaurant);
    }
    public void changeToSupermarket() {
        MainApplication.stage.setScene(MainApplication.supermarket);
    }
    public void changeToCart() {
        MainApplication.stage.setScene(MainApplication.cart);
    }
    public void refreshSearch() {
        ArrayList<String> results = new ArrayList<>();
        //results.addAll(Arrays.asList(Main.core.searchRestaurantName(searchBox.getText()).split("\n")));
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
    public void initialize() {
        //locationBox.getItems().addAll(User.getUser(Main.core.loggedInUser).getLocations());
        locations.add("1");
        locations.add("2");
        locations.add("3");
        locations.add("4");
        locations.add("5");
        locations.add("6");
        locations.add("7");
        locations.add("8");
        locations.add("9");
        locationBox.getItems().addAll(locations);
        locationBox.getSelectionModel().selectedItemProperty().addListener((observableValue, o, selection) -> {
            //User.getUser(Main.core.loggedInUser).setSelectedLocation(Integer.parseInt(selection.toString()));
            System.out.println(Integer.parseInt(selection));
        });
    }
}