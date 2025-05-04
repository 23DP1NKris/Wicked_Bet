package wickedbet.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class LeaderboardUser {
    private StringProperty username;
    private StringProperty biggestBet;
    private StringProperty biggestWin;

    public LeaderboardUser(String username, BigDecimal biggestBet, BigDecimal biggestWin) {
        this.username = new SimpleStringProperty(username);
        this.biggestBet = new SimpleStringProperty(biggestBet.toString());
        this.biggestWin = new SimpleStringProperty(biggestWin.toString());
    }

    // returns the username
    public String getUsername() {
        return username.get();
    }

    // sets the username
    public void setUsername(String username) {
        this.username.set(username);
    }

    // returns the username as StringProperty
    public StringProperty getUsernameProperty() {
        return username;
    }

    // returns the biggestBet as StringProperty
    public StringProperty getBiggestBetProperty() {
        return biggestBet;
    }

    // returns the biggestWin as StringProperty
    public StringProperty getBiggestWinProperty() {
        return biggestWin;
    }
}