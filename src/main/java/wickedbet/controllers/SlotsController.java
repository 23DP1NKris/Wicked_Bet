package wickedbet.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import wickedbet.alerts.UserAlerts;
import wickedbet.models.SlotStats;
import wickedbet.models.User;
import wickedbet.services.BetService;
import wickedbet.services.JsonService;
import wickedbet.services.SpinService;
import wickedbet.services.UserSessionService;
import wickedbet.utils.SceneManager;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

public class SlotsController {
    private final SceneManager sceneManager = new SceneManager();
    private final UserSessionService userSessionService = UserSessionService.getInstance();
    private final UserAlerts betAlerts = new UserAlerts();
    private final BetService betService = new BetService();
    private final SpinService spinService = new SpinService();
    private final JsonService jsonService = new JsonService();
    SlotStats stats = jsonService.loadSlotStats();

    private User currentUser;

    @FXML
    private TextField betAmount;
    @FXML
    private Button changeBetButton, spinButton;
    @FXML
    private Label balanceLabel, wonLabel, spinsLabel;

    public BigDecimal bet = new BigDecimal("0.10");
    public BigDecimal win = BigDecimal.ZERO;

    private final String[] SYMBOL_NAMES = {"a", "broom", "candy", "eye", "free", "horseshoe", "k", "pot", "witch"};  // names of the slot icons that get added to slot_x.png
    private Image[] symbols;
    private List<List<ImageView>> reels;
    private final Random random = new Random();

    @FXML
    private ImageView slot_1, slot_2, slot_3, slot_4, slot_5, slot_6, slot_7, slot_8, slot_9;

    @FXML
    public void initialize() {
        // makes the "Change Bet" button invisible
        changeBetButton.setManaged(false);
        changeBetButton.setVisible(false);

        // event listener that listens to the bet input being active
        betAmount.textProperty().addListener((obs, oldVal, newVal) -> {
            boolean show = !newVal.trim().isEmpty();
            // sets the "Change Bet" button as visible
            changeBetButton.setManaged(show);
            changeBetButton.setVisible(show);
        });

        currentUser = userSessionService.getLoggedIn();
        updateBalanceLabel(); // shows the initial user's balance
        updateSpinsLabel();   // shows the remaining free spin amount on the scene

        // loads all images from the resource folder using SYMBOL_NAMES
        symbols = new Image[SYMBOL_NAMES.length];
        for (int i = 0; i < SYMBOL_NAMES.length; i++) {
            String imagePath = "/javafx/images/slots_images/slot_" + SYMBOL_NAMES[i] + ".png"; // adds the image path to the symbol
            InputStream is = getClass().getResourceAsStream(imagePath);
            symbols[i] = new Image(is);
        }

        // orders the slot images into columns
        reels = Arrays.asList(
                Arrays.asList(slot_1, slot_4, slot_7),  // 1st column
                Arrays.asList(slot_2, slot_5, slot_8),  // 2nd column
                Arrays.asList(slot_3, slot_6, slot_9)   // 3rd column
        );
    }

    // switches to the menu scene
    public void goBack(ActionEvent event) throws IOException {
        sceneManager.switchToMenu(event);
    }

    // switches to the add balance scene
    public void switchToAddBalance(ActionEvent event) throws IOException {
        sceneManager.switchToAddBalance(event);
    }

    // switches to the slot stats scene
    public void switchToSlotStats(ActionEvent event) throws IOException {
        sceneManager.switchToSlotStats(event);
    }

    // updates the balance label when called
    private void updateBalanceLabel() {
        balanceLabel.setText(String.format("Balance: %.2f €", currentUser.getBalance())); // formats the output
    }

    // updates the won label when called
    private void updateWonLabel() {
        wonLabel.setText(String.format("Won: %.2f €", win)); // formats the output
    }

    // updates the free spins label
    private void updateSpinsLabel() {
        spinsLabel.setText("Free spins: " + currentUser.getRemainingSpins());
    }

    // changes the bet variable
    public void changeBet(ActionEvent event) {
        String inputBet = betAmount.getText().trim(); // gets the bet as string from the text field and trims it
        BigDecimal balance = currentUser.getBalance();  // gets the user's balance

        if (betService.validationCheck(inputBet, balance)) { // validation
            this.bet = new BigDecimal(inputBet); // parses the inputted bet from a string into a BigDecimal

            javafx.application.Platform.runLater(() -> { // delays the button becoming invisible before the actions are finished
                // sets the button as invisible again
                changeBetButton.setVisible(false);
                changeBetButton.setManaged(false);
            });
        }
    }

