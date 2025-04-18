package wickedbet.services;

import wickedbet.alerts.UserAlerts;
import wickedbet.models.User;

import java.util.List;

public class LoginService {
    private final JsonService jsonService = new JsonService();
    private final UserAlerts loginAlerts = new UserAlerts();
    private final ValidInputService validInputService = new ValidInputService();
    private final UserSessionService userSessionService = UserSessionService.getInstance();

    public boolean loginUser(String username, String password) {
        // input validation
        if (!validationCheck(username, password)) {
            return false;
        }

        List<User> users = jsonService.loadUsers(); // loads all users from the json file in a list
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {   // checks if the
                userSessionService.setLoggedIn(user);   // sets the user as online
                return true;
            }
        }

        // shows an alert if the user input doesn't match any users in the list
        loginAlerts.showAlert("Invalid credentials", "Invalid username or password.");
        return false;
    }

    // method for input validation
    public boolean validationCheck(String username, String password) {
        if (validInputService.emptyInputs(username, password)) {
            loginAlerts.showAlert("Invalid input", "Username or password fields cannot be empty!");
            return false;
        }
        return true;
    }
}
