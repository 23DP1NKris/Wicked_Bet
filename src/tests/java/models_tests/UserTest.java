package models_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wickedbet.models.User;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("testUser", "testPass");
    }

    @Test
    public void testConstructorInitialValues() {
        assertEquals("testUser", user.getUsername());
        assertEquals("testPass", user.getPassword());
        assertEquals(BigDecimal.ZERO, user.getBalance());
        assertEquals(10, user.getRemainingSpins());
        assertEquals(BigDecimal.ZERO, user.getBiggestBet());
        assertEquals(BigDecimal.ZERO, user.getBiggestWin());
        assertEquals(LocalDate.now(), user.getRegistrationDate());
    }

    @Test
    public void testGettersAndSettersBalance() {
        BigDecimal newBalance = new BigDecimal("50.25");
        user.setBalance(newBalance);
        assertEquals(newBalance, user.getBalance());
        user.addBalance(new BigDecimal("10.75"));
        assertEquals(new BigDecimal("61.00"), user.getBalance());
    }

    @Test
    public void testRemainingSpins() {
        user.setRemainingSpins(5);
        assertEquals(5, user.getRemainingSpins());
        user.setRemainingSpins(0);
        assertEquals(0, user.getRemainingSpins());
    }

    @Test
    public void testBiggestBetAndWin() {
        BigDecimal bet = new BigDecimal("20");
        BigDecimal win = new BigDecimal("100");
        user.setBiggestBet(bet);
        assertEquals(bet, user.getBiggestBet());
        user.setBiggestWin(win);
        assertEquals(win, user.getBiggestWin());
    }
}
