module com.example.barghifoodgraphics {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens com.example.barghifoodgraphics to javafx.fxml;
    exports com.example.barghifoodgraphics;
}