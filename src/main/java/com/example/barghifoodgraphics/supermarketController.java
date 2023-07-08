package com.example.barghifoodgraphics;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class supermarketController {
    public void changeToUserPage() {
        MainApplication.stage.setScene(MainApplication.userPage);
    }
}