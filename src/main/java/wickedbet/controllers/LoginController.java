package wickedbet.controllers;

import wickedbet.services.JsonService;
import wickedbet.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private final LoginService loginService = new LoginService();
    private final SceneController sceneController = new SceneController();

    public void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username or password cannot be empty.");
        }

        if (loginService.loginUser(username, password)) {
            // switch to menu page
        } else {
           System.out.println("Invalid details. Try again.");
        }
    }

    public void switchToRegister(ActionEvent event) throws IOException {
        sceneController.switchToRegister(event);
    }

}