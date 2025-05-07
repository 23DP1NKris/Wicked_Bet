package services_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wickedbet.models.User;
import wickedbet.services.JsonService;
import wickedbet.services.ValidInputService;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ValidInputServiceTest {
    private ValidInputService service;

    @BeforeEach
    void setUp() throws Exception {
        service = new ValidInputService();
        JsonService stubJson = new JsonService() {
            @Override
            public List<User> loadUsers() {
                User user = new User("ExistingUser", "pw");
                return Collections.singletonList(user);
            }
        };

        Field field = ValidInputService.class.getDeclaredField("jsonService");
        field.setAccessible(true);
        field.set(service, stubJson);
    }

    @Test
    void validInputsUsernamePassword_AllValid() {
        assertFalse(ValidInputService.validInputs("User_123", "Pass@123&"));
    }

    @Test
    void validInputsUsernamePassword_InvalidPassword() {
        assertTrue(ValidInputService.validInputs("User", "Pass-"));
    }

    @Test
    void validInputsUsernamePassword_InvalidUsername() {
        assertTrue(ValidInputService.validInputs("User.", "Pass@123&"));
    }

    @Test
    void validInputsUsernamePassword_InvalidUsernameAndPassword() {
        assertTrue(ValidInputService.validInputs("User.", "Pass-"));
    }

    @Test
    void emptyInputsUsernamePassword_EmptyUsername() {
        assertTrue(service.emptyInputs("", "password"));
    }

    @Test
    void emptyInputsUsernamePassword_EmptyPassword() {
        assertTrue(service.emptyInputs("User", ""));
    }

    @Test
    void emptyInputsUsernamePassword_BothEmpty() {
        assertTrue(service.emptyInputs("", ""));
    }

    @Test
    void emptyInputsUsernamePassword_BothFilled() {
        assertFalse(service.emptyInputs("user", "pass"));
    }

    @Test
    void userExists_ExistingUser_IgnoringCase() {
        assertTrue(service.userExists("existinguser"));
    }

    @Test
    void userExists_NewUser() {
        assertFalse(service.userExists("newUser"));
    }

    @Test
    void passwordLength_TooShort() {
        assertTrue(service.passwordLength("12345"));
    }

    @Test
    void passwordLength_LongEnough() {
        assertFalse(service.passwordLength("123456"));
    }

    @Test
    void validInputsBet_ValidFormat() {
        assertFalse(ValidInputService.validInputs("123.45"));
    }

    @Test
    void validInputsBet_InvalidFormat() {
        assertTrue(ValidInputService.validInputs("123.456"));
    }

    @Test
    void emptyInputsBet_NullOrEmpty() {
        assertTrue(ValidInputService.emptyInputs((String) null));
        assertTrue(ValidInputService.emptyInputs(""));
    }

    @Test
    void emptyInputsBet_NotEmpty() {
        assertFalse(ValidInputService.emptyInputs("0"));
    }
}
