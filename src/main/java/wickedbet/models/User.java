package wickedbet.models;

import java.time.LocalDate;
import java.math.BigDecimal;

public class User {
    private String username;
    private String password;
    private BigDecimal balance;
    private int remainingSpins;
    private BigDecimal biggestBet;
    private BigDecimal biggestWin;
    private final LocalDate registrationDate;

    // user constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = BigDecimal.ZERO;
        this.remainingSpins = 10;
        this.biggestBet = BigDecimal.ZERO;
        this.biggestWin = BigDecimal.ZERO;
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
    public BigDecimal getBalance() {
        return balance;
    }

    // sets the new balance
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    // adds balance
    public void addBalance(BigDecimal addedBalance) {
        this.balance = this.balance.add(addedBalance);
    }

    // returns the biggestBet value
    public BigDecimal getBiggestBet() {
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
    public void setBiggestBet(BigDecimal biggestBet) {
        this.biggestBet = biggestBet;
    }

    // returns the biggestWin variable
    public BigDecimal getBiggestWin() {
        return biggestWin;
    }

    // sets the biggestWin variable
    public void setBiggestWin(BigDecimal biggestWin) {
        this.biggestWin = biggestWin;
    }

    // returns the registration date
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
}
