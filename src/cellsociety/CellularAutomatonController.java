package cellsociety;

import cellsociety.model.CellState;
import cellsociety.model.CellularAutomaton;
import cellsociety.model.grids.Dense2DCellGrid;
import cellsociety.view.CellularAutomatonView;
import cellsociety.view.SimulationView;
import cellsociety.xml.XMLConfigurationParser;
import cellsociety.xml.XMLException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
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

  protected static final double[] STEP_SIZES = {1.0, 1.0 / 5, 1.0 / 10, 1.0 / 20, 1.0 / 60};

  private Timeline animation;
  private KeyFrame frame;
  private CellularAutomaton myModel;
  private SimulationView mySimulationView;
  private File currentConfigFile;
  private List<List<String>> currentStates;

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
   * @param mySimulationView - the View object for each simulation
   * @param configFile - the File for each simulation
   */

  public CellularAutomatonController(SimulationView mySimulationView,
      File configFile) {
    this();
    this.mySimulationView = mySimulationView;
    currentConfigFile = configFile;
    CellularAutomatonConfiguration config = new CellularAutomatonConfiguration(configFile);
    currentStates = config.getInitialStates();
    myModel = new CellularAutomaton(config.getGrid(), config.getRuleSet());
  }

  public void saveConfigFile(GridPane masterLayout) {
    pauseSimulation();
    DirectoryChooser directoryChooser = new DirectoryChooser();
    File saveConfigFileLocation = directoryChooser.showDialog(masterLayout.getScene().getWindow());
    String saveFileName = currentConfigFile.getName().replaceAll(".xml","").concat("copy.xml"); // todo: allow user to change name
    try {
      Path storeConfigFilePath = Paths.get(saveConfigFileLocation.getPath() + "/" + saveFileName);
      Path storedConfigFilePath = Files.copy(currentConfigFile.toPath(), storeConfigFilePath, StandardCopyOption.REPLACE_EXISTING);
      File storedConfigFile = storedConfigFilePath.toFile();
      updateStoredConfigFile(storedConfigFile);
    } catch (IOException e) {
      e.printStackTrace();
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
    for (int row = 0; row < currentState.length; row++) {
      for (int col = 0; col < currentState[0].length; col++) {
        currentStates.get(row).set(col, currentState[row][col].toString());
      }
    }
    mySimulationView.updateView(currentStates);
  }

  public void updateStoredConfigFile(File storedConfigFile) {
    XMLConfigurationParser updateParser = new XMLConfigurationParser(storedConfigFile);
    updateParser.updateStoredConfigFile(currentStates);
  }

  public void updateParameters(Map<String, String> parameterList) {
    //TODO: implement parameter changing
  }
}
