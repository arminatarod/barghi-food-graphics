package com.example.barghifoodgraphics;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class loginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox captchaBox;
    @FXML
    private StackPane captchaPane;
    public void forgotPressed() {
        String result = MainApplication.core.forgetPasswordPressed(usernameField.getText());
        if (result == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Invalid username");
            a.setContentText("Please enter a valid username.");
            a.show();
        } else {
            Stage forgotPasswordStage = new Stage();
            forgotPasswordStage.setTitle("Forgot password");
            forgotPasswordStage.setMinWidth(400);
            forgotPasswordStage.setMinHeight(540);
            forgotPasswordStage.setScene(MainApplication.forgotPassword);
            forgotPasswordController.username = usernameField.getText();
            forgotPasswordController.questionText = result;
            ((forgotPasswordController) MainApplication.fxmlLoaderForgotPassword.getController()).initialize();
            forgotPasswordStage.show();
        }
    }
    public void captchaPressed() {
        if (captchaBox.isSelected()) {
            captchaPane.setOpacity(0);
            FadeTransition ft = new FadeTransition(Duration.millis(500), captchaPane);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();
        }
    }
    public void loginPressed() throws IOException {
        if (captchaBox.isSelected()) {
            int result = MainApplication.core.login(usernameField.getText(), passwordField.getText());
            if (result == 0) {
                MainApplication.fxmlLoaderUserPage = new FXMLLoader(MainApplication.class.getResource("userPage.fxml"));
                MainApplication.userPage = new Scene(MainApplication.fxmlLoaderUserPage.load(), 400, 600);
                MainApplication.stage.setScene(MainApplication.userPage);
            } else if (result == 1) {
                MainApplication.stage.setScene(MainApplication.adminPageOne);
            } else if (result == 3) {
                MainApplication.stage.setScene(MainApplication.deliverymanPage);
            } else if (result == 4) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Incorrect password!");
                a.setContentText("The entered password is not correct.");
                a.show();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Invalid username!");
                a.setContentText("No account with this username exists.");
                a.show();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Captcha error!");
            a.setContentText("You must confirm that you are not a robot.");
            a.show();
        }
    }
    public void changeToSignup() {
        MainApplication.stage.setScene(MainApplication.signup);
    }
    public String getUsername() {
        return usernameField.getText();
    }
}