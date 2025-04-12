package wickedbet.services;

import wickedbet.models.User;
import java.util.List;
import java.util.regex.Pattern;

public class ValidInputService {
    private final JsonService jsonService = new JsonService();

    public static boolean validInputs(String username, String password) {
        return !Pattern.matches("^[a-z0-A-Z9_]+$", username) || !Pattern.matches("^[a-zA-Z0-9!@#$&_]+$", password);
    }

    public boolean emptyInputs(String username, String password) {
        return username.isEmpty() || password.isEmpty();
    }

    public boolean userExists(String username) {
        List<User> users = jsonService.loadUsers();
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean passwordLength(String password) {
        return password.length() < 6;
    }
}