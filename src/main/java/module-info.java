module com.example.barghifoodgraphics {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.datatype.jsr310;


    opens com.example.barghifoodgraphics to javafx.fxml;
    exports com.example.barghifoodgraphics;
}