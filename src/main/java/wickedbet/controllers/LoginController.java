package wickedbet.controllers;

import wickedbet.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final LoginService loginService = new LoginService();
    private final SceneController sceneController = new SceneController();

    @FXML
    public void login(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username or password cannot be empty.");
            return;
        }

        if (loginService.loginUser(username, password)) {
            sceneController.switchToMenu(event);
        } else {
            System.out.println("Invalid login credentials.");
        }
    }

    @FXML
    public void switchToRegister(ActionEvent event) throws IOException {
        sceneController.switchToRegister(event);
    }
}
