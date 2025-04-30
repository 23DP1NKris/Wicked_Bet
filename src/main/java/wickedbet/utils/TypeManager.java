package wickedbet.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wickedbet.models.LeaderboardUser;
import wickedbet.models.User;

import java.util.List;

public class TypeManager {
    // puts all users into an ObservableList so it can be used in the table
    public ObservableList<LeaderboardUser> toLeaderboard(List<User> users) {
        ObservableList<LeaderboardUser> list = FXCollections.observableArrayList();
        for (User user : users) {
            list.add(new LeaderboardUser(user.getUsername(), user.getBiggestBet(), user.getBiggestWin()));
        }
        return list;
    }
}