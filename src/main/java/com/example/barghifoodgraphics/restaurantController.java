package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class restaurantController {
    @FXML
    private TextField searchBox;
    @FXML
    private ListView<String> searchResultsRestaurant;
    @FXML
    private VBox searchVbox, restaurantsBox;
    public void changeToUserPage() {
        MainApplication.stage.setScene(MainApplication.userPage);
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
        searchVbox.setVisible(!searchBox.getText().isEmpty());
    }
    public void initialize() throws FileNotFoundException {
        //for (int i : Main.core.getNearestRestaurants()) {
        for (int i = 1; i <= 5; i++) {
            VBox restaurantBox = new VBox();
            restaurantBox.setAlignment(Pos.TOP_CENTER);
            restaurantBox.setStyle("-fx-background-color: WHITE; -fx-background-radius: 10; -fx-cursor: hand;");
            restaurantBox.setPadding(new Insets(20, 20, 20, 20));
            VBox.setMargin(restaurantBox, new Insets(20, 0, 20, 0));
            restaurantBox.setPrefSize(10000, Region.USE_COMPUTED_SIZE);
            ImageView restaurantLogo = new ImageView(new Image(new FileInputStream("src/main/resources/restaurantLogos/" + i + ".png")));
            restaurantLogo.setPreserveRatio(true);
            restaurantLogo.setFitHeight(150);
            restaurantLogo.setFitWidth(200);
            Label restaurantTitle = new Label("ASDF"/*Main.core.restaurants.get(i).getName()*/);
            restaurantTitle.setFont(Font.font("System", 24));
            restaurantTitle.setPadding(new Insets(5, 5, 5, 5));
            Label restaurantRating = new Label("Rating: " + "★".repeat(3/*Math.round(Main.core.restaurants.get(i).getAverageRating()*/) + "☆".repeat(5 - 3/*Math.round(Main.core.restaurants.get(i).getAverageRating()*/));
            restaurantRating.setPadding(new Insets(5, 5, 5, 5));
            /*StringBuilder foodTypes = new StringBuilder();
            for (String foodType : Main.core.restaurants.get(i).getFoodType())
                foodTypes.append(foodType).append('-');
            foodTypes.deleteCharAt(foodTypes.length() - 1);*/
            Label restaurantFoodTypes = new Label("A-B-C-D"/*foodTypes.toString()*/);
            restaurantFoodTypes.setPadding(new Insets(5, 5, 5, 5));
            Label deliveryPrice = new Label("Delivery price: " + 1000/*Main.core.getDeliveryPrice(i)*/);
            deliveryPrice.setPadding(new Insets(5, 5, 5, 5));
            VBox.setMargin(deliveryPrice, new Insets(20, 0, 10, 0));
            restaurantBox.getChildren().addAll(restaurantLogo, restaurantTitle, restaurantRating, restaurantFoodTypes, deliveryPrice);
            restaurantsBox.getChildren().add(restaurantBox);
        }
    }
}