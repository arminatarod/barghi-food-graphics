package com.example.barghifoodgraphics;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class loginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox captchaBox;
    @FXML
    private Pane captchaPane;
    public void forgotPressed() {

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
    public void loginPressed() {
        if (captchaBox.isSelected()) {
            //Main.core.login(usernameField.getText(), passwordField.getText());
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
}