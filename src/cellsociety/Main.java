package cellsociety;

import cellsociety.view.SplashScreen;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Driver to run the Cellular Automaton Simulation.
 *
 * @author Bill Guo
 */
public class Main extends Application {

    private static final String TITLE = "Cellular Automaton";
    private static final String CSS_FILE = "cellsociety/resources/normalfont.css";
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    /**
     * Creates the splash screen and displays it on the stage
     */
    @Override
    public void start(Stage stage) {

        Parent root = new SplashScreen(stage).initialize();

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add(CSS_FILE);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Entry point.
     *
     * @param args arguments
     */
    public static void main (String[] args) {
        launch(args);
    }
}
