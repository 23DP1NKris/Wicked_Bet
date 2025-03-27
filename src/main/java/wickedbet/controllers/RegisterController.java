package wickedbet.controllers;

import wickedbet.models.User;
import wickedbet.services.RegisterService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private final RegisterService registerService = new RegisterService();
    private static User registeredUser;

    public static User getRegisteredUser() {
        return registeredUser;
    }

    public void switchToLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/javafx/login.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void register(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return;
        }

        User registeredUser = new User(username, password);
        registerService.registerUser(registeredUser);

        System.out.println("Username: " + registeredUser.getUsername() + "\n" + "Password: " + registeredUser.getPassword());
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
