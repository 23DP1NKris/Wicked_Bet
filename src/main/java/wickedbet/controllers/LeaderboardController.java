package wickedbet.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

        usernameColumn.setCellValueFactory(cell -> cell.getValue().getUsernameProperty());
        biggestBetColumn.setCellValueFactory(cell -> cell.getValue().getBiggestBetProperty());
        biggestWinColumn.setCellValueFactory(cell -> cell.getValue().getBiggestWinProperty());

        // show everyone initially
        leaderboard.setItems(typeManager.toLeaderboard(allUsers));
    }

    public void applyFilter(ActionEvent event) {
        // if there is no value to parse it gives it a default value
        BigDecimal minBet = parseDecimalOrDefault(minBiggestBet.getText(), BigDecimal.ZERO);
        BigDecimal maxBet = parseDecimalOrDefault(maxBiggestBet.getText(), null);
        BigDecimal minWin = parseDecimalOrDefault(minBiggestWin.getText(), BigDecimal.ZERO);
        BigDecimal maxWin = parseDecimalOrDefault(maxBiggestWin.getText(), null);

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

        // searches for the user
        List<User> matched = allUsers.stream()
                .filter(user -> user.getUsername().toLowerCase().contains(searchedUsername.toLowerCase()))
                .collect(Collectors.toList());

        leaderboard.setItems(typeManager.toLeaderboard(matched));
    }

    // parses the input or returns the default value
    private BigDecimal parseDecimalOrDefault(String text, BigDecimal defaultValue) {
        try {
            if (text == null || text.isBlank()) {
                return defaultValue;
            }

            return new BigDecimal(text.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // helper method that checks if the values are within range
    private boolean withinRange(BigDecimal value, BigDecimal min, BigDecimal max) {
        if (value.compareTo(min) < 0) {
            return false;
        }

        if (max != null && value.compareTo(max) > 0) {
            return false;
        }

        return true;
    }
}