package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Optional;

public class commentController {
    int selectedComment = -1;
    @FXML
    private VBox commentContainer;
    public void changeToUserPage() {
        MainApplication.stage.setScene(MainApplication.userPage);
    }
    public void addComment() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add comment");
        dialog.setHeaderText("Enter comment content:");
        ((GridPane)dialog.getDialogPane().getChildren().get(3)).getChildren().remove(1);
        ((GridPane)dialog.getDialogPane().getChildren().get(3)).getChildren().add(new TextArea());
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println(((TextArea) ((GridPane) dialog.getDialogPane().getChildren().get(3)).getChildren().get(1)).getText());
            //TODO: add comment
        }
        initialize();
    }
    public void editComment() {
        if (selectedComment == -1) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No comment selected.");
            a.setContentText("You must select a comment.");
            a.show();
        } else {
            //TODO: edit comment (if it's the same person)
        }
        initialize();
    }
    public void deleteComment() {
        if (selectedComment == -1) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No comment selected.");
            a.setContentText("You must select a comment.");
            a.show();
            return;
        } else {
            //TODO: delete comment (if it's the same person)
        }
        initialize();
    }
    public void initialize() {
        selectedComment = -1;
        commentContainer.getChildren().clear();
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
            commentBox.setStyle("-fx-background-color: rgb(100,100,100); -fx-background-radius: 10; -fx-cursor: hand;");
            commentBox.setPrefWidth(10000);
            commentBox.setPrefHeight(-1);
            commentBox.setPadding(new Insets(20));
            VBox.setMargin(commentBox, new Insets(20, 0, 20, 0));
            commentBox.getChildren().add(userLabel);
            commentBox.getChildren().add(contentLabel);
            int indexCopy = i;
            commentBox.setOnMouseClicked(mouseEvent -> {
                if (selectedComment != -1)
                    commentContainer.getChildren().get(selectedComment).setStyle("-fx-background-color: rgb(100,100,100); -fx-background-radius: 10; -fx-cursor: hand;");
                selectedComment = indexCopy;
                commentBox.setStyle("-fx-background-color: rgb(150,150,150); -fx-background-radius: 10; -fx-cursor: hand;");
            });
            commentContainer.getChildren().add(commentBox);
        }
    }
}