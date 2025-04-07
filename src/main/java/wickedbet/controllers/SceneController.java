package wickedbet.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneController {

    private void switchScene(String fxmlFile, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/javafx/"+fxmlFile)));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }

    public void switchToLogin(ActionEvent event) throws IOException {
        switchScene("login.fxml", event);
    }

    public void switchToRegister(ActionEvent event) throws IOException {
        switchScene("register.fxml", event);
    }

    public void switchToMenu(ActionEvent event) throws IOException {
        switchScene("menu.fxml", event);
    }

    public void switchToSlots(ActionEvent event) throws IOException {
        switchScene("slots.fxml", event);
    }

    public void switchToStatistics(ActionEvent event) throws IOException {
        switchScene("statistics.fxml", event);
    }

    public void switchToLeaderboard(ActionEvent event) throws IOException {
        switchScene("leaderboard.fxml", event);
    }
}
