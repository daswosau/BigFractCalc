module com.example.damnbignumb {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.damnbignumb to javafx.fxml;
    exports com.example.damnbignumb;
}