    // starts the animation and math on button press
    public void startSpin(ActionEvent event) {
        spinButton.setDisable(true); // disables the spin button

        BigDecimal BetBeforeSpin = bet; // stores the user's chosen bet before anything happens
        BigDecimal actualBetUsed;

        if (currentUser.getRemainingSpins() > 0) {                                  // checks if the user has free spins left
            actualBetUsed = new BigDecimal("0.10");                             // sets bet to 0.10 during free spin
            currentUser.setRemainingSpins(currentUser.getRemainingSpins() - 1);     // deducts free spins by 1
            jsonService.saveUserUpdate(currentUser);                                // updates spins in json
            updateSpinsLabel();                                                     // updates the remaining free spins on the scene after clicking spin
        } else {
            actualBetUsed = BetBeforeSpin;                                          // use the real selected bet
            if (currentUser.getBalance().compareTo(actualBetUsed) < 0) {            // checks if the user has enough balance to spin
                betAlerts.showAlert("Not enough balance", "You don't have enough balance to place this bet!");
                spinButton.setDisable(false); // enables the button if the user didn't have enough balance
                return;
            }

            currentUser.setBalance(currentUser.getBalance().subtract(actualBetUsed)); // removes the bet amount from the user's balance
            jsonService.saveUserUpdate(currentUser);                                  // updates balance in json
            updateBalanceLabel();                                                     // updates the balance on the scene after clicking spin
            stats.setTotalBet(stats.getTotalBet().add(actualBetUsed));                // adds the total bet amount to the previous sum
            jsonService.saveSlotStats(stats);                                         // updates the slot stats in json
        }

        betService.biggestBet(actualBetUsed); // updates user's biggest bet stat

        // pass both the user's selected bet and the actual bet used in this spin
        startSpinAnimation(BetBeforeSpin, actualBetUsed);
    }

    public void startSpinAnimation(BigDecimal userSelectedBet, BigDecimal actualBetUsed) {
        int[] stopIndices = new int[3];
        for (int i = 0; i < 3; i++) {
            // chooses which symbols will be output in the end
            stopIndices[i] = random.nextInt(SYMBOL_NAMES.length);
            animateReel(i, stopIndices[i]);
        }

        // creates a timeline
        new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            win = spinService.calculateWin(actualBetUsed, stopIndices, SYMBOL_NAMES);    // calculates the amount won (math in SpinService)
            spinService.updateUserBalance(win);   // adds the money won to the balance
            spinService.giveFreeSpins();          // gives free spins if the
            updateBalanceLabel();                 // updates the balance displayed on the scene
            updateSpinsLabel();                   // updates the remaining free spins on the scene
            updateWonLabel();                     // shows the won amount in the scene
            spinService.biggestWin(win);          // updates the biggestWin variable if the win is bigger
            stats.setTotalSpins(stats.getTotalSpins() + 1);     // adds 1 to the totalSpins count
            stats.setTotalWin(stats.getTotalWin().add(win));    // adds the win amount to the previous sum
            jsonService.saveSlotStats(stats);                   // updates the slot stats in json

            if (actualBetUsed.equals(new BigDecimal("0.10"))) { // only reset the bet back if the spin was a free spin
                bet = userSelectedBet;  // restores user's original bet after free spin
            }

            spinButton.setDisable(false); // enables the spin button after the animation
        })).play();
    }

    // creates a timeline which makes the spinning animation look like it's spinning
    private void animateReel(int reelIndex, int stopIndex) {
        List<ImageView> reel = reels.get(reelIndex);
        Timeline timeline = new Timeline();
        final int[] currentOffset = {0};

        // animation - adds a scrolling effect
        for (int i = 0; i < 7; i++) {            // in "i < x" x = 100ms
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(i * 100),
                    e -> updateReelImages(reel, currentOffset[0]++)
            ));
        }

        // final keyframe - shows the final symbols according to stopIndex
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(800), // must be 100ms over the x * 100 value in i < x
                e -> updateReelImages(reel, stopIndex)
        ));

        timeline.play(); // plays the timeline
    }

    // displays/updates the images on the scene
    private void updateReelImages(List<ImageView> reel, int offset) {
        for (int i = 0; i < 3; i++) {
            int symbolIndex = (offset + i) % SYMBOL_NAMES.length;

            if (symbols[symbolIndex] != null) {
                reel.get(i).setImage(symbols[symbolIndex]);
            }
        }
    }
}