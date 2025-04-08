package wickedbet.controllers;

import javafx.event.ActionEvent;
import wickedbet.utils.SceneManager;

import java.io.IOException;

public class SlotsController {
    private final SceneManager sceneManager = new SceneManager();

    public void goBack(ActionEvent event) throws IOException {
        sceneManager.switchToMenu(event);
    }
}
