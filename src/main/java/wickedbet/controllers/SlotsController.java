package wickedbet.controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

public class SlotsController {
    private final SceneController sceneController = new SceneController();

    public void goBack(ActionEvent event) throws IOException {
        sceneController.switchToMenu(event);
    }
}
