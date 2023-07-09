module com.example.barghifoodgraphics {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;


    opens com.example.barghifoodgraphics to javafx.fxml;
    exports com.example.barghifoodgraphics;
}