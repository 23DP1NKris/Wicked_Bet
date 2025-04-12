package wickedbet.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import wickedbet.models.User;
import wickedbet.services.UserSessionService;
import wickedbet.utils.SceneManager;

import java.io.IOException;

public class SlotsController {
    private final SceneManager sceneManager = new SceneManager();
    private final UserSessionService userSessionService = UserSessionService.getInstance();

    private User currentUser;

    @FXML
    private TextField betAmount;
    @FXML
    private Button changeBetButton;
    @FXML
    private Label balanceLabel;

    @FXML
    public void initialize() {
        changeBetButton.setVisible(false);
        betAmount.focusedProperty().addListener((obs, oldVal, newVal) -> {
            changeBetButton.setVisible(newVal);
        });

        currentUser = userSessionService.getLoggedIn();
        updateBalanceLabel();
    }

    public void goBack(ActionEvent event) throws IOException {
        sceneManager.switchToMenu(event);
    }

    public void switchToAddBalance(ActionEvent event) throws IOException {
        sceneManager.switchToAddBalance(event);
    }

    public void switchToSlotStats(ActionEvent event) throws IOException {
        sceneManager.switchToSlotStats(event);
    }

    private void updateBalanceLabel() {
        balanceLabel.setText(String.format("Balance: %.2f €", currentUser.getBalance()));
    }
}