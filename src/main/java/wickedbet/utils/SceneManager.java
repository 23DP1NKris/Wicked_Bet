package wickedbet.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {

    // takes the fxml file's name and adds it to the path, as well as gives all the attributes to the scene and stage
    private void switchScene(String fxmlFile, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/javafx/" + fxmlFile)));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }

    // login scene
    public void switchToLogin(ActionEvent event) throws IOException {
        switchScene("login.fxml", event);
    }

    // register scene
    public void switchToRegister(ActionEvent event) throws IOException {
        switchScene("register.fxml", event);
    }

    // menu scene
    public void switchToMenu(ActionEvent event) throws IOException {
        switchScene("menu.fxml", event);
    }

    // slots/game scene
    public void switchToSlots(ActionEvent event) throws IOException {
        switchScene("slots.fxml", event);
    }

    // stats scene
    public void switchToStatistics(ActionEvent event) throws IOException {
        switchScene("statistics.fxml", event);
    }

    // leaderboard scene
    public void switchToLeaderboard(ActionEvent event) throws IOException {
        switchScene("leaderboard.fxml", event);
    }

    // add balance scene
    public void switchToAddBalance(ActionEvent event) throws IOException {
        switchScene("addbalance.fxml", event);
    }

    // slot stats scene
    public void switchToSlotStats(ActionEvent event) throws IOException {
        switchScene("slotstats.fxml", event);
    }
}
