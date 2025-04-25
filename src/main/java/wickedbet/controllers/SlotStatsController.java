package wickedbet.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import wickedbet.models.SlotStats;
import wickedbet.services.JsonService;
import wickedbet.utils.SceneManager;

import java.io.IOException;

public class SlotStatsController {
    private final SceneManager sceneManager = new SceneManager();
    private final JsonService jsonService = new JsonService();

    @FXML
    private Label totalSpinsLabel, totalBetsLabel, totalWonLabel;

    public void initialize() {
        SlotStats stats = jsonService.loadSlotStats(); // loads the values of the stats object

        totalSpinsLabel.setText(String.valueOf(stats.getTotalSpins())); // shows the total spins on the scene
        totalBetsLabel.setText(String.format("%.2f €", stats.getTotalBet())); // shows the totalBet value on the scene
        totalWonLabel.setText(String.format("%.2f €", stats.getTotalWin())); // shows the totalWin value on the scene
    }

    // switches to the slots scene
    public void goBack(ActionEvent event) throws IOException {
        sceneManager.switchToSlots(event);
    }
}
