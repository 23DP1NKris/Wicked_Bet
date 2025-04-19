package wickedbet.services;

import wickedbet.models.User;

public class UserSessionService {
    private User user;
    private static UserSessionService instance;

    public static UserSessionService getInstance() { // returns the instance of the user
        if (instance == null) {
            instance = new UserSessionService();
        }
        return instance;
    }

    public void setLoggedIn(User user) { // sets the user either online or offline
        this.user = user;
    }

    public User getLoggedIn() { // returns whether the user is online or offline
        return user;
    }
}