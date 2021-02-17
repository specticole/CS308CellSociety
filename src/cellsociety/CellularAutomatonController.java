package cellsociety;

import cellsociety.grids.Dense2DCellGrid;
import cellsociety.view.CellularAutomatonView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

/**
 * This class initializes a CellularAutomatonConfiguration object that stores all relevant
 * properties of the simulation, and then initializes an appropriate Model and View object. This
 * class also runs steps of the simulation.
 *
 * @author Patrick Liu
 */
public class CellularAutomatonController {

  public static final double[] STEP_SIZES = {1.0, 1.0 / 5, 1.0 / 10, 1.0 / 20, 1.0 / 60};

  private Timeline animation;
  private KeyFrame frame;
  private CellularAutomatonView myView;
  private CellularAutomaton myModel;
  private File currentConfigFile;

  public CellularAutomatonController() {
    frame = new KeyFrame(Duration.seconds(STEP_SIZES[2]), e -> step());
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
  }

  /**
   * Creates a Controller object that stores a myView object, will pass updated states from the
   * Model to the View
   *
   * @param myView - the View object for this simulation
   */
  public CellularAutomatonController(CellularAutomatonView myView) {
    this();
    this.myView = myView;
  }

  /**
   * Initialize the CellularAutomaton given a configuration object.
   */
  public void initializeForConfig(CellularAutomatonConfiguration config) {
    myModel = new CellularAutomaton(config.getGrid(), config.getRuleSet());
  }

  /**
   * Loads a file based on user selection in a visual file chooser
   *
   * @param masterLayout - the GridPane that the View holds
   * @return - object that stores data relevant to the Model and the View
   */
  public CellularAutomatonConfiguration loadConfigFile(GridPane masterLayout) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new ExtensionFilter("XML Document", "*.xml"));
    currentConfigFile = fileChooser.showOpenDialog(masterLayout.getScene().getWindow());
    try {
      CellularAutomatonConfiguration simulationConfig = new CellularAutomatonConfiguration(currentConfigFile);
      return simulationConfig;
    } catch (NullPointerException n) {
      n.printStackTrace();
      return null;
    }
  }

  /**
   * Starts or resumes the simulation by resuming the Timeline object
   */
  public void playSimulation() {
    animation.play();
  }

  /**
   * Pauses the simulation by pausing the Timeline object
   */
  public void pauseSimulation() {
    animation.pause();
  }

  // replaces the current KeyFrame object in order to change the animation rate
  private void changeAnimationRate(double rate) {
    frame = new KeyFrame(Duration.seconds(rate), e -> step());
    animation.stop();
    animation.getKeyFrames().clear();
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  /**
   * Changes the animation rate based on a five-position slider
   *
   * @param sliderPos - the new position of the slider
   */
  public void changeRateSlider(int sliderPos) {
    changeAnimationRate(STEP_SIZES[sliderPos - 1]);
  }

  /**
   * Pauses the simulation and increments the time by one
   */
  public void stepOnce() {
    animation.pause();
    step();
  }

  // may be implemented in Complete
  public void resetSimulation() {

  }

  private void step() {
    myModel.step();

    CellState currentState[][] = ((Dense2DCellGrid) myModel.getGrid()).extractStates(0);
    List<List<String>> updatedStateStrings = new ArrayList<>();
    for (int row = 0; row < currentState.length; row++) {
      ArrayList<String> rowUpdatedStates = new ArrayList<>();
      for (int col = 0; col < currentState[0].length; col++) {
        rowUpdatedStates.add(currentState[row][col].toString());
      }
      updatedStateStrings.add(rowUpdatedStates);
    }
    myView.updateView(updatedStateStrings);
  }

  public void storeConfigFile() throws IOException {
    Path storePath = Files.createDirectory(currentConfigFile.toPath());
    Files.copy(currentConfigFile.toPath(), storePath);
  }

}
