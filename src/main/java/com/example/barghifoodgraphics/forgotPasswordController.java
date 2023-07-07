package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class forgotPasswordController {
    static String username, questionText;
    @FXML
    Label question;
    @FXML
    TextField answer;
    public void submit() {
        //String result = Main.core.checkRecoveryQuestionAnswer(((loginController)MainApplication.fxmlLoaderLogin.getController()).getUsername(), answer.getText());
        String result = "ASDFASDF";
        if (result == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Invalid username");
            a.setContentText("Please enter a valid username.");
            a.show();
        } else {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Your password:");
            a.setContentText(result);
            a.show();
        }
    }
    public void initialize() {
        question.setText(questionText);
    }
}