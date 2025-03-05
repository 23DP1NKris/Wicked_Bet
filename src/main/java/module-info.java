module com.example.wickedbet {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wickedbet to javafx.fxml;
    exports com.example.wickedbet;
}