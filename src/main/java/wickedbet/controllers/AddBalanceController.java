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
import java.math.BigDecimal;

public class AddBalanceController {
    private final SceneManager sceneManager = new SceneManager();
    private final JsonService jsonService = new JsonService();
    private final UserSessionService userSessionService = UserSessionService.getInstance();

    private User currentUser;

    @FXML
    private Label balanceLabel;

    @FXML
    public void initialize() {
        currentUser = userSessionService.getLoggedIn(); // creates an object of the online user
        updateBalanceLabel(); // shows the initial balance
    }

    public void goBack(ActionEvent event) throws IOException { // goes back to the slots scene
        sceneManager.switchToSlots(event);
    }

    public void addFiveBalance(ActionEvent event) { // adds 5 euros to the balance
        addBalance(BigDecimal.valueOf(5));
    }

    public void addTenBalance(ActionEvent event) { // adds 10 euros to the balance
        addBalance(BigDecimal.valueOf(10));
    }

    public void addTwentyBalance(ActionEvent event) { // adds 20 euros to the balance
        addBalance(BigDecimal.valueOf(20));
    }

    public void addFiftyBalance(ActionEvent event) { // adds 50 euros to the balance
        addBalance(BigDecimal.valueOf(50));
    }

    public void addHundredBalance(ActionEvent event) { // adds 100 euros to the balance
        addBalance(BigDecimal.valueOf(100));
    }

    public void resetBalance(ActionEvent event) { // sets the balance as 0
        currentUser.setBalance(BigDecimal.ZERO); // sets the user's balance as 0
        updateBalanceLabel(); // updates the balance displayed on the scene
        saveUpdatedUser();  // saves the user to json
    }

    private void addBalance(BigDecimal amount) {
        if (currentUser != null) {
            currentUser.addBalance(amount); // adds the given amount of balance to the user
            updateBalanceLabel(); // updates the balance displayed on the scene
            saveUpdatedUser(); // saves the user to json
        }
    }

    private void updateBalanceLabel() {
        balanceLabel.setText(String.format("Balance: %.2f €", currentUser.getBalance().doubleValue())); // outputs the user's balance as "Balance: x €" with only 2 numbers after the separator
    }

    // overwrites the user's object with new data in json
    private void saveUpdatedUser() {
        List<User> users = jsonService.loadUsers(); // loads all users from json into a list
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(currentUser.getUsername())) {  // checks the online user's object in json
                users.set(i, currentUser); // selects the user
                break;
            }
        }
        jsonService.writeUsersToFile(users); // writes the user in json
    }
}