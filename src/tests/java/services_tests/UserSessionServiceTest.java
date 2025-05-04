package services_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wickedbet.models.User;
import wickedbet.services.UserSessionService;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class UserSessionServiceTest {

    @BeforeEach
    void resetSingleton() throws Exception {
        Field inst = UserSessionService.class.getDeclaredField("instance");
        inst.setAccessible(true);
        inst.set(null, null);
    }

    @Test
    void getInstance_ReturnsSingleton() {
        UserSessionService s1 = UserSessionService.getInstance();
        UserSessionService s2 = UserSessionService.getInstance();
        assertSame(s1, s2);
    }

    @Test
    void getInstance_LazyInitialization() {
        Field instance;
        try {
            instance = UserSessionService.class.getDeclaredField("instance");
            instance.setAccessible(true);
            assertNull(instance.get(null));
            UserSessionService.getInstance();
            assertNotNull(instance.get(null));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void setAndGetLoggedIn_UserIsStored() {
        UserSessionService svc = UserSessionService.getInstance();
        User user = new User("test", "pw");
        svc.setLoggedIn(user);
        assertSame(user, svc.getLoggedIn());
    }

    @Test
    void getLoggedIn_DefaultIsNull() {
        UserSessionService svc = UserSessionService.getInstance();
        assertNull(svc.getLoggedIn());
    }
}
