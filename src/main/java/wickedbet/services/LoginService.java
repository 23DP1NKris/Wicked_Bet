package wickedbet.services;

import wickedbet.alerts.UserAlerts;
import wickedbet.models.User;

import java.util.List;

public class LoginService {
    private final JsonService jsonService = new JsonService();
    private final UserAlerts loginAlerts = new UserAlerts();
    private final ValidInputService validInputService = new ValidInputService();

    public boolean loginUser(String username, String password) {
        if (!validationCheck(username, password)) {
            return false;
        }

        List<User> users = jsonService.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }

        loginAlerts.showAlert("Invalid credentials", "Invalid username or password.");
        return false;
    }

    public boolean validationCheck(String username, String password) {
        if (validInputService.emptyInputs(username, password)) {
            loginAlerts.showAlert("Invalid input", "Username or password fields cannot be empty!");
            return false;
        }

        return true;
    }
}
