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
        SlotStats stats = jsonService.loadSlotStats();

        totalSpinsLabel.setText(String.valueOf(stats.getTotalSpins()));
        totalBetsLabel.setText(String.format("%.2f €", stats.getTotalBet()));
        totalWonLabel.setText(String.format("%.2f €", stats.getTotalWin()));
    }

    // switches to the slots scene
    public void goBack(ActionEvent event) throws IOException {
        sceneManager.switchToSlots(event);
    }
}
