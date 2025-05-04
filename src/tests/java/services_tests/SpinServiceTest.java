package services_tests;

import org.junit.jupiter.api.*;
import wickedbet.services.SpinService;
import wickedbet.services.JsonService;
import wickedbet.services.UserSessionService;
import wickedbet.models.User;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SpinServiceTest {

    private SpinService spinService;
    private StubJsonService stubJsonService;
    private UserSessionService session;

    private static class StubJsonService extends JsonService {
        User lastSavedUser = null;
        @Override
        public void saveUserUpdate(User user) {
            lastSavedUser = user;
        }
    }

    private void injectJsonService() throws Exception {
        Field jsonField = SpinService.class.getDeclaredField("jsonService");
        jsonField.setAccessible(true);
        jsonField.set(spinService, stubJsonService);
    }

    @BeforeEach
    void setUp() throws Exception {
        spinService = new SpinService();
        stubJsonService = new StubJsonService();
        injectJsonService();

        session = UserSessionService.getInstance();
        session.setLoggedIn(new User("test", "pw"));
        session.getLoggedIn().setBalance(BigDecimal.ZERO);
        session.getLoggedIn().setRemainingSpins(0);
        session.getLoggedIn().setBiggestWin(BigDecimal.ZERO);
    }

    @Test
    void calculateWin_middleRowMatch_eightTimesBet() {
        BigDecimal bet = new BigDecimal("2.00");
        String[] symbols = {"A","B","C"};
        int[] stops = {2,2,2};
        BigDecimal win = spinService.calculateWin(bet, stops, symbols);
        assertEquals(new BigDecimal("16.00"), win);
    }

    @Test
    void calculateWin_diagonalMatch_fiveTimesBet() {
        BigDecimal bet = new BigDecimal("1.50");
        String[] symbols = {"X","Y"};
        int[] stops = {0,1,0};
        BigDecimal win = spinService.calculateWin(bet, stops, symbols);
        assertEquals(new BigDecimal("7.50"), win);
    }

    @Test
    void calculateWin_threeFreeSymbols_winsExpectedAmount() {
        BigDecimal bet = new BigDecimal("3.00");
        String[] symbols = {"free","A"};
        int[] stops = {0,1,1};
        BigDecimal win = spinService.calculateWin(bet, stops, symbols);
        assertEquals(new BigDecimal("3.60"), win);
    }

    @Test
    void giveFreeSpins_appliesWonFreeSpinsToUserAndSaves() {
        spinService.calculateWin(BigDecimal.ONE, new int[]{0,1,1}, new String[]{"free","A"});
        spinService.giveFreeSpins();
        User user = session.getLoggedIn();
        assertEquals(3, user.getRemainingSpins());
        assertSame(user, stubJsonService.lastSavedUser);
    }

    @Test
    void giveFreeSpins_noFreeSpins_noChange() {
        spinService.calculateWin(BigDecimal.ONE, new int[]{1,1,1}, new String[]{"A","B"});
        spinService.giveFreeSpins();
        assertEquals(0, session.getLoggedIn().getRemainingSpins());
    }

    @Test
    void updateUserBalance_addsWinAndSaves() {
        BigDecimal win = new BigDecimal("5.25");
        spinService.updateUserBalance(win);
        User user = session.getLoggedIn();
        assertEquals(new BigDecimal("5.25"), user.getBalance());
        assertSame(user, stubJsonService.lastSavedUser);
    }

    @Test
    void updateUserBalance_multipleCalls_accumulatesBalance() {
        spinService.updateUserBalance(new BigDecimal("1.00"));
        spinService.updateUserBalance(new BigDecimal("2.50"));
        assertEquals(new BigDecimal("3.50"), session.getLoggedIn().getBalance());
    }

    @Test
    void biggestWin_higherThanPrevious_updatesAndSaves() {
        BigDecimal win = new BigDecimal("10.00");
        session.getLoggedIn().setBiggestWin(new BigDecimal("5.00"));
        spinService.biggestWin(win);
        assertEquals(new BigDecimal("10.00"), session.getLoggedIn().getBiggestWin());
        assertSame(session.getLoggedIn(), stubJsonService.lastSavedUser);
    }

    @Test
    void biggestWin_notHigher_doesNothing() {
        BigDecimal win = new BigDecimal("2.00");
        session.getLoggedIn().setBiggestWin(new BigDecimal("5.00"));
        spinService.biggestWin(win);
        assertEquals(new BigDecimal("5.00"), session.getLoggedIn().getBiggestWin());
        assertNull(stubJsonService.lastSavedUser);
    }
}