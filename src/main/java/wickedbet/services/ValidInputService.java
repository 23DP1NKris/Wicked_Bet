package wickedbet.services;

import wickedbet.models.User;
import java.util.List;
import java.util.regex.Pattern;

public class ValidInputService {
    private final JsonService jsonService = new JsonService();

    public static boolean validInputs(String username, String password) { // checks if there aren't invalid symbols in the inputs
        return !Pattern.matches("^[a-zA-Z0-9_]+$", username) || !Pattern.matches("^[a-zA-Z0-9!@#$&_]+$", password);
    }

    public boolean emptyInputs(String username, String password) { // checks if the inputs aren't empty
        return username.isEmpty() || password.isEmpty();
    }

    public boolean userExists(String username) { // checks if a user with the same username doesn't already exist
        List<User> users = jsonService.loadUsers(); // loads all users from the json file into a list
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {    // checks while ignoring upper and lowercase so a user with "User1" can't register when "user1" exists
                return true;
            }
        }
        return false;
    }

    public boolean passwordLength(String password) { // checks if the password is at least 6 chars long
        return password.length() < 6;
    }

    public static boolean validInputs(String inputBet) { // checks if there aren't invalid symbols in the input
        return !Pattern.matches("^\\d+(\\.\\d{1,2})?$", inputBet);
    }

    public static boolean emptyInputs(String inputBet) { // checks if the input isn't empty
        return inputBet == null || inputBet.isEmpty();
    }
}