module com.example.wickedbet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens wickedbet to javafx.fxml;
    exports wickedbet;
    exports wickedbet.controllers;
    opens wickedbet.controllers to javafx.fxml;
}