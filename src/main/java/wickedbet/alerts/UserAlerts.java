package wickedbet.alerts;

import javafx.scene.control.Alert;

public class UserAlerts {
    // shows an alert on screen
    public void showAlert(String title, String message) {  // asks for the title and message to display
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}