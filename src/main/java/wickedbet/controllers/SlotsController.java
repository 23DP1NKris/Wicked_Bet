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
import wickedbet.models.User;
import wickedbet.services.BetService;
import wickedbet.services.JsonService;
import wickedbet.services.SpinService;
import wickedbet.services.UserSessionService;
import wickedbet.utils.SceneManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SlotsController {
    private final SceneManager sceneManager = new SceneManager();
    private final UserSessionService userSessionService = UserSessionService.getInstance();
    private final UserAlerts betAlerts = new UserAlerts();
    private final BetService betService = new BetService();
    private final SpinService spinService = new SpinService();
    private final JsonService jsonService = new JsonService();

    private User currentUser;

    @FXML
    private TextField betAmount;
    @FXML
    private Button changeBetButton;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label wonLabel;
    @FXML
    private Label spinsLabel;

    public double bet = 0.10;
    public double win;

    private final String[] SYMBOL_NAMES = {"a", "broom", "candy", "eye", "free", "horseshoe", "k", "pot", "witch"};  // names of the slot icons that get added to slot_x.png
    private Image[] symbols;
    private List<List<ImageView>> reels;
    private final Random random = new Random();

    @FXML
    private ImageView slot_1, slot_2, slot_3, slot_4, slot_5, slot_6, slot_7, slot_8, slot_9;

    @FXML
    public void initialize() {
        changeBetButton.setManaged(false);
        changeBetButton.setVisible(false);

        betAmount.textProperty().addListener((obs, oldVal, newVal) -> {
            boolean show = !newVal.trim().isEmpty();
            changeBetButton.setManaged(show);
            changeBetButton.setVisible(show);
        });

        currentUser = userSessionService.getLoggedIn();
        updateBalanceLabel();
        updateSpinsLabel();

        // loads all images from the resource folder using SYMBOL_NAMES
        symbols = new Image[SYMBOL_NAMES.length];
        for (int i = 0; i < SYMBOL_NAMES.length; i++) {
            String imagePath = "/javafx/images/slots_images/slot_" + SYMBOL_NAMES[i] + ".png";
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

    public void goBack(ActionEvent event) throws IOException {
        sceneManager.switchToMenu(event);
    }

    public void switchToAddBalance(ActionEvent event) throws IOException {
        sceneManager.switchToAddBalance(event);
    }

    public void switchToSlotStats(ActionEvent event) throws IOException {
        sceneManager.switchToSlotStats(event);
    }

    private void updateBalanceLabel() {
        balanceLabel.setText(String.format("Balance: %.2f €", currentUser.getBalance()));
    }

    private void updateWonLabel() {
        wonLabel.setText(String.format("Won: %.2f €", win));
    }

    private void updateSpinsLabel() {
        spinsLabel.setText("Free spins: " + currentUser.getRemainingSpins());
    }

    public void changeBet(ActionEvent event) {
        String inputBet = betAmount.getText().trim();
        double balance = currentUser.getBalance();

        if (betService.validationCheck(inputBet, balance)) {
            this.bet = Double.parseDouble(inputBet);

            javafx.application.Platform.runLater(() -> {
                changeBetButton.setVisible(false);
                changeBetButton.setManaged(false);
            });
        }
    }

    public void startSpin(ActionEvent event)    {
        // checks if the user has enough balance to spin
        if (currentUser.getBalance() < bet) {
            betAlerts.showAlert("Not enough balance", "You don't have enough balance to place this bet!");
            return;
        }

        currentUser.setBalance(currentUser.getBalance() - bet); // removes the bet amount from the user's balance
        currentUser.setRemainingSpins(currentUser.getRemainingSpins() - 1); // removes one of the 10 free spins
        jsonService.saveUserUpdate(currentUser);    // updates both balance and remaining spins in json
        updateBalanceLabel();   // updates the balance displayed on screen after clicking spin
        updateSpinsLabel();

        betService.biggestBet(bet); // updates user's biggest bet stat
        startSpinAnimation();   // calls the spin animation to be shown on screen
    }

    public void startSpinAnimation() {
        int[] stopIndices = new int[3];
        for (int i = 0; i < 3; i++) {
            // chooses which symbols will be output in the end
            stopIndices[i] = random.nextInt(SYMBOL_NAMES.length);
            animateReel(i, stopIndices[i]);
        }

        new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            win = spinService.calculateWin(bet, stopIndices, SYMBOL_NAMES);    // calculates the amount won (math in SpinService)
            spinService.updateUserBalance(win);   // adds the money won to the balance
            updateBalanceLabel();   // updates the balance displayed on the screen
            updateWonLabel();
            spinService.biggestWin(win);  // updates the biggestWin variable
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

        timeline.play();
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
