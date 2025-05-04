package models_tests;

import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wickedbet.models.LeaderboardUser;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardUserTest {
    private LeaderboardUser lbUser;

    @BeforeEach
    public void setUp() {
        lbUser = new LeaderboardUser("user1", new BigDecimal("150.00"), new BigDecimal("200.50"));
    }

    @Test
    public void getUsername_ReturnsInitialUsername() {
        assertEquals("user1", lbUser.getUsername());
    }

    @Test
    public void getUsernameProperty_ReflectsUsername() {
        StringProperty prop = lbUser.getUsernameProperty();
        assertEquals("user1", prop.get());
        prop.set("changedUser");
        assertEquals("changedUser", lbUser.getUsername());
    }

    @Test
    public void setUsername_UpdatesBothGetterAndProperty() {
        lbUser.setUsername("newUser");
        assertEquals("newUser", lbUser.getUsername());
        assertEquals("newUser", lbUser.getUsernameProperty().get());
    }

    @Test
    public void getBiggestBetProperty_ReturnsInitialBet() {
        StringProperty betProp = lbUser.getBiggestBetProperty();
        assertEquals("150.00", betProp.get());
    }

    @Test
    public void biggestBetProperty_CanBeUpdated() {
        StringProperty betProp = lbUser.getBiggestBetProperty();
        betProp.set("250.25");
        assertEquals("250.25", lbUser.getBiggestBetProperty().get());
    }

    @Test
    public void getBiggestWinProperty_ReturnsInitialWin() {
        StringProperty winProp = lbUser.getBiggestWinProperty();
        assertEquals("200.50", winProp.get());
    }

    @Test
    public void biggestWinProperty_CanBeUpdated() {
        StringProperty winProp = lbUser.getBiggestWinProperty();
        winProp.set("350.75");
        assertEquals("350.75", lbUser.getBiggestWinProperty().get());
    }
}