package wickedbet.services;

import wickedbet.models.User;

public class SpinService {
    private static final UserSessionService userSessionService = UserSessionService.getInstance();

    public void spin(double bet) {
        User currentUser = userSessionService.getLoggedIn();

        currentUser.setBiggestBet(bet);
        System.out.println("Biggest Bet: " + currentUser.getBiggestBet());
    }
}
