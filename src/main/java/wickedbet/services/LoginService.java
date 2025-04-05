package wickedbet.services;

import wickedbet.models.User;

import java.util.List;

public class LoginService {
    private final JsonService jsonService = new JsonService();

    public boolean loginUser(String username, String password) {
        List<User> users = jsonService.loadUsers();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Logged in successfully.");
                return true;
            }
        }

        System.out.println("Invalid username or password.");
        return false;
    }

    // add an update date on login function
}