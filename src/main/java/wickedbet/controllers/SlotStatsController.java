package wickedbet.controllers;

import javafx.event.ActionEvent;
import wickedbet.utils.SceneManager;

import java.io.IOException;

public class SlotStatsController {
    private final SceneManager sceneManager = new SceneManager();

    // switches to the slots scene
    public void goBack(ActionEvent event) throws IOException {
        sceneManager.switchToSlots(event);
    }
}
