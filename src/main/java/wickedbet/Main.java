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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/javafx/start.fxml")); // loads the scene from the given file path
        Scene scene = new Scene(fxmlLoader.load(), 650, 580);   // creates a scene with the resolution of 650x580px
        stage.setScene(scene);        // sets the initial scene on the stage
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/javafx/images/icon.png")).toExternalForm()); // puts the file path/image into icon
        stage.getIcons().add(icon);   // adds an icon to the stage
        stage.setTitle("Wicked Bet"); // sets the title of the stage as "Wicked Bet"
        stage.setResizable(false);    // removes the resize property
        stage.show();                 // shows the stage
    }

    public static void main(String[] args) {
        launch();   // launches the app when the main file is run
    }
}