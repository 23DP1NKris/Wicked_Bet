package wickedbet.models;

import java.time.LocalDate;

public class User {
    private String username;
    private String password;
    private double biggestBet;
    private double biggestWin;
    private LocalDate lastLoginDate;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.biggestBet = 0.0;
        this.biggestWin = 0.0;
        this.lastLoginDate = LocalDate.now();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBiggestBet() {
        return biggestBet;
    }

    public double getBiggestWin() {
        return biggestWin;
    }

    public LocalDate getLastLoginDate() {
        return lastLoginDate;
    }

    public void updateLastLoginDate() {
        this.lastLoginDate = LocalDate.now();
    }
}