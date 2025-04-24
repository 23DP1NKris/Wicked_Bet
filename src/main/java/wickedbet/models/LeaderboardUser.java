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

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty getUsernameProperty() {
        return username;
    }

    public String getBiggestBet() {
        return biggestBet.get();
    }

    public void setBiggestBet(BigDecimal biggestBet) {
        this.biggestBet.set(String.valueOf(biggestBet));
    }

    public StringProperty getBiggestBetProperty() {
        return biggestBet;
    }

    public String getBiggestWin() {
        return biggestWin.get();
    }

    public void setBiggestWin(BigDecimal biggestWin) {
        this.biggestWin.set(biggestWin.toString());
    }

    public StringProperty getBiggestWinProperty() {
        return biggestWin;
    }
}
