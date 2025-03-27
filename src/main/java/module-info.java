module com.example.wickedbet {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens wickedbet.models to com.google.gson, javafx.fxml;
    opens wickedbet.controllers to javafx.fxml;

    exports wickedbet;
    exports wickedbet.controllers;
    exports wickedbet.services;
}
