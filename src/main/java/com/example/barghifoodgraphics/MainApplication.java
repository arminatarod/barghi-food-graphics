package com.example.barghifoodgraphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    static Stage stage;
    static Scene login, signup, userPage, forgotPassword;
    static FXMLLoader fxmlLoaderLogin, fxmlLoaderSignup, fxmlLoaderUserPage, fxmlLoaderForgotPassword;
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
        stage = mainStage;
        stage.setTitle("Barghi Food");
        stage.setMinWidth(400);
        stage.setMinHeight(640);
        stage.setScene(userPage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}