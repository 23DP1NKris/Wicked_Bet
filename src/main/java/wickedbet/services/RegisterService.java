package wickedbet.services;

import wickedbet.alerts.UserAlerts;
import wickedbet.models.User;

public class RegisterService {
    private final JsonService jsonService = new JsonService();
    private final ValidRegistrationService validRegistrationService = new ValidRegistrationService();
    private final UserAlerts registerAlerts = new UserAlerts();

    public void registerUser(User user) {
        jsonService.saveUser(user);
        System.out.println("User registered successfully: " + user.getUsername());
    }

    public boolean validationCheck(String username, String password) {
        if (validRegistrationService.emptyInputs(username, password)) {
            registerAlerts.showAlert("Invalid input", "Username or password fields cannot be empty!");
            return false;
        }

        if (ValidRegistrationService.validInputs(username, password)) {
            registerAlerts.showAlert("Invalid input", "Username cannot contain any special characters!" +
                                        "\nThe password can only contain ! @ # $ &.");
            return false;
        }

        if (validRegistrationService.userExists(username)) {
            registerAlerts.showAlert("Invalid username", "Username is already taken!");
            return false;
        }

        if (validRegistrationService.passwordLength(password)) {
            registerAlerts.showAlert("Invalid password", "Password should be at least 6 characters long!");
            return false;
        }

        return true;
    }
}