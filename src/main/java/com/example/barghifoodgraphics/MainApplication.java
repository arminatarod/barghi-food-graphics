package com.example.barghifoodgraphics;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainApplication extends Application {
    static Core core;
    static Stage stage;
    static Scene login, signup, userPage, forgotPassword, supermarket, cart, restaurant, comment, edit, add, adminPageOne, adminPageTwo, menu, deliverymanPage, addRestaurant, orderDetails, addDiscount;
    static FXMLLoader fxmlLoaderLogin, fxmlLoaderSignup, fxmlLoaderUserPage, fxmlLoaderForgotPassword, fxmlLoaderSupermarket, fxmlLoaderCart, fxmlLoaderRestaurant, fxmlLoaderComment, fxmlLoaderAdminPageOne, fxmlLoaderAdminPageTwo, fxmlLoaderEdit, fxmlLoaderAdd, fxmlLoaderMenu, fxmlLoaderDeliverymanPage, fxmlLoaderAddRestaurant, fxmlLoaderOrderDetails, fxmlLoaderAddDiscount;
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
        fxmlLoaderAdd = new FXMLLoader(MainApplication.class.getResource("add.fxml"));
        add = new Scene(fxmlLoaderAdd.load(), 400, 250);
        fxmlLoaderEdit = new FXMLLoader(MainApplication.class.getResource("edit.fxml"));
        edit = new Scene(fxmlLoaderEdit.load(), 400, 250);
        fxmlLoaderMenu = new FXMLLoader(MainApplication.class.getResource("menu.fxml"));
        menu = new Scene(fxmlLoaderMenu.load(), 400, 600);
        Timer timer = new Timer();
        TimerTask changeTraffic = new TimerTask() {
            @Override
            public void run() {
                int x = new Random().nextInt(19) + 1, y = new Random().nextInt(19) + 1, z = new Random().nextInt(3);
                core.setTraffic(x, y, z);
                System.out.println(x + ", " + y + ", " + z);
                core.setTraffic(14, 15, 0);
                System.out.println(core.getTraffic(14, 15));
            }
        };
        timer.schedule(changeTraffic, 0L, 500);
        stage = mainStage;
        stage.setTitle("Barghi Food");
        stage.setMinWidth(410);
        stage.setMinHeight(640);
        stage.setScene(login);
        stage.setOnCloseRequest(windowEvent -> System.exit(0));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}