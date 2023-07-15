package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.Optional;

public class addRestaurantController {
    @FXML TextField restaurantName, restaurantType, restaurantLocationId;
    public void initialize(){}
    public void add()
    {
        if(restaurantName.getText().isBlank() || restaurantType.getText().isBlank() || restaurantLocationId.getText().isBlank())
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
                MainApplication.core.addRestaurant(restaurantName.getText(), restaurantType.getText(), Integer.parseInt(restaurantLocationId.getText()));
                MainApplication.stage.setScene(MainApplication.adminPageOne);
                adminController tmp = MainApplication.fxmlLoaderAdminPageOne.getController();
                tmp.initialize();
            }
        }
    }
}
