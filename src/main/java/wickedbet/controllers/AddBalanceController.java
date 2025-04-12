package wickedbet.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import wickedbet.models.User;
import wickedbet.services.JsonService;
import wickedbet.services.UserSessionService;
import wickedbet.utils.SceneManager;

import java.io.IOException;
import java.util.List;

public class AddBalanceController {
    private final SceneManager sceneManager = new SceneManager();
    private final JsonService jsonService = new JsonService();
    private final UserSessionService userSessionService = UserSessionService.getInstance();

    private User currentUser;

    @FXML
    private Label balanceLabel;

    @FXML
    public void initialize() {
        currentUser = userSessionService.getLoggedIn();
        updateBalanceLabel();
    }

    public void goBack(ActionEvent event) throws IOException {
        sceneManager.switchToSlots(event);
    }

    public void addFiveBalance(ActionEvent event) {
        addBalance(5);
    }

    public void addTenBalance(ActionEvent event) {
        addBalance(10);
    }

    public void addTwentyBalance(ActionEvent event) {
        addBalance(20);
    }

    public void addFiftyBalance(ActionEvent event) {
        addBalance(50);
    }

    public void addHundredBalance(ActionEvent event) {
        addBalance(100);
    }

    public void resetBalance(ActionEvent event) {
        currentUser.setBalance(0);
        updateBalanceLabel();
        saveUpdatedUser();
    }

    private void addBalance(double amount) {
        if (currentUser != null) {
            currentUser.addBalance(amount);
            updateBalanceLabel();
            saveUpdatedUser();
        }
    }

    private void updateBalanceLabel() {
        balanceLabel.setText(String.format("Balance: %.2f €", currentUser.getBalance()));
    }

    private void saveUpdatedUser() {
        List<User> users = jsonService.loadUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(currentUser.getUsername())) {
                users.set(i, currentUser);
                break;
            }
        }
        jsonService.writeUsersToFile(users);
    }
}
