package wickedbet.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import wickedbet.services.LoginService;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private final LoginService loginService = new LoginService();
    private final SceneController sceneController = new SceneController();

    public void login(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (loginService.loginUser(username, password)) {
            sceneController.switchToMenu(event);
        }
    }

    @FXML
    private void switchToRegister(ActionEvent event) throws IOException {
        sceneController.switchToRegister(event);
    }

}