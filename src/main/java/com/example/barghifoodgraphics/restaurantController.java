package com.example.barghifoodgraphics;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    public void changeToMenu(int restaurantID) throws IOException {
        MainApplication.fxmlLoaderMenu = new FXMLLoader(MainApplication.class.getResource("menu.fxml"));
        MainApplication.menu = new Scene(MainApplication.fxmlLoaderMenu.load(), 400, 600);
        //hamin khat payin ro mituni copy koni va badesh bejaye initialize meghdar ye moteghayer ro avaz koni
        //restaurantID morede nazar hamin bala gerefte shode, tu ye moteghayer berizesh
        //vali hatman akhare in taghirat bezar khat payin bemune ke update beshe
        ((menuController)MainApplication.fxmlLoaderMenu.getController()).initialize();
        MainApplication.stage.setScene(MainApplication.menu);
    }
    public void refreshSearch() {
        ArrayList<String> results = new ArrayList<>(MainApplication.core.searchRestaurantName(searchBox.getText()));
        searchResultsRestaurant.getItems().clear();
        searchResultsRestaurant.getItems().addAll(results);
        searchVbox.setVisible(!searchBox.getText().isEmpty());
    }
    public void initialize() throws FileNotFoundException {
        for (int i : MainApplication.core.getNearestRestaurants()) {
            VBox restaurantBox = new VBox();
            restaurantBox.setOnMouseClicked(mouseEvent -> {
                try {
                    changeToMenu(i);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            restaurantBox.setAlignment(Pos.TOP_CENTER);
            restaurantBox.setStyle("-fx-background-color: WHITE; -fx-background-radius: 10; -fx-cursor: hand;");
            restaurantBox.setPadding(new Insets(20, 20, 20, 20));
            VBox.setMargin(restaurantBox, new Insets(20, 0, 20, 0));
            restaurantBox.setPrefSize(10000, Region.USE_COMPUTED_SIZE);
            ImageView restaurantLogo = new ImageView(new Image(new FileInputStream("src/main/resources/restaurantLogos/" + i + ".png")));
            restaurantLogo.setPreserveRatio(true);
            restaurantLogo.setFitHeight(150);
            restaurantLogo.setFitWidth(200);
            Label restaurantTitle = new Label(MainApplication.core.restaurants.get(i).getName());
            restaurantTitle.setFont(Font.font("System", 24));
            restaurantTitle.setPadding(new Insets(5, 5, 5, 5));
            Label restaurantRating = new Label("Rating: " + "★".repeat((int)Math.round(Main.core.restaurants.get(i).getAverageRating())) + "☆".repeat(5 - (int)Math.round(Main.core.restaurants.get(i).getAverageRating())));
            restaurantRating.setPadding(new Insets(5, 5, 5, 5));
            StringBuilder foodTypes = new StringBuilder();
            for (String foodType : Main.core.restaurants.get(i).getFoodType())
                foodTypes.append(foodType).append('-');
            foodTypes.deleteCharAt(foodTypes.length() - 1);
            Label restaurantFoodTypes = new Label(foodTypes.toString());
            restaurantFoodTypes.setPadding(new Insets(5, 5, 5, 5));
            Label deliveryPrice = new Label("Delivery price: " + Main.core.showDeliveryPrice(i));
            deliveryPrice.setPadding(new Insets(5, 5, 5, 5));
            VBox.setMargin(deliveryPrice, new Insets(20, 0, 10, 0));
            restaurantBox.getChildren().addAll(restaurantLogo, restaurantTitle, restaurantRating, restaurantFoodTypes, deliveryPrice);
            restaurantsBox.getChildren().add(restaurantBox);
        }
    }
}