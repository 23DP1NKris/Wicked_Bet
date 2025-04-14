package wickedbet.services;

import wickedbet.alerts.UserAlerts;
import wickedbet.models.User;

public class BetService {
    private final UserAlerts betAlerts = new UserAlerts();
    private final JsonService jsonService = new JsonService();
    private final UserSessionService userSessionService = UserSessionService.getInstance();

    public boolean validationCheck(String inputBet, double balance) {
        if (ValidInputService.emptyInputs(inputBet)) {
            betAlerts.showAlert("Invalid input", "Your bet cannot be empty!");
            return false;
        }

        if (ValidInputService.validInputs(inputBet)) {
            betAlerts.showAlert("Invalid input", "Bet must be a number with up to two decimal places! (e.g. 0.45 or 5)");
            return false;
        }

        double numericBet;
        try {
            numericBet = Double.parseDouble(inputBet);
        } catch (NumberFormatException e) {
            betAlerts.showAlert("Invalid input", "Bet must be a valid number (e.g. 1.50)!");
            return false;
        }

        if (numericBet < 0.10) {
            betAlerts.showAlert("Invalid bet", "The minimum bet is 0.10!");
            return false;
        }

        if (numericBet > balance) {
            betAlerts.showAlert("Invalid bet", "Your bet cannot be bigger than your balance!");
            return false;
        }

        return true;
    }

    public void biggestBet(Double bet) {
        User currentUser = userSessionService.getLoggedIn();

        if (bet > currentUser.getBiggestBet()) {
            currentUser.setBiggestBet(bet);
            jsonService.saveUserUpdate(currentUser);
        }
    }
}