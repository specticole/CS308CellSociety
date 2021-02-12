package cellsociety;

import cellsociety.view.RectangularGridStyle;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String TITLE = "Cellular Automaton";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) {

        Parent root = new GridPane();
        RectangularGridStyle grid = new RectangularGridStyle(root);
        grid.createGrid(100,100);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add("game.css");
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main (String[] args) {
        launch(args);
    }
}
