package wickedbet.controllers;

import javafx.event.ActionEvent;
import wickedbet.services.UserSessionService;
import wickedbet.utils.SceneManager;

import java.io.IOException;

public class MenuController {
    private final SceneManager sceneManager = new SceneManager();
    private final UserSessionService userSessionService = UserSessionService.getInstance();

    public void logOut(ActionEvent event) throws IOException {
        userSessionService.setLoggedIn(null);
        sceneManager.switchToLogin(event);
    }

    public void switchToSlots(ActionEvent event) throws IOException {
        sceneManager.switchToSlots(event);
    }

    public void switchToStatistics(ActionEvent event) throws IOException {
        sceneManager.switchToStatistics(event);
    }

    public void switchToLeaderboard(ActionEvent event) throws IOException {
        sceneManager.switchToLeaderboard(event);
    }
}