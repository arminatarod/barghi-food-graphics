package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class signupController {
    @FXML
    private TextField usernameField, recoveryQuestion, recoveryAnswer;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox typeChooser;
    public void signupPressed() {
        if (typeChooser.getValue().equals("User"))
            Main.core.addUser(usernameField.getText(), passwordField.getText(), recoveryQuestion.getText(), recoveryAnswer.getText());
        else if (typeChooser.getValue().equals("Administrator"))
            Main.core.addAdmin(usernameField.getText(), passwordField.getText(), recoveryQuestion.getText(), recoveryAnswer.getText());
        else
            Main.core.addDelivery(usernameField.getText(), passwordField.getText(), recoveryQuestion.getText(), recoveryAnswer.getText());
    }
    public void changeToLogin() {
        MainApplication.stage.setScene(MainApplication.login);
    }
    public void initialize() {
        typeChooser.getItems().addAll("User", "Administrator", "Deliveryman");
        typeChooser.setValue("User");
    }
}