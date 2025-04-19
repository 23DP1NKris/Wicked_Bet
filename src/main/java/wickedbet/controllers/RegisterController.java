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

    // switches to the login scene
    public void switchToLogin(ActionEvent event) throws IOException {
        sceneManager.switchToLogin(event);
    }

    // registers
    public void register(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim(); // gets the username from the text field and trims it
        String password = passwordField.getText().trim(); // gets the password from the password field and trims it

        if (registerService.validationCheck(username, password)) { // validation
            User registeredUser = new User(username, password); // creates a temporary object with the password and username
            registerService.registerUser(registeredUser); // registers the user

            sceneManager.switchToMenu(event); // switches to the menu scene
        }
    }
}