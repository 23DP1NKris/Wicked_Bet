package wickedbet.services;

import wickedbet.models.User;

public class UserSessionService {
    private User user;
    private static UserSessionService instance;

    public static UserSessionService getInstance() {
        if (instance == null) {
            instance = new UserSessionService();
        }
        return instance;
    }

    public void setLoggedIn(User user) {
        this.user = user;
    }

    public User getLoggedIn() {
        return user;
    }
}