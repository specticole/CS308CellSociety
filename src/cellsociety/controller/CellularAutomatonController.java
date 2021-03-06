package cellsociety.controller;

import cellsociety.controller.CellularAutomatonConfiguration;
import cellsociety.model.CellState;
import cellsociety.model.CellularAutomaton;
import cellsociety.model.CellularAutomatonRule;
import cellsociety.model.GridCoordinates;
import cellsociety.model.grids.Dense2DCellGrid;
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
  private CellularAutomatonConfiguration config;

  /**
   * Initializes JavaFX animation
   */
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
   * @param configFile       - the File for each simulation
   */
  public CellularAutomatonController(SimulationView mySimulationView,
      File configFile) {
    this();
    this.mySimulationView = mySimulationView;
    currentConfigFile = configFile;
    try {
      config = new CellularAutomatonConfiguration(configFile);
      currentStates = config.getInitialStates();
      myModel = new CellularAutomaton(config.getGrid(), config.getRuleSet());
    }
    catch (Exception e) {
      mySimulationView.makeAlert("Invalid XML file");
    }
  }

  /**
   * Saves the current state of the grid into a new XML file. The file is saved
   * into a directory chosen by the user and has the name "[CONFIGNAME]copy.xml"
   *
   * @param masterLayout - GridPane where directory chooser should be displayed
   */
  public void saveConfigFile(GridPane masterLayout) throws XMLException {
    pauseSimulation();
    DirectoryChooser directoryChooser = new DirectoryChooser();
    File saveConfigFileLocation = directoryChooser.showDialog(masterLayout.getScene().getWindow());
    if (saveConfigFileLocation != null) {
      String saveFileName = currentConfigFile.getName().replaceAll(".xml", "")
          .concat("copy.xml"); // todo: allow user to change name
      try {
        Path storeConfigFilePath = Paths.get(saveConfigFileLocation.getPath() + "/" + saveFileName);
        Path storedConfigFilePath = Files.copy(currentConfigFile.toPath(), storeConfigFilePath,
            StandardCopyOption.REPLACE_EXISTING);
        File storedConfigFile = storedConfigFilePath.toFile();
        updateStoredConfigFile(storedConfigFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    else {
      throw new XMLException(new IllegalArgumentException());
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
    pauseSimulation();
    step();
  }

  /**
   * Resets the simulation by using the original configuration file
   * to re-initialize the Model
   */
  public void resetSimulation() {
    pauseSimulation();
    config = new CellularAutomatonConfiguration(currentConfigFile);
    myModel = new CellularAutomaton(config.getGrid(), config.getRuleSet());
    mySimulationView.updateView(config.getInitialStates());
  }

  // updates the Model, then uses the updated grid to update the View
  private void step() {
    myModel.step();
    CellState[][] currentState = ((Dense2DCellGrid) myModel.getGrid()).extractStates(0);
    setCurrentStates(currentState);
    mySimulationView.updateView(currentStates);
  }

  // helper method to update the ArrayList instance variable given a 2D array
  private void setCurrentStates(CellState[][] currentState) {
    for (int row = 0; row < currentState.length; row++) {
      for (int col = 0; col < currentState[0].length; col++) {
        currentStates.get(row).set(col, currentState[row][col].toString());
      }
    }
  }

  // helper method to save new config file by editing a copy of the original
  private void updateStoredConfigFile(File storedConfigFile) {
    XMLConfigurationParser updateParser = new XMLConfigurationParser(storedConfigFile);
    updateParser.updateStoredConfigFile(currentStates);
  }

  /**
   * Sets simulation parameters to user-defined values that they enter
   * in the View
   * @param parameterList - map of updated parameter names and values
   */
  public void updateParameters(Map<String, String> parameterList) {
    CellularAutomatonRule ruleSet = config.getRuleSet();
    ruleSet.setGameSpecifics(parameterList);
    myModel.setRule(ruleSet);
  }

  /**
   * Changes the state of a Cell that has been clicked on in the View
   * @param state - the state that the Cell should be updated to
   * @param xLocation - the x coordinate of the Cell
   * @param yLocation - the y coordinate of the Cell
   */
  public void changeCell(String state, int xLocation, int yLocation) {
    try {
      myModel.getGrid().getCell(new GridCoordinates(xLocation, yLocation)).setState(0,
          cellsociety.model.states.Index.allStates.get(config.getSimulationType())
              .getConstructor(String.class).newInstance(state));
      setCurrentStates(((Dense2DCellGrid) myModel.getGrid()).extractStates(0));
      mySimulationView.updateView(currentStates);
    } catch (Exception ignored) {

    }
  }
}
