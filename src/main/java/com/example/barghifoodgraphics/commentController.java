package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.HashSet;
import java.util.Optional;

public class commentController {
    Scene previousScene;
    boolean isAdmin = false, isFood = false;
    int objectID;
    HashSet<Integer> comments;
    int selectedComment = -1;
    @FXML
    private VBox commentContainer;
    @FXML
    private Button greenButton, yellowButton, redButton;
    public void backClick() {
        MainApplication.stage.setScene(previousScene);
    }
    public void greenClick() {
        if (isAdmin) {
            if (selectedComment == -1) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No comment selected.");
                a.setContentText("You must select a comment.");
                a.show();
            } else {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Add response");
                dialog.setHeaderText("Enter your response:");
                ((GridPane) dialog.getDialogPane().getChildren().get(3)).getChildren().remove(1);
                ((GridPane) dialog.getDialogPane().getChildren().get(3)).getChildren().add(new TextArea());
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent())
                    MainApplication.core.addResponse(Integer.parseInt(((Label)((VBox)commentContainer.getChildren().get(selectedComment)).getChildren().get(0)).getText().substring(4)), ((TextArea)((GridPane) dialog.getDialogPane().getChildren().get(3)).getChildren().get(1)).getText());
            }
        } else {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add a comment");
            dialog.setHeaderText("Enter your comment:");
            ((GridPane) dialog.getDialogPane().getChildren().get(3)).getChildren().remove(1);
            ((GridPane) dialog.getDialogPane().getChildren().get(3)).getChildren().add(new TextArea());
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent())
                MainApplication.core.addComment(((TextArea)((GridPane) dialog.getDialogPane().getChildren().get(3)).getChildren().get(1)).getText());
        }
        initialize();
    }
    public void yellowClick() {
        if (selectedComment == -1) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No comment selected.");
            a.setContentText("You must select a comment.");
            a.show();
        } else if (isAdmin) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Edit response");
            dialog.setHeaderText("Enter your response:");
            ((GridPane) dialog.getDialogPane().getChildren().get(3)).getChildren().remove(1);
            ((GridPane) dialog.getDialogPane().getChildren().get(3)).getChildren().add(new TextArea());
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent())
                MainApplication.core.editResponse(Integer.parseInt(((Label)((VBox)commentContainer.getChildren().get(selectedComment)).getChildren().get(0)).getText().substring(4)), ((TextArea)((GridPane) dialog.getDialogPane().getChildren().get(3)).getChildren().get(1)).getText());
        } else {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Edit comment");
            dialog.setHeaderText("Enter your comment:");
            ((GridPane) dialog.getDialogPane().getChildren().get(3)).getChildren().remove(1);
            ((GridPane) dialog.getDialogPane().getChildren().get(3)).getChildren().add(new TextArea());
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent())
                MainApplication.core.editComment(Integer.parseInt(((Label)((VBox)commentContainer.getChildren().get(selectedComment)).getChildren().get(0)).getText().substring(4)), ((TextArea)((GridPane) dialog.getDialogPane().getChildren().get(3)).getChildren().get(1)).getText());
        }
        initialize();
    }
    public void redClick() {
        if (selectedComment == -1) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No comment selected.");
            a.setContentText("You must select a comment.");
            a.show();
            return;
        } else if (isAdmin) {
            //TODO: delete response
        } else {
            //TODO: delete comment
        }
        initialize();
    }
    public void initialize() {
        selectedComment = -1;
        if(MainApplication.core.loggedInAdmin == -1)
            isFood = true;
        else
        {
            isAdmin = true;
            previousScene = MainApplication.adminPageTwo;
        }
        if (isAdmin) {
            greenButton.setText("Add response");
            yellowButton.setText("Edit response");
            redButton.setText("Delete response");
        }
        commentContainer.getChildren().clear();
        if (isFood)
            comments = MainApplication.core.foods.get(objectID).getComments();
        else
            comments = MainApplication.core.restaurants.get(MainApplication.core.selectedRestaurant).getComments();
        int index = 0;
        for (int i : comments) {
            Label idLabel = new Label("ID: " + i);
            idLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
            idLabel.setTextFill(Color.color(0.8,0.8,0.8));
            Label userLabel = new Label("User: " + MainApplication.core.accounts.get(MainApplication.core.comments.get(i).getCommenterID()).getUsername());
            userLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
            userLabel.setTextFill(Color.WHITE);
            Label contentLabel = new Label(MainApplication.core.comments.get(i).getContent());
            contentLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
            contentLabel.setTextFill(Color.WHITE);
            contentLabel.setWrapText(true);
            VBox.setMargin(contentLabel, new Insets(10, 0, 0, 0));
            Label answerTitleLabel = new Label("Response: ");
            answerTitleLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
            answerTitleLabel.setTextFill(Color.color(0.8,0.8,0.8));
            VBox.setMargin(answerTitleLabel, new Insets(20, 0, 0, 0));
            Label answerLabel = new Label(MainApplication.core.comments.get(i).getAnswer().isEmpty()? "No response submitted." : MainApplication.core.comments.get(i).getAnswer());
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
            commentBox.getChildren().add(idLabel);
            commentBox.getChildren().add(userLabel);
            commentBox.getChildren().add(contentLabel);
            commentBox.getChildren().add(answerTitleLabel);
            commentBox.getChildren().add(answerLabel);
            int indexCopy = index++;
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