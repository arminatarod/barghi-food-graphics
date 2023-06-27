package com.example.barghifoodgraphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    static Stage stage;
    static Scene login, signup, userPage;
    @Override
    public void start(Stage mainStage) throws IOException {
        FXMLLoader fxmlLoaderLogin = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        login = new Scene(fxmlLoaderLogin.load(), 400, 600);
        FXMLLoader fxmlLoaderSignup = new FXMLLoader(MainApplication.class.getResource("signup.fxml"));
        signup = new Scene(fxmlLoaderSignup.load(), 400, 600);
        FXMLLoader fxmlLoaderUserPage = new FXMLLoader(MainApplication.class.getResource("userPage.fxml"));
        userPage = new Scene(fxmlLoaderUserPage.load(), 400, 600);
        stage = mainStage;
        stage.setTitle("Barghi Food");
        stage.setMinWidth(400);
        stage.setMinHeight(640);
        stage.setScene(login);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}