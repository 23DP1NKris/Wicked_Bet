module com.example.wickedbet {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;

    opens wickedbet.models to com.google.gson, javafx.fxml;
    opens wickedbet.controllers to javafx.fxml;

    exports wickedbet;
    exports wickedbet.controllers;
    exports wickedbet.services;
    exports wickedbet.utils;
    opens wickedbet.utils to javafx.fxml;
    opens wickedbet.services to javafx.fxml;
}
