package cellsociety;

import cellsociety.view.CellularAutomatonView;
import cellsociety.view.SplashScreen;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String TITLE = "Cellular Automaton";
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = new SplashScreen(stage).initialize();

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add("game.css");
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();

    }

    public static void main (String[] args) {
        launch(args);
    }
}
