package wickedbet.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    private final SceneController sceneController = new SceneController();

    public void switchToLogin(ActionEvent event) throws IOException {
        sceneController.switchToLogin(event);
    }

    public void exit(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
