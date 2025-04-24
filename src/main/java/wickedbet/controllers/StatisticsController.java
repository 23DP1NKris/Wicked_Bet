package wickedbet.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import wickedbet.models.User;
import wickedbet.services.UserSessionService;
import wickedbet.utils.SceneManager;

import java.io.IOException;

public class StatisticsController {
    private final SceneManager sceneManager = new SceneManager();
    private final UserSessionService userSessionService = UserSessionService.getInstance();

    @FXML
    private Label usernameLabel, balanceLabel, biggestBetLabel, biggestWinLabel, registrationDateLabel;

    // displays and updates all the values when the scene is opened
    @FXML
    public void initialize() {
        User currentUser = userSessionService.getLoggedIn();

        usernameLabel.setText(currentUser.getUsername());
        balanceLabel.setText(String.format("%.2f €", currentUser.getBalance()));
        biggestBetLabel.setText(String.format("%.2f €", currentUser.getBiggestBet()));
        biggestWinLabel.setText(String.format("%.2f €", currentUser.getBiggestWin()));
        registrationDateLabel.setText(String.valueOf(currentUser.getRegistrationDate()));
    }

    // switches to the menu scene
    public void goBack(ActionEvent event) throws IOException {
        sceneManager.switchToMenu(event);
    }
}