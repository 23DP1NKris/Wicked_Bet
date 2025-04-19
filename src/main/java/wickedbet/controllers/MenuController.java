package wickedbet.controllers;

import javafx.event.ActionEvent;
import wickedbet.services.UserSessionService;
import wickedbet.utils.SceneManager;

import java.io.IOException;

public class MenuController {
    private final SceneManager sceneManager = new SceneManager();
    private final UserSessionService userSessionService = UserSessionService.getInstance();

    // switches to the login scene
    public void logOut(ActionEvent event) throws IOException {
        userSessionService.setLoggedIn(null); // sets the current/online user as offline
        sceneManager.switchToLogin(event);
    }

    // switches to the slots scene
    public void switchToSlots(ActionEvent event) throws IOException {
        sceneManager.switchToSlots(event);
    }

    // switches to the user's stats scene
    public void switchToStatistics(ActionEvent event) throws IOException {
        sceneManager.switchToStatistics(event);
    }

    // switches to the leaderboard scene
    public void switchToLeaderboard(ActionEvent event) throws IOException {
        sceneManager.switchToLeaderboard(event);
    }
}