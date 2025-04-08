package wickedbet.controllers;

import wickedbet.models.User;
import wickedbet.services.RegisterService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import wickedbet.utils.SceneManager;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private final RegisterService registerService = new RegisterService();
    private final SceneManager sceneManager = new SceneManager();

    public void switchToLogin(ActionEvent event) throws IOException {
        sceneManager.switchToLogin(event);
    }

    public void register(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (registerService.validationCheck(username, password)) {
            User registeredUser = new User(username, password);
            registerService.registerUser(registeredUser);

            sceneManager.switchToMenu(event);
        }
    }
}