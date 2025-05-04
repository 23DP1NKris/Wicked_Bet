package services_tests;

import org.junit.jupiter.api.*;
import wickedbet.services.BetService;
import wickedbet.alerts.UserAlerts;
import wickedbet.models.User;
import wickedbet.services.UserSessionService;

import java.lang.reflect.Field;
import java.math.BigDecimal;

class BetServiceTest {
    private BetService betService;
    private UserSessionService sessionService;

    private static class SilentAlerts extends UserAlerts {
        @Override
        public void showAlert(String title, String message) {
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        betService = new BetService();

        Field alertsField = BetService.class.getDeclaredField("betAlerts");
        alertsField.setAccessible(true);
        alertsField.set(betService, new SilentAlerts());

        sessionService = UserSessionService.getInstance();
        sessionService.setLoggedIn(new User("test", "pswd"));
    }

    @Test
    void validationCheck_emptyInput_returnsFalse() {
        boolean result = betService.validationCheck("", new BigDecimal("10.00"));
        Assertions.assertFalse(result);
    }

    @Test
    void validationCheck_invalidFormat_returnsFalse() {
        boolean result = betService.validationCheck("abc", new BigDecimal("10.00"));
        Assertions.assertFalse(result);
    }

    @Test
    void validationCheck_belowMinimumBet_returnsFalse() {
        boolean result = betService.validationCheck("0.05", new BigDecimal("10.00"));
        Assertions.assertFalse(result);
    }

    @Test
    void validationCheck_overBalance_returnsFalse() {
        boolean result = betService.validationCheck("20.00", new BigDecimal("10.00"));
        Assertions.assertFalse(result);
    }

    @Test
    void validationCheck_validInputWithinBalance_returnsTrue() {
        boolean result = betService.validationCheck("5.50", new BigDecimal("10.00"));
        Assertions.assertTrue(result);
    }

    @Test
    void biggestBet_higherThanPrevious_updatesBiggestBet() {
        User user = sessionService.getLoggedIn();
        user.setBiggestBet(new BigDecimal("2.00"));

        betService.biggestBet(new BigDecimal("5.00"));
        Assertions.assertEquals(new BigDecimal("5.00"), user.getBiggestBet());
    }

    @Test
    void biggestBet_lowerThanPrevious_doesNotUpdateBiggestBet() {
        User user = sessionService.getLoggedIn();
        user.setBiggestBet(new BigDecimal("10.00"));

        betService.biggestBet(new BigDecimal("5.00"));
        Assertions.assertEquals(new BigDecimal("10.00"), user.getBiggestBet());
    }
}
