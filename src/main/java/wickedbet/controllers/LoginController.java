package wickedbet.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import wickedbet.services.LoginService;
import wickedbet.utils.SceneManager;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private final LoginService loginService = new LoginService();
    private final SceneManager sceneManager = new SceneManager();

    public void login(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim(); // gets the username from the text field and trims it
        String password = passwordField.getText().trim(); // gets the password from the password field and trims it

        // if the validation and credentials are valid the user gets logged in
        if (loginService.loginUser(username, password)) {
            sceneManager.switchToMenu(event); // switches to the menu scene
        }
    }

    @FXML // switches to the register scene
    private void switchToRegister(ActionEvent event) throws IOException {
        sceneManager.switchToRegister(event);
    }

}