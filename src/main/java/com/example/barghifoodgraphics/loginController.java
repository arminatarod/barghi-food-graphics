package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class loginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    public void loginPressed() {
        Main.core.login(usernameField.getText(), passwordField.getText());
    }
    public void changeToSignup() {
        MainApplication.stage.setScene(MainApplication.signup);
    }
}