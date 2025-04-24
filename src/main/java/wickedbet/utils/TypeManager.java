package wickedbet.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wickedbet.models.LeaderboardUser;
import wickedbet.models.User;

import java.util.List;

public class TypeManager {
    public ObservableList<LeaderboardUser> UserToLeaderboard(List<User> users) {
        ObservableList<LeaderboardUser> leaderboardUsers = FXCollections.observableArrayList();

        for (User user : users) {
            leaderboardUsers.add(new LeaderboardUser(user.getUsername(), user.getBiggestBet(), user.getBiggestWin()));
        }

        return leaderboardUsers;
    }
}