package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class commentController {

    @FXML
    private VBox commentContainer;
    public void changeToUserPage() {
        MainApplication.stage.setScene(MainApplication.userPage);
    }
    public void initialize() {
        //TODO: insert data
        for (int i = 0; i < 10; i++) {
            Label userLabel = new Label("User: " + i);
            userLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
            userLabel.setTextFill(Color.WHITE);
            Label contentLabel = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.    " + i);
            contentLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
            contentLabel.setTextFill(Color.WHITE);
            contentLabel.setWrapText(true);
            VBox commentBox = new VBox();
            commentBox.setStyle("-fx-background-color: rgb(100,100,100); -fx-background-radius: 10;");
            commentBox.setPrefWidth(10000);
            commentBox.setPrefHeight(-1);
            commentBox.setPadding(new Insets(20));
            VBox.setMargin(commentBox, new Insets(20, 0, 20, 0));
            commentBox.getChildren().add(userLabel);
            commentBox.getChildren().add(contentLabel);
            commentContainer.getChildren().add(commentBox);
        }
    }
}