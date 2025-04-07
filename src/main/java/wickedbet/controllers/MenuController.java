package wickedbet.controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

public class MenuController {
    private final SceneController sceneController = new SceneController();

    public void logOut(ActionEvent event) throws IOException {
        sceneController.switchToLogin(event);
    }

    public void switchToSlots(ActionEvent event) throws IOException {
        sceneController.switchToSlots(event);
    }

    public void switchToStatistics(ActionEvent event) throws IOException {
        sceneController.switchToStatistics(event);
    }

    public void switchToLeaderboard(ActionEvent event) throws IOException {
        sceneController.switchToLeaderboard(event);
    }
}