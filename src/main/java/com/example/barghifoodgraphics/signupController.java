package com.example.barghifoodgraphics;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class signupController {
    @FXML
    private TextField usernameField, recoveryQuestion, recoveryAnswer;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox typeChooser;
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
    public void signupPressed() {
        if (captchaBox.isSelected()) {
            /*if (typeChooser.getValue().equals("User"))
                Main.core.addUser(usernameField.getText(), passwordField.getText(), recoveryQuestion.getText(), recoveryAnswer.getText());
            else if (typeChooser.getValue().equals("Administrator"))
                Main.core.addAdmin(usernameField.getText(), passwordField.getText(), recoveryQuestion.getText(), recoveryAnswer.getText());
            else
                Main.core.addDelivery(usernameField.getText(), passwordField.getText(), recoveryQuestion.getText(), recoveryAnswer.getText());*/
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