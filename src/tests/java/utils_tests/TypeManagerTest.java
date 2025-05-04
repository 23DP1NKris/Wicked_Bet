package utils_tests;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wickedbet.models.LeaderboardUser;
import wickedbet.models.User;
import wickedbet.utils.TypeManager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TypeManagerTest {
    private TypeManager typeManager;

    @BeforeEach
    public void setUp() {
        typeManager = new TypeManager();
    }

    @Test
    public void testToLeaderboard_EmptyList() {
        List<User> users = Collections.emptyList();
        ObservableList<LeaderboardUser> result = typeManager.toLeaderboard(users);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testToLeaderboard_SingleUser() {
        User user = new User("alice", "pass");
        user.setBiggestBet(new BigDecimal("50"));
        user.setBiggestWin(new BigDecimal("100"));
        List<User> users = Collections.singletonList(user);

        ObservableList<LeaderboardUser> result = typeManager.toLeaderboard(users);
        assertEquals(1, result.size());
        LeaderboardUser lb = result.get(0);
        assertEquals("alice", lb.getUsername());
        assertEquals("50", lb.getBiggestBetProperty().get());
        assertEquals("100", lb.getBiggestWinProperty().get());
    }

    @Test
    public void testToLeaderboard_MultipleUsers() {
        User user1 = new User("bob", "pswd");
        user1.setBiggestBet(new BigDecimal("20.5"));
        user1.setBiggestWin(new BigDecimal("40.75"));
        User user2 = new User("carol", "pswd");
        user2.setBiggestBet(new BigDecimal("30"));
        user2.setBiggestWin(new BigDecimal("60"));
        List<User> users = Arrays.asList(user1, user2);

        ObservableList<LeaderboardUser> result = typeManager.toLeaderboard(users);
        assertEquals(2, result.size());

        LeaderboardUser lb1 = result.get(0);
        assertEquals("bob", lb1.getUsername());
        assertEquals("20.5", lb1.getBiggestBetProperty().get());
        assertEquals("40.75", lb1.getBiggestWinProperty().get());

        LeaderboardUser lb2 = result.get(1);
        assertEquals("carol", lb2.getUsername());
        assertEquals("30", lb2.getBiggestBetProperty().get());
        assertEquals("60", lb2.getBiggestWinProperty().get());
    }


    @Test
    public void testToLeaderboard_NullListThrows() {
        assertThrows(NullPointerException.class, () -> typeManager.toLeaderboard(null));
    }

    @Test
    public void testToLeaderboard_DefaultValuesPreserved() {
        User user = new User("dave", "pswd");
        List<User> users = Collections.singletonList(user);

        ObservableList<LeaderboardUser> result = typeManager.toLeaderboard(users);
        assertEquals(1, result.size());
        LeaderboardUser lb = result.get(0);
        assertEquals("0", lb.getBiggestBetProperty().get());
        assertEquals("0", lb.getBiggestWinProperty().get());
    }
}
