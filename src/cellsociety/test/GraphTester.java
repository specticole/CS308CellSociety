package cellsociety.test;

import cellsociety.CellularAutomatonConfiguration;
import cellsociety.model.CellState;
import cellsociety.model.CellularAutomaton;
import cellsociety.model.grids.Dense2DCellGrid;
import cellsociety.view.Graph;
import cellsociety.xml.XMLException;
import java.io.File;
import java.security.Key;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GraphTester extends Application {
  private CellularAutomaton myModel;
  private Graph myGraph;
  private List<List<String>> currentStates;

  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle("Testing graph");
    Scene tempScene = new Scene(new Group(), 400, 400);
    stage.setScene(tempScene);
    stage.show();
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new ExtensionFilter("XML Document", "*.xml"));
    File configFile = fileChooser.showOpenDialog(stage.getScene().getWindow());
    CellularAutomatonConfiguration config = new CellularAutomatonConfiguration(configFile);
    myGraph = new Graph(config);
    Scene scene = new Scene(myGraph.initialize(), 400, 400);
    stage.setScene(scene);
    stage.show();

    myModel = new CellularAutomaton(config.getGrid(), config.getRuleSet());
    currentStates = config.getInitialStates();

    KeyFrame frame = new KeyFrame(Duration.seconds(1), e -> step());
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  public static void main(String[] args) {
    launch(args);
  }

  private void step() {
    myModel.step();
    CellState currentState[][] = ((Dense2DCellGrid) myModel.getGrid()).extractStates(0);
    for (int row = 0; row < currentState.length; row++) {
      for (int col = 0; col < currentState[0].length; col++) {
        currentStates.get(row).set(col, currentState[row][col].toString());
      }
    }
    myGraph.updateGraph(currentStates);
  }
}
