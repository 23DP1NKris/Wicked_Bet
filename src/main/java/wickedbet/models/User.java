package wickedbet.models;

import java.time.LocalDate;

public class User {
    private String username;
    private String password;
    private double balance;
    private double biggestBet;
    private double biggestWin;
    private LocalDate registrationDate;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBiggestBet() {
        return biggestBet;
    }

    public void setBiggestBet(double biggestBet) {
        this.biggestBet = biggestBet;
    }

    public double getBiggestWin() {
        return biggestWin;
    }

    public void setBiggestWin(double biggestWin) {
        this.biggestWin = biggestWin;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

}