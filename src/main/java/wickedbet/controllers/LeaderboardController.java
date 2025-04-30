package wickedbet.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import wickedbet.alerts.UserAlerts;
import wickedbet.models.LeaderboardUser;
import wickedbet.models.User;
import wickedbet.services.JsonService;
import wickedbet.utils.SceneManager;
import wickedbet.utils.TypeManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class LeaderboardController implements Initializable {
    private final JsonService jsonService = new JsonService();
    private final TypeManager typeManager = new TypeManager();
    private final SceneManager sceneManager = new SceneManager();
    private final UserAlerts userAlerts = new UserAlerts();

    @FXML
    private TableView<LeaderboardUser> leaderboard;
    @FXML
    private TableColumn<LeaderboardUser, String> usernameColumn, biggestBetColumn, biggestWinColumn;
    @FXML
    private TextField minBiggestBet, maxBiggestBet, minBiggestWin, maxBiggestWin, usernameSearch;
    @FXML
    private Button filterButton, searchButton;

    private List<User> allUsers;

    // switches back to the menu scene
    public void goBack(ActionEvent event) throws IOException {
        sceneManager.switchToMenu(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allUsers = jsonService.loadUsers(); // loads all users from json

        // assigns the cell values
        usernameColumn.setCellValueFactory(cell -> cell.getValue().getUsernameProperty());
        biggestBetColumn.setCellValueFactory(cell -> cell.getValue().getBiggestBetProperty());
        biggestWinColumn.setCellValueFactory(cell -> cell.getValue().getBiggestWinProperty());

        // show everyone initially
        leaderboard.setItems(typeManager.toLeaderboard(allUsers));
    }

    public void applyFilter(ActionEvent event) {
        // parses inputs
        BigDecimal minBet = parseDecimal(minBiggestBet.getText());
        BigDecimal maxBet = parseDecimal(maxBiggestBet.getText());
        BigDecimal minWin = parseDecimal(minBiggestWin.getText());
        BigDecimal maxWin = parseDecimal(maxBiggestWin.getText());

        // stops if any of the inputs were invalid
        if ((minBet == null && !minBiggestBet.getText().isBlank()) ||
                (maxBet == null && !maxBiggestBet.getText().isBlank()) ||
                (minWin == null && !minBiggestWin.getText().isBlank()) ||
                (maxWin == null && !maxBiggestWin.getText().isBlank())) {
            return;
        }

        // filters all the users
        List<User> filtered = allUsers.stream()
                .filter(user -> withinRange(user.getBiggestBet(), minBet, maxBet))
                .filter(user -> withinRange(user.getBiggestWin(), minWin, maxWin))
                .collect(Collectors.toList());

        leaderboard.setItems(typeManager.toLeaderboard(filtered));
    }

    public void applySearch(ActionEvent event) {
        String searchedUsername = usernameSearch.getText().trim();

        // shows all users on the leaderboard if the username input is empty
        if (searchedUsername.isBlank()) {
            leaderboard.setItems(typeManager.toLeaderboard(allUsers));
            return;
        }

        if (!searchedUsername.matches("^[a-zA-Z0-9_]*$")) {
            userAlerts.showAlert("Invalid search", "Username search can only contain letters, numbers, or underscores.");
            return;
        }

        // searches for the user
        List<User> matched = allUsers.stream()
                .filter(user -> user.getUsername().toLowerCase().contains(searchedUsername.toLowerCase()))
                .collect(Collectors.toList());

        leaderboard.setItems(typeManager.toLeaderboard(matched));
    }

    // parses the input string into BigDecimal or null if input is invalid
    private BigDecimal parseDecimal(String text) {
        try {
            if (text == null || text.isBlank()) {
                return null;
            }

            BigDecimal value = new BigDecimal(text.trim());

            if (value.compareTo(BigDecimal.ZERO) < 0) {
                userAlerts.showAlert("Invalid input", "The input cannot be negative.");
                return null;
            }

            return value;
        } catch (NumberFormatException e) {
            userAlerts.showAlert("Invalid input", "Input must be a valid number.");
            return null;
        }
    }

    // helper method that checks if the values are within range
    private boolean withinRange(BigDecimal value, BigDecimal min, BigDecimal max) {
        if (value == null) {
            return false;
        }

        if (min != null && value.compareTo(min) < 0) {
            return false;
        }

        if (max != null && value.compareTo(max) > 0) {
            return false;
        }

        return true;
    }
}