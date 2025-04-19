package wickedbet.models;

import java.time.LocalDate;

public class User {
    private String username;
    private String password;
    private double balance;
    private int remainingSpins;
    private double biggestBet;
    private double biggestWin;
    private final LocalDate registrationDate;

    // user constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
        this.remainingSpins = 10;
        this.biggestBet = 0.0;
        this.biggestWin = 0.0;
        this.registrationDate = LocalDate.now();
    }

    // returns the username
    public String getUsername() {
        return username;
    }

    // returns the password
    public String getPassword() {
        return password;
    }

    // returns the balance
    public double getBalance() {
        return balance;
    }

    // sets the new balance
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // adds balance
    public void addBalance(double addedBalance) {
        this.balance += addedBalance;
    }

    // returns the biggestBet value
    public double getBiggestBet() {
        return biggestBet;
    }

    // returns the remainingSpins variable
    public int getRemainingSpins() {
        return remainingSpins;
    }

    // sets the remaining spins (usually goes down by 1)
    public void setRemainingSpins(int remainingSpins) {
        this.remainingSpins = remainingSpins;
    }

    // sets the biggestBet
    public void setBiggestBet(double biggestBet) {
        this.biggestBet = biggestBet;
    }

    // returns the biggestWin variable
    public double getBiggestWin() {
        return biggestWin;
    }

    // sets the biggestWin variable
    public void setBiggestWin(double biggestWin) {
        this.biggestWin = biggestWin;
    }

    // returns the registration date
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
}