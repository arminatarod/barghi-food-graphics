package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import java.util.Optional;

public class addDiscountController {
    @FXML TextField discountPercentage, timestep;
    public void initialize(){}
    public void add()
    {
        if(discountPercentage.getText().isBlank() || timestep.getText().isBlank())
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
                MainApplication.core.discountFood(MainApplication.core.selectedFood, Integer.parseInt(discountPercentage.getText()), Integer.parseInt(timestep.getText()));
                ((adminController2)MainApplication.fxmlLoaderAdminPageTwo.getController()).initialize();
                MainApplication.stage.setScene(MainApplication.adminPageTwo);
            }
        }
    }
}
