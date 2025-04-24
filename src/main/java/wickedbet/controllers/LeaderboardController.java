package wickedbet.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import wickedbet.models.LeaderboardUser;
import wickedbet.models.User;
import wickedbet.services.JsonService;
import wickedbet.utils.SceneManager;
import wickedbet.utils.TypeManager;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class LeaderboardController implements Initializable {
    private final JsonService jsonService = new JsonService();
    private final TypeManager typeManager = new TypeManager();

    @FXML
    private TableView<LeaderboardUser> leaderboard;
    @FXML
    private TableColumn<LeaderboardUser, String> usernameColumn;
    @FXML
    private TableColumn<LeaderboardUser, String> biggestBetColumn;
    @FXML
    private TableColumn<LeaderboardUser, String> biggestWinColumn;

    private final SceneManager sceneManager = new SceneManager();

    public void goBack(ActionEvent event) throws IOException { //
        sceneManager.switchToMenu(event);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<User> users = jsonService.loadUsers();

        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsernameProperty());
        biggestBetColumn.setCellValueFactory(cellData -> cellData.getValue().getBiggestBetProperty());
        biggestWinColumn.setCellValueFactory(cellData -> cellData.getValue().getBiggestWinProperty());

        leaderboard.setItems(typeManager.UserToLeaderboard(users));
    }
}