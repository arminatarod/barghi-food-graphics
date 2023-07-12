package com.example.barghifoodgraphics;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        String result = "Where are you from?"/*Main.core.forgetPasswordPressed(usernameField.getText())*/;
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
    public String getUsername() {
        return usernameField.getText();
    }
}