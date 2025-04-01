package wickedbet.services;

import wickedbet.models.User;

public class RegisterService {
        private final JsonService jsonService = new JsonService();

        public void registerUser(User user) {
            if (validInputs(user.getUsername(), user.getPassword())) {
                jsonService.saveUser(user);
                System.out.println("User registered successfully: " + user.getUsername());
            } else {
                System.out.println("Invalid registration inputs."); // Update this to show a JavaFX alert later
            }
        }

    private boolean validInputs(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }
}
