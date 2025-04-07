package wickedbet.services;

import wickedbet.alerts.UserAlerts;
import wickedbet.models.User;

import java.util.List;

public class LoginService {
    private final JsonService jsonService = new JsonService();
    private final UserAlerts LoginAlerts = new UserAlerts();

    public boolean loginUser(String username, String password) {
        List<User> users = jsonService.loadUsers();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }

        LoginAlerts.showAlert("Invalid credentials", "Invalid username or password.");
        return false;
    }
}