package wickedbet.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private Button registerButton;

    @FXML
    private Button logInText;

    public RegisterController(TextField username, PasswordField password) {

    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
