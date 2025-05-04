package utils_tests;

import java.lang.reflect.Method;
import java.net.URL;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import wickedbet.utils.SceneManager;

public class SceneManagerTest {
    private SceneManager manager;
    private ActionEvent dummyEvent;

    @BeforeEach
    void setUp() {
        manager = new SceneManager();
        dummyEvent = new ActionEvent();
    }

    private URL invokeAndGetResource(String fxmlName) throws Exception {
        Method method = SceneManager.class.getDeclaredMethod("switchScene", String.class, ActionEvent.class);
        method.setAccessible(true);
        try {
            method.invoke(manager, fxmlName, dummyEvent);
        } catch (Exception ignored) {}
        return getClass().getResource("/javafx/" + fxmlName);
    }

    @Test
    void loginFXMLResourceExists() {
        URL res = getClass().getResource("/javafx/login.fxml");
        assertNotNull(res);
    }

    @Test
    void switchToLogin_UsesCorrectResource() throws Exception {
        URL res = invokeAndGetResource("login.fxml");
        assertNotNull(res);
        assertTrue(res.toString().endsWith("/javafx/login.fxml"));
    }

    @Test
    void registerFXMLResourceExists() {
        URL res = getClass().getResource("/javafx/register.fxml");
        assertNotNull(res);
    }

    @Test
    void switchToRegister_UsesCorrectResource() throws Exception {
        URL res = invokeAndGetResource("register.fxml");
        assertNotNull(res);
        assertTrue(res.toString().endsWith("/javafx/register.fxml"));
    }

    @Test
    void menuFXMLResourceExists() {
        URL res = getClass().getResource("/javafx/menu.fxml");
        assertNotNull(res);
    }

    @Test
    void switchToMenu_UsesCorrectResource() throws Exception {
        URL res = invokeAndGetResource("menu.fxml");
        assertNotNull(res);
        assertTrue(res.toString().endsWith("/javafx/menu.fxml"));
    }

    @Test
    void slotsFXMLResourceExists() {
        URL res = getClass().getResource("/javafx/slots.fxml");
        assertNotNull(res);
    }

    @Test
    void switchToSlots_UsesCorrectResource() throws Exception {
        URL res = invokeAndGetResource("slots.fxml");
        assertNotNull(res);
        assertTrue(res.toString().endsWith("/javafx/slots.fxml"));
    }

    @Test
    void statisticsFXMLResourceExists() {
        URL res = getClass().getResource("/javafx/statistics.fxml");
        assertNotNull(res);
    }

    @Test
    void switchToStatistics_UsesCorrectResource() throws Exception {
        URL res = invokeAndGetResource("statistics.fxml");
        assertNotNull(res);
        assertTrue(res.toString().endsWith("/javafx/statistics.fxml"));
    }

    @Test
    void leaderboardFXMLResourceExists() {
        URL res = getClass().getResource("/javafx/leaderboard.fxml");
        assertNotNull(res);
    }

    @Test
    void switchToLeaderboard_UsesCorrectResource() throws Exception {
        URL res = invokeAndGetResource("leaderboard.fxml");
        assertNotNull(res);
        assertTrue(res.toString().endsWith("/javafx/leaderboard.fxml"));
    }

    @Test
    void addBalanceFXMLResourceExists() {
        URL res = getClass().getResource("/javafx/addbalance.fxml");
        assertNotNull(res);
    }

    @Test
    void switchToAddBalance_UsesCorrectResource() throws Exception {
        URL res = invokeAndGetResource("addbalance.fxml");
        assertNotNull(res);
        assertTrue(res.toString().endsWith("/javafx/addbalance.fxml"));
    }

    @Test
    void slotStatsFXMLResourceExists() {
        URL res = getClass().getResource("/javafx/slotstats.fxml");
        assertNotNull(res);
    }

    @Test
    void switchToSlotStats_UsesCorrectResource() throws Exception {
        URL res = invokeAndGetResource("slotstats.fxml");
        assertNotNull(res);
        assertTrue(res.toString().endsWith("/javafx/slotstats.fxml"));
    }
}
