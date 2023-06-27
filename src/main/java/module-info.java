module com.example.barghifoodgraphics {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.barghifoodgraphics to javafx.fxml;
    exports com.example.barghifoodgraphics;
}