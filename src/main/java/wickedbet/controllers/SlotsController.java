package wickedbet.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import wickedbet.utils.SceneManager;

import java.io.IOException;

public class SlotsController {
    private final SceneManager sceneManager = new SceneManager();

    @FXML
    private TextField betAmount;

    @FXML
    private Button changeBetButton;

    @FXML
    public void initialize() {
        changeBetButton.setVisible(false);
        betAmount.focusedProperty().addListener((obs, oldVal, newVal) -> {
            changeBetButton.setVisible(newVal);
        });
    }

    public void goBack(ActionEvent event) throws IOException {
        sceneManager.switchToMenu(event);
    }
}