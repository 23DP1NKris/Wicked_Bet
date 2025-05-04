package services_tests;

import org.junit.jupiter.api.*;
import wickedbet.controllers.RegisterController;
import wickedbet.services.RegisterService;
import wickedbet.utils.SceneManager;
import wickedbet.models.User;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.lang.reflect.Field;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {

    private RegisterController controller;
    private StubRegisterService stubRegisterService;
    private StubSceneManager stubSceneManager;

    @BeforeAll
    static void initToolkit() {
        javafx.application.Platform.startup(() -> {});
    }

    private static class StubRegisterService extends RegisterService {
        boolean validationResult = true;
        User lastRegisteredUser = null;

        @Override
        public boolean validationCheck(String username, String password) {
            return validationResult;
        }

        @Override
        public void registerUser(User user) {
            lastRegisteredUser = user;
        }
    }

    private static class StubSceneManager extends SceneManager {
        boolean switchedToLogin = false;
        boolean switchedToMenu = false;

        @Override
        public void switchToLogin(ActionEvent event) {
            switchedToLogin = true;
        }

        @Override
        public void switchToMenu(ActionEvent event) {
            switchedToMenu = true;
        }
    }

    private void injectStubs() throws Exception {
        Field regServiceField = RegisterController.class.getDeclaredField("registerService");
        regServiceField.setAccessible(true);
        regServiceField.set(controller, stubRegisterService);

        Field sceneMgrField = RegisterController.class.getDeclaredField("sceneManager");
        sceneMgrField.setAccessible(true);
        sceneMgrField.set(controller, stubSceneManager);
    }

    @BeforeEach
    void setUp() throws Exception {
        controller = new RegisterController();
        stubRegisterService = new StubRegisterService();
        stubSceneManager = new StubSceneManager();

        controller.usernameField = new TextField();
        controller.passwordField = new PasswordField();

        injectStubs();
    }

    @Test
    void switchToLogin_SceneManager() throws IOException {
        ActionEvent dummyEvent = new ActionEvent();
        controller.switchToLogin(dummyEvent);
        assertTrue(stubSceneManager.switchedToLogin, "Expected switchToLogin to call SceneManager.switchToLogin");
    }

    @Test
    void switchToLogin() throws IOException {
        ActionEvent dummyEvent = new ActionEvent();
        assertFalse(stubSceneManager.switchedToMenu);
        controller.switchToLogin(dummyEvent);
        assertFalse(stubSceneManager.switchedToMenu, "switchToLogin should not switch to menu");
    }

    @Test
    void register_withValidInput_registersAndSwitchesToMenu() throws IOException {
        controller.usernameField.setText("alice");
        controller.passwordField.setText("secret");
        stubRegisterService.validationResult = true;

        controller.register(new ActionEvent());

        assertNotNull(stubRegisterService.lastRegisteredUser, "Expected registerUser to be called");
        assertEquals("alice", stubRegisterService.lastRegisteredUser.getUsername());
        assertEquals("secret", stubRegisterService.lastRegisteredUser.getPassword());
        assertTrue(stubSceneManager.switchedToMenu, "Expected switchToMenu to be called on valid registration");
    }

    @Test
    void register_withInvalidInput_doesNothing() throws IOException {
        controller.usernameField.setText("bob");
        controller.passwordField.setText("pwd");
        stubRegisterService.validationResult = false;

        controller.register(new ActionEvent());

        assertNull(stubRegisterService.lastRegisteredUser, "Expected no call to registerUser");
        assertFalse(stubSceneManager.switchedToMenu, "Expected no call to switchToMenu");
    }

    @Test
    void register_trimsFieldsBeforeValidation() throws Exception {
        controller.usernameField.setText("john_pork ");
        controller.passwordField.setText("pa$$w0rd");
        stubRegisterService = new StubRegisterService() {
            @Override
            public boolean validationCheck(String username, String password) {
                assertEquals("john_pork", username);
                assertEquals("pa$$w0rd", password);
                return false;
            }
        };
        injectStubs();

        controller.register(new ActionEvent());
    }
}