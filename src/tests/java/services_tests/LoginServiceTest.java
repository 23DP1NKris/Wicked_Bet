package services_tests;

import org.junit.jupiter.api.*;
import wickedbet.services.LoginService;
import wickedbet.services.UserSessionService;
import wickedbet.alerts.UserAlerts;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    private LoginService loginService;

    private static class SilentAlerts extends UserAlerts {
        @Override
        public void showAlert(String title, String message) {}
    }

    private void fakeAlert(Object target) throws Exception {
        Field alertsField = null;
        for (Field field : LoginService.class.getDeclaredFields()) {
            if (field.getType() == UserAlerts.class) {
                alertsField = field;
                break;
            }
        }

        if (alertsField == null) {
            throw new IllegalStateException("No UserAlerts field on LoginService");
        }

        alertsField.setAccessible(true);
        alertsField.set(target, new SilentAlerts());
    }

    @BeforeEach
    void setUp() throws Exception {
        loginService = new LoginService();
        fakeAlert(loginService);
        UserSessionService.getInstance().setLoggedIn(null);
    }

    @Test
    void loginUser_ValidCredentials_ReturnsTrue() {
        boolean ok = loginService.loginUser("pork_jonathan", "TimCheeseisevil");
        assertTrue(ok);
        assertEquals("pork_jonathan", UserSessionService.getInstance().getLoggedIn().getUsername());
    }

    @Test
    void loginUser_InvalidCredentials_ReturnsFalse() {
        boolean ok = loginService.loginUser("nope", "wrong");
        assertFalse(ok);
        assertNull(UserSessionService.getInstance().getLoggedIn());
    }

    @Test
    void validationCheck_ValidInputs_ReturnsTrue() {
        assertTrue(loginService.validationCheck("user","pass"));
    }

    @Test
    void validationCheck_EmptyPassword_ReturnsFalse() {
        assertFalse(loginService.validationCheck("user",""));
    }
}
