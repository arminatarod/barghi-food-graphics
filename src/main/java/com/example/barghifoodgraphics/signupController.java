package com.example.barghifoodgraphics;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public class signupController {
    @FXML
    private TextField usernameField, recoveryQuestion, recoveryAnswer;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> typeChooser;
    @FXML
    private CheckBox captchaBox;
    @FXML
    private StackPane captchaPane;
    public void captchaPressed() {
        if (captchaBox.isSelected()) {
            captchaPane.setOpacity(0);
            FadeTransition ft = new FadeTransition(Duration.millis(500), captchaPane);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();
        }
    }
    private void checkSignup(int result) {
        if (result == 0) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Registration successful");
            a.setContentText("Your account has been registered successfully.");
            a.show();
        } else if (result == 1) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Not logged out!");
            a.setContentText("You must be logged out to sign up.");
            a.show();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Username taken!");
            a.setContentText("Please select another username.");
            a.show();
        }
    }
    public void signupPressed() {
        if (captchaBox.isSelected()) {
            if (typeChooser.getValue().equals("User")) {
                int result = MainApplication.core.addUser(usernameField.getText(), passwordField.getText(), recoveryQuestion.getText(), recoveryAnswer.getText());
                checkSignup(result);
            } else if (typeChooser.getValue().equals("Administrator")) {
                int result = MainApplication.core.addAdmin(usernameField.getText(), passwordField.getText(), recoveryQuestion.getText(), recoveryAnswer.getText());
                checkSignup(result);
            } else {
                int result = MainApplication.core.addDeliveryman(usernameField.getText(), passwordField.getText(), recoveryQuestion.getText(), recoveryAnswer.getText());
                checkSignup(result);
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Captcha error!");
            a.setContentText("You must confirm that you are not a robot.");
            a.show();
        }
    }
    public void changeToLogin() {
        MainApplication.stage.setScene(MainApplication.login);
    }
    public void initialize() {
        typeChooser.getItems().addAll("User", "Administrator", "Deliveryman");
        typeChooser.setValue("User");
    }
}