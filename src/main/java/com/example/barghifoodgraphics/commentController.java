package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Optional;

public class commentController {
    int selectedComment = -1;
    @FXML
    private VBox commentContainer;
    public void addComment() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add a comment");
        dialog.setHeaderText("Enter your comment:");
        ((GridPane)dialog.getDialogPane().getChildren().get(3)).getChildren().remove(1);
        ((GridPane)dialog.getDialogPane().getChildren().get(3)).getChildren().add(new TextArea());
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println(((TextArea) ((GridPane) dialog.getDialogPane().getChildren().get(3)).getChildren().get(1)).getText());
            //TODO: add the comment
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
            VBox.setMargin(contentLabel, new Insets(10, 0, 0, 0));
            Label answerTitleLabel = new Label("Response: ");
            answerTitleLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
            answerTitleLabel.setTextFill(Color.color(0.8,0.8,0.8));
            VBox.setMargin(answerTitleLabel, new Insets(20, 0, 0, 0));
            Label answerLabel = new Label("No response submitted.");
            answerLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
            answerLabel.setTextFill(Color.color(0.8,0.8,0.8));
            answerLabel.setWrapText(true);
            VBox.setMargin(answerLabel, new Insets(10, 0, 0, 0));
            VBox commentBox = new VBox();
            commentBox.setStyle("-fx-background-color: rgb(100,100,100); -fx-background-radius: 10; -fx-cursor: hand;");
            commentBox.setPrefWidth(10000);
            commentBox.setPrefHeight(-1);
            commentBox.setPadding(new Insets(20));
            VBox.setMargin(commentBox, new Insets(20, 0, 20, 0));
            commentBox.getChildren().add(userLabel);
            commentBox.getChildren().add(contentLabel);
            commentBox.getChildren().add(answerTitleLabel);
            commentBox.getChildren().add(answerLabel);
            int indexCopy = i;
            commentBox.setOnMouseClicked(mouseEvent -> {
                if (selectedComment != -1)
                    commentContainer.getChildren().get(selectedComment).setStyle("-fx-background-color: rgb(100,100,100); -fx-background-radius: 10; -fx-cursor: hand;");
                selectedComment = indexCopy;
                commentBox.setStyle("-fx-background-color: rgb(50,50,50); -fx-background-radius: 10; -fx-cursor: hand;");
            });
            commentContainer.getChildren().add(commentBox);
        }
    }
}