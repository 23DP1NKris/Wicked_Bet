package wickedbet.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerText;

    public LoginController(TextField username) {
        this.username = username;
        this.password = new PasswordField();
    }

    @FXML
    private void loginButtonAction(ActionEvent event) {

    }

    @FXML
    private void registerTextAction(ActionEvent event) {

    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
