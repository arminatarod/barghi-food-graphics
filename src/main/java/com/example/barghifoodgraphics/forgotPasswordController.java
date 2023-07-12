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
        String result = MainApplication.core.checkRecoveryQuestionAnswer(((loginController)MainApplication.fxmlLoaderLogin.getController()).getUsername(), answer.getText());
        Alert a;
        if (result == null) {
            a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Invalid request");
            a.setContentText("The username is invalid and/or the answer is incorrect.");
        } else {
            a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Your password:");
            a.setContentText(result);
        }
        a.show();
    }
    public void initialize() {
        question.setText(questionText);
    }
}