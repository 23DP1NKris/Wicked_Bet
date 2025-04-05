package wickedbet.models;

import java.time.LocalDate;

public class User {
    private String username;
    private String password;
    private double biggestBet;
    private double biggestWin;
    private LocalDate registrationDate;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.biggestBet = 0.0;
        this.biggestWin = 0.0;
        this.registrationDate = LocalDate.now();
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

    public LocalDate getPreviousLoginDate() {
        return registrationDate;
    }

    public void updatePreviousLoginDate() {
        this.registrationDate = LocalDate.now();
    }
}