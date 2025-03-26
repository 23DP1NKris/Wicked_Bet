package wickedbet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/javafx/start.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 580);
        stage.setScene(scene);
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/javafx/images/icon.png")).toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("Wicked Bet");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}