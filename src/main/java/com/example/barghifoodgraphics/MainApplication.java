package com.example.barghifoodgraphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    static Stage stage;
    static Scene login, signup, userPage, forgotPassword, supermarket, cart, restaurant, comment;
    static FXMLLoader fxmlLoaderLogin, fxmlLoaderSignup, fxmlLoaderUserPage, fxmlLoaderForgotPassword, fxmlLoaderSupermarket, fxmlLoaderCart, fxmlLoaderRestaurant, fxmlLoaderComment;
    @Override
    public void start(Stage mainStage) throws IOException {
        fxmlLoaderLogin = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        login = new Scene(fxmlLoaderLogin.load(), 400, 600);
        fxmlLoaderSignup = new FXMLLoader(MainApplication.class.getResource("signup.fxml"));
        signup = new Scene(fxmlLoaderSignup.load(), 400, 600);
        fxmlLoaderUserPage = new FXMLLoader(MainApplication.class.getResource("userPage.fxml"));
        userPage = new Scene(fxmlLoaderUserPage.load(), 400, 600);
        fxmlLoaderForgotPassword = new FXMLLoader(MainApplication.class.getResource("forgotPassword.fxml"));
        forgotPassword = new Scene(fxmlLoaderForgotPassword.load(), 400, 500);
        fxmlLoaderSupermarket = new FXMLLoader(MainApplication.class.getResource("supermarket.fxml"));
        supermarket = new Scene(fxmlLoaderSupermarket.load(), 400, 600);
        fxmlLoaderCart = new FXMLLoader(MainApplication.class.getResource("cart.fxml"));
        cart = new Scene(fxmlLoaderCart.load(), 600, 600);
        fxmlLoaderRestaurant = new FXMLLoader(MainApplication.class.getResource("restaurant.fxml"));
        restaurant = new Scene(fxmlLoaderRestaurant.load(), 400, 600);
        fxmlLoaderComment = new FXMLLoader(MainApplication.class.getResource("comment.fxml"));
        comment = new Scene(fxmlLoaderComment.load(), 400, 600);
        stage = mainStage;
        stage.setTitle("Barghi Food");
        stage.setMinWidth(410);
        stage.setMinHeight(640);
        stage.setScene(comment);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}