package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.Optional;

public class editController {
    @FXML HBox BoxOne;
    @FXML HBox BoxTwo;
    @FXML TextField foodNameTextField, foodPriceTextField, discountTExtField;
    public void initialize()
    {
        BoxOne = new HBox();
        BoxTwo = new HBox();
        foodPriceTextField = new TextField();
        foodNameTextField = new TextField();
        discountTExtField = new TextField();
    }
    public void ok()
    {
        if(foodNameTextField.getText().isBlank() || foodPriceTextField.getText().isBlank() || discountTExtField.getText().isBlank())
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Missing field");
            a.setContentText("You must complete all the fields.");
            a.show();
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Confirmation");
            a.setContentText("Are you sure?");
            Optional<ButtonType> result = a.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
            {
                MainApplication.stage.setScene(MainApplication.adminPageTwo);
            }
        }
    }
}
