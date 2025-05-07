package services_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wickedbet.alerts.UserAlerts;
import wickedbet.models.User;
import wickedbet.services.RegisterService;
import wickedbet.services.UserSessionService;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {
    private RegisterService svc;

    private static class SilentAlerts extends UserAlerts {
        @Override
        public void showAlert(String title, String message) {}
    }

    private void fakeAlerts(RegisterService target) throws Exception {
        Field alertsField = null;
        for (Field f : RegisterService.class.getDeclaredFields()) {
            if (f.getType() == UserAlerts.class) {
                alertsField = f;
                break;
            }
        }
        if (alertsField == null) {
            throw new IllegalStateException("No UserAlerts field on RegisterService");
        }
        alertsField.setAccessible(true);
        alertsField.set(target, new SilentAlerts());
    }

    @BeforeEach
    void setUp() throws Exception {
        UserSessionService.getInstance().setLoggedIn(null);
        svc = new RegisterService();
        fakeAlerts(svc);
    }

    @Test
    void registerUser_setsSessionAndDoesNotThrow() {
        User user = new User("alice", "securePwd!");
        assertNull(UserSessionService.getInstance().getLoggedIn());
        svc.registerUser(user);
        assertEquals(user, UserSessionService.getInstance().getLoggedIn());
    }

    @Test
    void registerUser_overwritesPreviousSession() {
        User user1 = new User("bob", "123456");
        User user2 = new User("carol", "abcdef");
        svc.registerUser(user1);
        assertEquals(user1, UserSessionService.getInstance().getLoggedIn());
        svc.registerUser(user2);
        assertEquals(user2, UserSessionService.getInstance().getLoggedIn());
    }

    @Test
    void validationCheck_rejectsEmptyUsername() {
        boolean ok = svc.validationCheck("", "nonEmpty");
        assertFalse(ok);
    }

    @Test
    void validationCheck_rejectsEmptyPassword() {
        boolean ok = svc.validationCheck("user", "");
        assertFalse(ok);
    }

    @Test
    void validationCheck_rejectsBadCharsInUsername() {
        boolean ok = svc.validationCheck("invalid@@", "password");
        assertFalse(ok);
    }

    @Test
    void validationCheck_rejectsShortPassword() {
        boolean ok = svc.validationCheck("validUser", "123");
        assertFalse(ok);
    }

    @Test
    void validationCheck_rejectsExistingUsername() {
        User exists = new User("takenName", "password");
        svc.registerUser(exists);
        boolean ok = svc.validationCheck("takenName", "password");
        assertFalse(ok);
    }

    @Test
    void validationCheck_acceptsGoodInput() {
        boolean ok = svc.validationCheck("newUser", "password");
        assertTrue(ok);
    }
}
