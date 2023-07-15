package com.example.barghifoodgraphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    static Core core;
    static Stage stage;
    static Scene login, signup, userPage, forgotPassword, supermarket, cart, restaurant, comment, edit, add, adminPageOne, adminPageTwo, menu, deliverymanPage, addRestaurant, orderDetails;
    static FXMLLoader fxmlLoaderLogin, fxmlLoaderSignup, fxmlLoaderUserPage, fxmlLoaderForgotPassword, fxmlLoaderSupermarket, fxmlLoaderCart, fxmlLoaderRestaurant, fxmlLoaderComment, fxmlLoaderAdminPageOne, fxmlLoaderAdminPageTwo, fxmlLoaderEdit, fxmlLoaderAdd, fxmlLoaderMenu, fxmlLoaderDeliverymanPage, fxmlLoaderAddRestaurant, fxmlLoaderOrderDetails;
    @Override
    public void start(Stage mainStage) throws IOException {
        core = new Core();
        fxmlLoaderLogin = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        login = new Scene(fxmlLoaderLogin.load(), 400, 600);
        fxmlLoaderSignup = new FXMLLoader(MainApplication.class.getResource("signup.fxml"));
        signup = new Scene(fxmlLoaderSignup.load(), 400, 600);
        fxmlLoaderForgotPassword = new FXMLLoader(MainApplication.class.getResource("forgotPassword.fxml"));
        forgotPassword = new Scene(fxmlLoaderForgotPassword.load(), 400, 500);
        fxmlLoaderSupermarket = new FXMLLoader(MainApplication.class.getResource("supermarket.fxml"));
        supermarket = new Scene(fxmlLoaderSupermarket.load(), 400, 600);
        fxmlLoaderRestaurant = new FXMLLoader(MainApplication.class.getResource("restaurant.fxml"));
        restaurant = new Scene(fxmlLoaderRestaurant.load(), 400, 600);
        /*fxmlLoaderComment = new FXMLLoader(MainApplication.class.getResource("comment.fxml"));
        comment = new Scene(fxmlLoaderComment.load(), 400, 600);*/
        fxmlLoaderAdd = new FXMLLoader(MainApplication.class.getResource("add.fxml"));
        add = new Scene(fxmlLoaderAdd.load(), 400, 250);
        fxmlLoaderEdit = new FXMLLoader(MainApplication.class.getResource("edit.fxml"));
        edit = new Scene(fxmlLoaderEdit.load(), 400, 250);
        fxmlLoaderMenu = new FXMLLoader(MainApplication.class.getResource("menu.fxml"));
        menu = new Scene(fxmlLoaderMenu.load(), 400, 600);
        stage = mainStage;
        stage.setTitle("Barghi Food");
        stage.setMinWidth(410);
        stage.setMinHeight(640);
        stage.setScene(login);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}