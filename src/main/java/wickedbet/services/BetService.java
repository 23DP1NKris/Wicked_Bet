package wickedbet.services;

import wickedbet.alerts.UserAlerts;
import wickedbet.models.User;
import java.math.BigDecimal;

public class BetService {
    private final UserAlerts betAlerts = new UserAlerts();
    private final JsonService jsonService = new JsonService();
    private final UserSessionService userSessionService = UserSessionService.getInstance();

    // validation check (logic in ValidInputService)
    public boolean validationCheck(String inputBet, BigDecimal balance) {
        if (ValidInputService.emptyInputs(inputBet)) {
            betAlerts.showAlert("Invalid input", "Your bet cannot be empty!");
            return false;
        }

        if (ValidInputService.validInputs(inputBet)) {
            betAlerts.showAlert("Invalid input", "Bet must be a number with up to two decimal places! (e.g. 0.45 or 5)");
            return false;
        }

        BigDecimal numericBet;
        try {
            numericBet = new BigDecimal(inputBet);
        } catch (NumberFormatException e) {
            betAlerts.showAlert("Invalid input", "Bet must be a valid number (e.g. 1.50)!");
            return false;
        }

        if (numericBet.compareTo(new BigDecimal("0.10")) < 0) {
            betAlerts.showAlert("Invalid bet", "The minimum bet is 0.10!");
            return false;
        }

        if (numericBet.compareTo(balance) > 0) {
            betAlerts.showAlert("Invalid bet", "Your bet cannot be bigger than your balance!");
            return false;
        }

        return true;
    }

    // sets the user's biggest bet if the bet is bigger than the previous biggest one
    public void biggestBet(BigDecimal bet) {
        User currentUser = userSessionService.getLoggedIn();

        if (bet.compareTo(currentUser.getBiggestBet()) > 0) { // checks if the current bet is bigger than the user's biggest bet
            currentUser.setBiggestBet(bet); // sets the user's biggest bet
            jsonService.saveUserUpdate(currentUser); // updates it in json
        }
    }
}