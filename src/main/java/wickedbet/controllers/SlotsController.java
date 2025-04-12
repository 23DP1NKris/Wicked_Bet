package wickedbet.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import wickedbet.alerts.UserAlerts;
import wickedbet.models.User;
import wickedbet.services.BetService;
import wickedbet.services.UserSessionService;
import wickedbet.utils.SceneManager;
import wickedbet.services.SpinService;

import java.io.IOException;

public class SlotsController {
    private final SceneManager sceneManager = new SceneManager();
    private final UserSessionService userSessionService = UserSessionService.getInstance();
    private final UserAlerts betAlerts = new UserAlerts();
    private final BetService betService = new BetService();
    private final SpinService spinService = new SpinService();

    private User currentUser;

    @FXML
    private TextField betAmount;
    @FXML
    private Button changeBetButton;
    @FXML
    private Label balanceLabel;


    public double bet = 0.10;

    @FXML
    public void initialize() {
        changeBetButton.setManaged(false);
        changeBetButton.setVisible(false);

        betAmount.textProperty().addListener((obs, oldVal, newVal) -> {
            boolean show = !newVal.trim().isEmpty();
            changeBetButton.setManaged(show);
            changeBetButton.setVisible(show);
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

    public void changeBet(ActionEvent event) {
        String inputBet = betAmount.getText().trim();
        double balance = currentUser.getBalance();

        if (betService.validationCheck(inputBet, balance)) {
            this.bet = Double.parseDouble(inputBet);
            System.out.println("New bet: " + bet); // New bet: x (not lower than 0.1 and not higher than the user's bal)

            javafx.application.Platform.runLater(() -> {
                changeBetButton.setVisible(false);
                changeBetButton.setManaged(false);
            });
        }
    }

    public void startSpin(ActionEvent event) throws IOException {
        if (currentUser.getBalance() < bet) {
            betAlerts.showAlert("Not enough balance", "You don't have enough balance to place this bet!");
            return;
        }

        spinService.spin(bet);
    }
}
