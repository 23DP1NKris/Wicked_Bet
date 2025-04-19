package wickedbet.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import wickedbet.utils.SceneManager;

import java.io.IOException;

public class StartController {
    private final SceneManager sceneManager = new SceneManager();

    // switches to the login scene
    public void switchToLogin(ActionEvent event) throws IOException {
        sceneManager.switchToLogin(event);
    }

    // closes the program when clicking on "exit"
    public void exit(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
