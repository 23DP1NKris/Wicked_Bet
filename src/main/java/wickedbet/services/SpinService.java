package wickedbet.services;

import wickedbet.models.User;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SpinService {
    private static final UserSessionService userSessionService = UserSessionService.getInstance();
    private final JsonService jsonService = new JsonService();

    BigDecimal win;

    public BigDecimal calculateWin(BigDecimal bet, int[] reelStops, String[] symbols) {
        // makes all the slots into slot1, slot2, ..., slot9
        String slot1 = symbols[reelStops[0] % symbols.length];       // (1,1)
        String slot2 = symbols[reelStops[1] % symbols.length];       // (1,2)
        String slot3 = symbols[reelStops[2] % symbols.length];       // (1,3)
        String slot4 = symbols[(reelStops[0] + 1) % symbols.length]; // (2,1)
        String slot5 = symbols[(reelStops[1] + 1) % symbols.length]; // (2,2)
        String slot6 = symbols[(reelStops[2] + 1) % symbols.length]; // (2,3)
        String slot7 = symbols[(reelStops[0] + 2) % symbols.length]; // (3,1)
        String slot8 = symbols[(reelStops[1] + 2) % symbols.length]; // (3,2)
        String slot9 = symbols[(reelStops[2] + 2) % symbols.length]; // (3,3)

        if ((slot4.equals(slot5) && slot5.equals(slot6)) || (slot2.equals(slot5) && slot2.equals(slot8))) { // middle row or middle column
            win = bet.multiply(new BigDecimal(8));
        } else if ((slot1.equals(slot5) && slot5.equals(slot9)) || (slot3.equals(slot5) && slot5.equals(slot7))) {  // diagonals
            win = bet.multiply(new BigDecimal(5));
        } else if (slot1.equals(slot5) || slot3.equals(slot5) || slot7.equals(slot5) || slot9.equals(slot5)) {  // corner and middle slot
            win = bet.multiply(new BigDecimal("1.2"));
        } else if (slot4.equals(slot5) || slot5.equals(slot6) || slot4.equals(slot6)) { // one of two slots in the middle row
            win = bet.multiply(new BigDecimal("1.5"));
        } else {
            win = BigDecimal.ZERO; // resets the win back to 0 if none match
        }

        return win.setScale(2, RoundingMode.HALF_UP);
    }

    // updates the user's balance
    public void updateUserBalance(BigDecimal win) {
        User currentUser = userSessionService.getLoggedIn();    // gets the active user
        currentUser.addBalance(win);                            // adds balance according to the amount won
        jsonService.saveUserUpdate(currentUser);                // updates it in json
    }

    // updates the user's biggestWin stat
    public void biggestWin(BigDecimal win) {
        User currentUser = userSessionService.getLoggedIn();

        if (win.compareTo(currentUser.getBiggestWin()) > 0) {     // checks if the win is bigger than the user's previous biggest win
            currentUser.setBiggestWin(win);          // sets the user's biggest win
            jsonService.saveUserUpdate(currentUser); // updates it in json
        }
    }
}